package com.holocron.gaia.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.holocron.gaia.R;
import com.holocron.gaia.model.WeekDay;
import com.holocron.gaia.net.FirstUpdade;
import com.holocron.gaia.net.NetworkStatus;
import com.holocron.gaia.net.XlsxDownloadAsyncTask;
import com.holocron.gaia.repository.cache.CsvFileManagerRead;

import java.io.IOException;

public class MainActivity extends ActionBarActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private Spinner spinner;
    private FirstUpdade update;
    private TextView textLunch;
    private TextView textDinner;

    private NavigationDrawerFragment mNavigationDrawerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("Activity", "onCreate");

        textLunch = (TextView) findViewById(R.id.lunch);
        textDinner = (TextView) findViewById(R.id.dinner);

        //UPDATE
        update = new FirstUpdade(MainActivity.this);
        update.execute();
        //ENDUPDATE

        setupNavigationDrawer();
        setupWeekSpinner();

    }

    protected void onStart(){
        super.onStart();

        Log.d("Activity", "onStart");

    }

    protected void onDestroy(){
        super.onDestroy();

        Log.d("Activity", "onDestroy");

    }

    protected void onStop(){
        super.onStop();

        Log.d("Activity", "onStop");

    }

    protected void onResume(){
        super.onResume();

        Log.d("Activity", "onResume");

    }

    protected void onPause(){
        super.onPause();

        Log.d("Activity", "onPause");

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
                WeekDay day = WeekDay.getDay(position);

                String meat = "";

                CsvFileManagerRead csvFileManagerReadLunch = new CsvFileManagerRead(MainActivity.this);

                try {
                    meat = csvFileManagerReadLunch.meatType(day.getValue(), true);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                textLunch.setText(meat);

                CsvFileManagerRead csvFileManagerReadDinner = new CsvFileManagerRead();

                try {
                    meat = csvFileManagerReadDinner.meatType(day.getValue(), false);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                textDinner.setText(meat);

            }

            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
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

    private void updateXlsx() {
        if (NetworkStatus.isConnected(this)) {//verifica se existe conexão
            //Referenciar o objeto novamente evida o erro de execução ao clicar várias vezes nele!
            XlsxDownloadAsyncTask downloadBackground = new XlsxDownloadAsyncTask(MainActivity.this);
            downloadBackground.execute();
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
