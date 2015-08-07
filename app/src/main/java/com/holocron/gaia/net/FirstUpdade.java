package com.holocron.gaia.net;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.holocron.gaia.Constants;

import org.apache.http.util.ByteArrayBuffer;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
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
            File fileTest = new File(Constants.FILENAME);

            long startTime = System.currentTimeMillis();
            Log.d("ImageManager", "download begining");
            Log.d("ImageManager", "download url:" + url);
            Log.d("ImageManager", "downloaded file name:" + Constants.FILENAME);
                        /* Open a connection to that URL. */
            URLConnection ucon = url.openConnection();
            ucon.setReadTimeout(Constants.TIMEOUT_CONNECTION);
            ucon.setConnectTimeout(Constants.TIMEOUT_SOCKET);

            //long lastModified = ucon.getHeaderFieldDate("Last-Modified", currentTime);
            //Log.d("Donw", "last modified " + lastModified);
            Log.d("Donw", "Update " + fileTest.lastModified());
            //Log.d("Donw", "Diferença " + ((fileTest.lastModified() + Constants.TWOHOURS) - lastModified));
                        /*
                         * Define InputStreams to read from the URLConnection.
                         */

            Date date = new Date(currentTime);
            Date datee = new Date(fileTest.lastModified() + Constants.TWOHOURS);
            Log.d("ImageManager", "Tempo do android" + datee);

            Log.d("ImageManager", "Tempo do android" + date);

            if ((fileTest.lastModified() + Constants.TWOHOURS) <= currentTime) {
                Log.d("ImageManager", "Esta Baixando");

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
                FileOutputStream fos = new FileOutputStream(fileTest);
                fos.write(baf.toByteArray());
                fos.close();
                Log.d("ImageManager", "download ready in"
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