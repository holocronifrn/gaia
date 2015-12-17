package com.holocron.gaia.activity;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.v4.widget.DrawerLayout;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.holocron.gaia.Constants;
import com.holocron.gaia.R;
import com.holocron.gaia.model.WeekDay;
import com.holocron.gaia.net.FirstUpdade;
import com.holocron.gaia.net.NetworkStatus;
import com.holocron.gaia.net.XlsxDownloadAsyncTask;
import com.holocron.gaia.repository.cache.ControlDay;
import com.holocron.gaia.repository.cache.CsvFileManagerRead;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends ActionBarActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private Spinner spinner;
    private FirstUpdade update;
    private TextView textLunch;
    private TextView textDinner;
    private TextView tvDataDeAtualizacao;
    private boolean status = false;
    private int num = 0;

    private NavigationDrawerFragment mNavigationDrawerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("Activity", "onCreate");

        textLunch = (TextView) findViewById(R.id.lunch);
        textDinner = (TextView) findViewById(R.id.dinner);
        tvDataDeAtualizacao = (TextView) findViewById(R.id.tvAtualizacaoCardapio);

        Log.d("Activity1", "onCreate1");

        setupNavigationDrawer();
        setupWeekSpinner();
        showUpdateMenu();
        //Log.d("Activity1", "onCreate1");
    }

    private void showUpdateMenu(){
        ControlDay ctDay = new ControlDay();
        try {

            tvDataDeAtualizacao.setText("" + ctDay.dataAtualizacao());
            Log.d("DataAtualização", " " + ctDay.dataAtualizacao());

        } catch (IOException e) {
            e.printStackTrace();
            Log.d("DataAtualização", "Não deu certo!" );
        }
    }

    protected void onResume() {
        super.onResume();

        Log.d("Activity1", "onResume");
        long currentTime = System.currentTimeMillis();

        File fileCard = new File(Constants.FILENAME);

        Date dateFile = new Date(currentTime);
        Date newDateFile = new Date(currentTime);
        Date newDate = new Date(currentTime);

        if (fileCard.exists()) {//Caso o arquivo não existe será atribuido a ele data ficticias para que possa ser realizado o dowload
            //AJUSTANDO DATA
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

            ControlDay controlDay = new ControlDay();

            String dateInString = null;
            Log.d("Activity1", "onResume");
            try {
                Log.d("Activity1", "antes da data");
                dateInString = controlDay.data();
                Log.d("Activity1", "após da data");
            } catch (IOException e) {
                Log.d("Activity1", "Exceção aqui");
                e.printStackTrace();
            }

            Log.d("Activity1", "onResume");
            try {
                Date dateDoArquivo = formatter.parse(dateInString);
                dateFile = dateDoArquivo;
                //Log.d(Constants.TAG, "Data do arquivo " + dateDoArquivo);
                //Log.d(Constants.TAG, "Data do arquivo formatado " + (formatter.format(dateDoArquivo)));
            } catch (ParseException e) {
                e.printStackTrace();
                //Log.d(Constants.TAG, "Erro na conversão ");
            }
            //Incrementando 7 dias na data com base na data anterior
            Calendar c = Calendar.getInstance();
            c.setTime(dateFile);
            c.add(Calendar.DATE, 7);
            newDateFile = c.getTime();
            //END
        }
        Log.d("Activity1", "onResume");
        if (newDateFile.before(newDate) || newDateFile.equals(newDate)) {
            //UPDATE
            update = new FirstUpdade(MainActivity.this);
            update.execute();
            //ENDUPDATE

            setupNavigationDrawer();
            setupWeekSpinner();
            showUpdateMenu();
        }
    }

    public void onButtonClick(View view){
        startActivity(Reclame.class);
    }

    private void setupNavigationDrawer() {
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, drawerLayout);
    }

    private void setupWeekSpinner() {
        ArrayAdapter<CharSequence> adapter =
                ArrayAdapter.createFromResource(this, R.array.dias_semana_list, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner = (Spinner) findViewById(R.id.semana_spinner);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (status) {
                    status = !status;
                    WeekDay day = WeekDay.getDay(2);

                    Log.d("Spinner1", "" + day);

                    setupTextWeek(day);
                } else {
                    WeekDay day = WeekDay.getDay(position);

                    Log.d("Spinner2", "No " + day);
                    setupTextWeek(day);
                }
            }

            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
    }

    public void setupTextWeek(WeekDay weekDay) {
        String meatLunch = "";
        String meatDinner = "";

        if(status){
            status = !status;
            weekDay = WeekDay.getDay(num);

            Log.d("Spinner1","" + weekDay);

            setupTextWeek(weekDay);
        }

        CsvFileManagerRead csvFileManagerReadLunch = new CsvFileManagerRead(MainActivity.this);

        try {
            meatLunch = csvFileManagerReadLunch.meatType(weekDay, true);
            meatDinner = csvFileManagerReadLunch.meatType(weekDay, false);
        } catch (IOException e) {
            e.printStackTrace();
        }

        textLunch.setText(meatLunch);
        textDinner.setText(meatDinner);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        switch (position) {
            case 1:
                startActivity(Reclame.class);
                break;
            case 2:
                updateXlsx();
                break;
            case 3:
                startActivity(Sobre.class);
                break;
        }
    }

    private void startActivity(Class clazz) {
        startActivity(new Intent(this, clazz));
    }

    public void atualizadorDePosition(){
        status = !status;
        num = spinner.getSelectedItemPosition();
    }

    private void updateXlsx() {

        atualizadorDePosition();

        if (NetworkStatus.isConnected(this)) {//verifica se existe conexão
            //Referenciar o objeto novamente evida o erro de execução ao clicar várias vezes nele!
            XlsxDownloadAsyncTask downloadBackground = new XlsxDownloadAsyncTask(MainActivity.this);
            downloadBackground.execute();
            Log.d("Spinner","Update");
        } else {
            Toast.makeText(MainActivity.this, "ERRO! Verifique Sua Conexão!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
