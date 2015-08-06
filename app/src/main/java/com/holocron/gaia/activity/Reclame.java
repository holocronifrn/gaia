package com.holocron.gaia.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.holocron.gaia.R;

/**
 * Created by S�vio on 26/07/2015.
 */
public class Reclame extends ActionBarActivity {

    EditText editTextEmailConteudo;
    EditText nomeCompleto;
    EditText matricula;
    Button botaoEnviar;

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.reclame);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editTextEmailConteudo = (EditText) findViewById(R.id.editTextEmailConteudo);
        nomeCompleto = (EditText) findViewById(R.id.editTextNomeCompleto);
        botaoEnviar = (Button) findViewById(R.id.buttonSend);
        matricula = (EditText) findViewById(R.id.editTextMatricula);

    }

    public void onButtonClickSend(View v) {
        String emailmatricula = matricula.getText().toString();
        String emailnome = nomeCompleto.getText().toString();
        String emailTo = "saviorennan@gmail.com";
        String emailSubject = "Reclamacao- Aplicativo Hoje é o quê?";
        String emailConteudoTeste= editTextEmailConteudo.getText().toString();
        String emailConteudo = editTextEmailConteudo.getText().toString()
                + "\n\n" + "Nome do aluno: " + emailnome + "\n" + "Matrícula: " + emailmatricula + "\n";

        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{emailTo});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, emailSubject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, emailConteudo);

        if (emailConteudoTeste.length() == 0 || emailmatricula.length() == 0 || emailnome.length() == 0) {

            Toast.makeText(getApplication(), "Existe campos obrigatórios em branco!", Toast.LENGTH_SHORT).show();

        } else {
            emailIntent.setType("message/rfc822");
            startActivity(Intent.createChooser(emailIntent, "Selecione o seu provedor de email: "));
        }

    }

    public boolean onCreateOptionsMenu(android.view.Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

}

