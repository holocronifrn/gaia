package com.holocron.gaia.net;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.holocron.gaia.Constants;
import com.holocron.gaia.repository.cache.ControlDay;
import com.holocron.gaia.repository.cache.CsvFileManagerRead;
import com.holocron.gaia.repository.cache.FileManager;

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

    private Context context;
    private ProgressDialog dialog;

    //Dowload and Update
//    private long currentTime;
//    private File file;
//    private URL url;
//    private String downloadUrl;
//    private String fileName;
//    private long startTime;
//    private long lastModified;
//    private FileManager fileManager;
//    private BufferedInputStream bufferinstream;
//    private URLConnection uconn;

    public XlsxDownloadAsyncTask(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = new ProgressDialog(context);
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
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

//    private String downloadArquivoExcel() {
//        try {
////            file = new File(Constants.FILENAME);
////            downloadUrl = Constants.URL;
////            fileName = Constants.FILENAME;
////
////            currentTime = System.currentTimeMillis();
////            url = new URL(downloadUrl);
////            startTime = System.currentTimeMillis();
////
////            Log.d("DownloadManager", "download url:" + url);
////            Log.d("DownloadManager", "download file name:" + fileName);
////
////            uconn = url.openConnection();
////            uconn.setReadTimeout(Constants.TENSECONDS);
////            uconn.setConnectTimeout(Constants.TENSECONDS);
////
////            lastModified = uconn.getHeaderFieldDate("Last-Modified", currentTime);
////
////            Log.d("Donw", "last modified " + lastModified);
////            Log.d("Donw", "Update " + file.lastModified());
////
////            Date data = new Date(lastModified);
////            Log.d("Donw", "last modified " + data);
////
////            Date dataa = new Date(file.lastModified());
////            Log.d("Donw", "last modified " + dataa);
////
////            InputStream is = uconn.getInputStream();
////            bufferinstream = new BufferedInputStream(is);
////
//////            fileManager = new FileManager(context, file);
////
////
//////            if ((file.lastModified() + Constants.TWOHOURS) < (lastModified)) {
//////                fileManager.write(bufferinstream);
//////                Log.d("DownloadManager", "download ready in" + ((System.currentTimeMillis() - startTime) / 1000) + "sec");
//////
//////            }
////
////            ByteArrayBuffer baf = new ByteArrayBuffer(5000);
////            int current = 0;
////            while((current = bufferinstream.read()) != -1){
////                baf.append((byte) current);
////            }
////
////            FileOutputStream fos = new FileOutputStream( file);
////            fos.write(baf.toByteArray());
////            fos.flush();
////            fos.close();
//            URL url = new URL(Constants.URL);
//            File file = new File(Constants.FILENAME);
//
//            long startTime = System.currentTimeMillis();
//            Log.d("DownloadManager", "download url:" + url);
//            Log.d("DownloadManager", "download file name:" + Constants.FILENAME);
//
//            URLConnection uconn = url.openConnection();
//            uconn.setReadTimeout(Constants.TENSECONDS);
//            uconn.setConnectTimeout(Constants.TENSECONDS);
//
//            InputStream is = uconn.getInputStream();
//            BufferedInputStream bufferinstream = new BufferedInputStream(is);
//
////<<<<<<< HEAD
//            ByteArrayBuffer baf = new ByteArrayBuffer(5000);
//            int current = 0;
//            while ((current = bufferinstream.read()) != -1) {
//                baf.append((byte) current);
//            }
//
//            FileOutputStream fos = new FileOutputStream(file);
//            fos.write(baf.toByteArray());
//            fos.flush();
//            fos.close();
////=======
////            fileManager = new FileManager(context, file);
////
////
////            bufferinstream = new BufferedInputStream(is);
////
////            if ((file.lastModified() + Constants.TWOHOURS) < (lastModified)) {
////                fileManager.write(bufferinstream);
////                Log.d("DownloadManager", "download ready in" + ((System.currentTimeMillis() - startTime) / 1000) + "sec");
////
////            }
////
////>>>>>>> a36cb3b6ed422cdc87822abc7e2395982f2a9674
//            return "Cardápio Atualizado!";
//        } catch (IOException ioe) {
//            Log.d("DownloadManager", "Error: " + ioe);
//            return "Erro de Conexão.";
//        }
//    }

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

            Date dateFile = new Date(currentTime);
            Date newDateFile = new Date(currentTime);
            Date newDate = new Date(currentTime);
            //Log.d("ImageManager", "Tempo do android" + datee);

            if(fileCard.exists()){
                Log.d(Constants.TAG, "Existe");
            }else{
                Log.d(Constants.TAG, "Não Existe");
            }

            if(fileCard.exists()) {//Caso o arquivo não existe será atribuido a ele data ficticias para que possa ser realizado o dowload
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
                    Log.d(Constants.TAG, "Erro na conversão ");
                }
                //Incrementando 7 dias na data com base na data anterior
                Calendar c = Calendar.getInstance();
                c.setTime(dateFile);
                c.add(Calendar.DATE, 7);
                newDateFile = c.getTime();
                //END
            }

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

            return "Cardápio Atualizado!";
        } catch (IOException ioe) {
            Log.d("DownloadManager", "Error: " + ioe);
            return "Erro de Conexão.";
        }
    }
}