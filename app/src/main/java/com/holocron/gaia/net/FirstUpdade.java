package com.holocron.gaia.net;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.holocron.gaia.Constants;
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

/**
 * Created by herlan on 07/08/15.
 */

public class FirstUpdade extends AsyncTask<Void, Void, String> {
    public FirstUpdade(Context context) {
        this.context = context;
    }

    private Context context;
    private ProgressDialog dialog;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = new ProgressDialog(context);
        dialog.setMessage("Iniciando...");
        dialog.setCancelable(false);//Impedir que o dialogo seja fechado!
        dialog.show();
    }

    @Override
    protected String doInBackground(Void... params) {
        return downloadArquivoExcel();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        dialog.dismiss();
    }

    private String downloadArquivoExcel() {  //this is the downloader method
        try {
            long currentTime = System.currentTimeMillis();
            URL url = new URL(Constants.URL); //you can write here any link
            File fileCard = new File(Constants.FILENAME);

            long startTime = System.currentTimeMillis();
            Log.d(Constants.TAG, "download begining");
            Log.d(Constants.TAG, "download url: " + url);
            Log.d(Constants.TAG, "downloaded file name: " + Constants.FILENAME);
                        /* Open a connection to that URL. */
            URLConnection ucon = url.openConnection();
            ucon.setReadTimeout(Constants.TIMEOUT_CONNECTION);
            ucon.setConnectTimeout(Constants.TIMEOUT_SOCKET);
                        /*
                         * Define InputStreams to read from the URLConnection.
                         */

            Date dateFile = new Date();
            Date newDateFile;
            Date newDate = new Date(currentTime);
            //Log.d("ImageManager", "Tempo do android" + datee);

            //AJUSTANDO DATA
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            ControlDay controlDay = new ControlDay();
            String dateInString = controlDay.data();

            try {
                Date dateDoArquivo = formatter.parse(dateInString);
                dateFile = dateDoArquivo;
                Log.d(Constants.TAG, "Data do arquivo " + dateDoArquivo);
                Log.d(Constants.TAG, "Data do arquivo formatado " + (formatter.format(dateDoArquivo)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            //Incrementando 7 dias na data com base na data anterior
            Calendar c = Calendar.getInstance();
            c.setTime(dateFile);
            c.add(Calendar.DATE, 7);
            newDateFile = c.getTime();
            //END

            Log.d(Constants.TAG, "Data do Sistema: " + newDate);
            Log.d(Constants.TAG, "Data do arquivo original: " + dateFile);
            Log.d(Constants.TAG, "Data do novo arquivo Modificado : " + newDateFile);
            //Caso a data do arquivo seja inferior ou igual ele baixa
            if (newDateFile.before(newDate) || newDateFile.equals(newDate)) {
                Log.d(Constants.TAG, "Esta Baixando = true");

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
                Log.d(Constants.TAG, "download ready in "
                        + ((System.currentTimeMillis() - startTime) / 1000)
                        + " sec");
            }

            return null;
        } catch (IOException ioe) {
            Log.d("DownloadManager", "Error: " + ioe);
            return null;
        }
    }
}