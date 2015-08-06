package com.holocron.gaia.net;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.holocron.gaia.Constants;
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

    public XlsxDownloadAsyncTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = new ProgressDialog(context);
        dialog.setMessage("Baixando Menu...");
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
        File file = new File(context.getCacheDir(),Constants.FILENAME);
        String downloadUrl = Constants.URL;
        String fileName = Constants.FILENAME;

        try {
//            File root = android.os.Environment.getExternalStorageDirectory();
//            File dir = new File(root.getAbsolutePath() + "/myclock/databases");
//            if (dir.exists() == false) {
//                dir.mkdirs();
//            }

            long currentTime = System.currentTimeMillis();
            URL url = new URL(downloadUrl);
            long startTime = System.currentTimeMillis();

            Log.d("DownloadManager", "download url:" + url);
            Log.d("DownloadManager", "download file name:" + context.getCacheDir() + fileName);

            URLConnection uconn = url.openConnection();
            uconn.setReadTimeout(100000);
            uconn.setConnectTimeout(100000);

            long lastModified = uconn.getHeaderFieldDate("Last-Modified", currentTime);

            Log.d("Donw", "last modified " + lastModified);
            Log.d("Donw", "Update " + file.lastModified());

            Date data = new Date(lastModified);
            Log.d("Donw", "last modified " + data);

            Date dataa = new Date(file.lastModified());
            Log.d("Donw", "last modified " + dataa);

            InputStream is = uconn.getInputStream();

            FileManager fileManager = new FileManager(context, file);
            BufferedInputStream bufferinstream = new BufferedInputStream(is);

            if ((file.lastModified() + 86400000) < (lastModified)) {
                fileManager.write(bufferinstream);

            } else {
                return "Planilha Já Atualizada!";
            }
            Log.d("DownloadManager", "download ready in" + ((System.currentTimeMillis() - startTime) / 1000) + "sec");
            return "Arquivo Baixado";
        } catch (IOException ioe) {
            Log.d("DownloadManager", "Error: " + ioe);
            return "Erro de Conexão.";
        }

//        try {
//            long currentTime = System.currentTimeMillis();
//
//            URL url = new URL(Constants.URL);
//            url.getHost();
//            url.getFile();
//            url.getPort();
//            url.getUserInfo();
//            URLConnection con = (URLConnection) url.openConnection();
//
//            long lastModified = con.getHeaderFieldDate("Last-Modified", currentTime);
//
//            Log.d("CSV", "last modified "+lastModified);
//
//            FileManager fileTest = new FileManager();
//
//            Log.d("CSV", "Update "+ fileTest.lastModified());
//
//            if(fileTest.lastModified() < lastModified) {
//                BufferedInputStream buf = new BufferedInputStream(con.getInputStream());
//
//                FileManager fileManager = new FileManager(context);
//
//                fileManager.write(buf);
//
//                buf.close();
//                return "Arquivo Baixado";
//            }else {
//                return "Planilha Já Atualizada!";
//            }
//        } catch (IOException ioe) {
//            return "Erro de Conexão.";
//        }
    }
}