package com.holocron.gaia.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.holocron.gaia.R;
import com.holocron.gaia.net.NetworkStatus;
import com.holocron.gaia.net.XlsxDownloadAsyncTask;

public class MainActivity extends ActionBarActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private String[] states;
    private Spinner spinner;
    /**
     * Fragmento gerir os comportamentos, intera��es e apresenta��o da gaveta de navega��o.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Usado para armazenar o �ltimo t�tulo da tela. Para uso em {link #restoreActionBar ()}
     */

    private CharSequence mTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        //download File
        // Configurar a drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        //Lista dias da semana
        states = getResources().getStringArray(R.array.dias_semana_list);
        spinner = (Spinner) findViewById(R.id.semana_spinner);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, states);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        //customizar lista
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.dias_semana_list, R.layout.spinner_item);
        spinner.setAdapter(adapter);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                spinner.getSelectedItemPosition();
            }
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        //PopUP Sobre


    }
    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // atualizar o conte�do principal, substituindo fragmentos
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }

    public void onSectionAttached(int number) {

        switch (number) {
            case 1:
                mTitle = getString(R.string.inicio);
                break;
            case 2:
                startActivity(new Intent(this, Reclame.class));
                break;
            case 3:
                //BOT�O ATUALIZAR
                if (NetworkStatus.isConnected(this)) {//verifica se existe conex�o
                    //Referenciar o objeto novamente evida o erro de execu��o ao clicar v�rias vezes nele!
                    XlsxDownloadAsyncTask downloadBackground = new XlsxDownloadAsyncTask(MainActivity.this);
                    downloadBackground.execute();
                } else {
                    Toast.makeText(MainActivity.this, "ERRO! Verifique Sua Conex�o!", Toast.LENGTH_LONG).show();
                }

                break;
            case 4:
                startActivity(new Intent(this, Sobre.class));
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Exibir apenas itens na barra de a��o relevantes a esta tela
            // Se a gaveta n�o est� mostrando. Caso contr�rio, deixe a gaveta
            // Decidir o que mostrar na barra de a��o.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Item da barra de a��o Handle clica aqui. A barra de a��o vai
        // Processar automaticamente os cliques no bot�o / Up Casa, contanto
        // Como voc� especificar uma atividade pai em AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    /**
     * Um fragmento espa�o reservado contendo uma vis�o simples.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * O argumento fragmento representando o n�mero da se��o para este
         * Fragmento.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Retorna uma nova inst�ncia desse fragmento para a se��o de dado
         * N�mero.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }


}
