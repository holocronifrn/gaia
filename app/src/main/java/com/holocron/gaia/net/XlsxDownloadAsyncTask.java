package com.holocron.gaia.net;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.holocron.gaia.Constants;
import com.holocron.gaia.repository.cache.CsvFileManagerRead;
import com.holocron.gaia.repository.cache.FileManager;

import java.io.BufferedInputStream;
import java.io.File;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

public class XlsxDownloadAsyncTask extends AsyncTask<Void, Void, String> {

    private Context context;
    private ProgressDialog dialog;

    //Dowload and Update
    private long currentTime;
    private File file;
    private URL url;
    private String downloadUrl;
    private String fileName;
    private long startTime;
    private long lastModified;
    private FileManager fileManager;
    private BufferedInputStream bufferinstream;
    private URLConnection uconn;

    public XlsxDownloadAsyncTask(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
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

    private String downloadArquivoExcel() {
        file = new File(context.getCacheDir(), Constants.FILENAME);
        downloadUrl = Constants.URL;
        fileName = Constants.FILENAME;

        try {
            currentTime = System.currentTimeMillis();
            url = new URL(downloadUrl);
            startTime = System.currentTimeMillis();

            Log.d("DownloadManager", "download url:" + url);
            Log.d("DownloadManager", "download file name:" + context.getCacheDir() + fileName);

            uconn = url.openConnection();
            uconn.setReadTimeout(Constants.TENSECONDS);
            uconn.setConnectTimeout(Constants.TENSECONDS);

            lastModified = uconn.getHeaderFieldDate("Last-Modified", currentTime);

            Log.d("Donw", "last modified " + lastModified);
            Log.d("Donw", "Update " + file.lastModified());

            Date data = new Date(lastModified);
            Log.d("Donw", "last modified " + data);

            Date dataa = new Date(file.lastModified());
            Log.d("Donw", "last modified " + dataa);

            InputStream is = uconn.getInputStream();

            fileManager = new FileManager(context, file);


            bufferinstream = new BufferedInputStream(is);

            if ((file.lastModified() + Constants.TWOHOURS) < (lastModified)) {
                fileManager.write(bufferinstream);
                Log.d("DownloadManager", "download ready in" + ((System.currentTimeMillis() - startTime) / 1000) + "sec");

            }

            return "Cardápio Atualizado!";
        } catch (IOException ioe) {
            Log.d("DownloadManager", "Error: " + ioe);
            return "Erro de Conexão.";
        }
    }
}