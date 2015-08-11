package com.holocron.gaia.net;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.holocron.gaia.Constants;
import com.holocron.gaia.activity.MainActivity;
import com.holocron.gaia.model.WeekDay;
import com.holocron.gaia.repository.cache.ControlDay;

import org.apache.http.util.ByteArrayBuffer;

import java.io.BufferedInputStream;
import java.io.File;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class XlsxDownloadAsyncTask extends AsyncTask<Void, Void, String> {

    private MainActivity mainActivity;
    private ProgressDialog dialog;

    public XlsxDownloadAsyncTask(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public Context getContext() {
        return mainActivity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = new ProgressDialog(mainActivity);
        dialog.setMessage("Atualizando cardápio...");
        dialog.setCancelable(false);//Impedir que o dialogo seja fechado!
        dialog.show();
    }

    @Override
    protected String doInBackground(Void... url) {
        return downloadArquivoExcel();
    }

    @Override
    protected void onPostExecute(String msg) {
        dialog.dismiss();
        mainActivity.setupTextWeek(WeekDay.MONDAY);
        Toast.makeText(mainActivity, msg, Toast.LENGTH_LONG).show();
    }

    private String downloadArquivoExcel() {  //this is the downloader method
        try {
            long currentTime = System.currentTimeMillis();
            URL url = new URL(Constants.URL); //you can write here any link

            File dir = new File(Constants.DIR);

            if (!dir.exists()) {
                   dir.mkdir();
                    //Log.d("TAG", "Diretório criado");

            }

            File fileCard = new File(Constants.FILENAME);

            long startTime = System.currentTimeMillis();
            //Log.d(Constants.TAG, "download begining");
            //Log.d(Constants.TAG, "download url: " + url);
            //Log.d(Constants.TAG, "downloaded file name: " + Constants.FILENAME);
                        /* Open a connection to that URL. */
            URLConnection ucon = url.openConnection();
            ucon.setReadTimeout(Constants.TIMEOUT_CONNECTION);
            ucon.setConnectTimeout(Constants.TIMEOUT_SOCKET);
            /*
             * Define InputStreams to read from the URLConnection.
             */

            /*if (fileCard.exists()) {
                Log.d(Constants.TAG, "Existe");
            } else {
                Log.d(Constants.TAG, "Não Existe");
            }*/


//            Log.d(Constants.TAG, "Data do Sistema: " + newDate);
//            Log.d(Constants.TAG, "Data do arquivo original: " + dateFile);
//            Log.d(Constants.TAG, "Data do novo arquivo Modificado : " + newDateFile);

            //Log.d(Constants.TAG, "Esta Baixando = true");
            InputStream is = ucon.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
                /*
                 * Read bytes to the Buffer until there is nothing more to read(-1).
                */

            ByteArrayBuffer baf = new ByteArrayBuffer(50);
            int current = 0;
            while ((current = bis.read()) != -1) {
                baf.append((byte) current);
            }

                /* Convert the Bytes read to a String. */
            FileOutputStream fos = new FileOutputStream(fileCard);
            fos.write(baf.toByteArray());
            fos.close();
            /*Log.d(Constants.TAG, "download ready in "
                    + ((System.currentTimeMillis() - startTime) / 1000)
                    + " sec");*/


            return "Cardápio Atualizado!";
        } catch (IOException ioe) {
            //Log.d("DownloadManager", "Error: " + ioe);
            return "Erro de Conexão.";
        }
    }
}