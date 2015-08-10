package com.holocron.gaia.activity;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.holocron.gaia.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class Reclame extends ActionBarActivity {

    EditText editTextEmailConteudo;
    EditText nomeCompleto;
    EditText matricula;
    Button botaoEnviar;

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.reclame);
        showSimplePopUp();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editTextEmailConteudo = (EditText) findViewById(R.id.editTextEmailConteudo);
        nomeCompleto = (EditText) findViewById(R.id.editTextNomeCompleto);
        botaoEnviar = (Button) findViewById(R.id.buttonSend);
        matricula = (EditText) findViewById(R.id.editTextMatricula);


    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onButtonClickSend(View v) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date data = new Date();

        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        Date data_atual = cal.getTime();

        String data_completa = dateFormat.format(data_atual);
        String emailmatricula = matricula.getText().toString();
        String emailnome = nomeCompleto.getText().toString();
        String emailTo = "coaes.pf@ifrn.edu.br";
        String emailSubject = data_completa + " -Reclamação" + "- " + emailnome;
        String emailConteudoTeste = editTextEmailConteudo.getText().toString();
        String emailConteudo = editTextEmailConteudo.getText().toString()
                + "\n\n" + "Matrícula do aluno: " + emailmatricula + "\n";

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

    private void showSimplePopUp() {

        AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);
        helpBuilder.setTitle("Atenção!");
        helpBuilder.setMessage("A reclamação feita através deste canal não substitui o procedimento de protocolo de reclamações.");
        helpBuilder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                    }
                });

        // Remember, create doesn't show the dialog
        AlertDialog helpDialog = helpBuilder.create();
        helpDialog.show();
    }

}


