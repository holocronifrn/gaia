package com.holocron.gaia.net;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.holocron.gaia.Constants;
import com.holocron.gaia.repository.cache.FileManager;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

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
        try {
            URL url = new URL(Constants.URL);
            url.getHost();
            url.getFile();
            url.getPort();
            url.getUserInfo();
            URLConnection con = url.openConnection();
            BufferedInputStream buf = new BufferedInputStream(con.getInputStream());

            FileManager fileManager = new FileManager(context);
            fileManager.write(buf);

            buf.close();
            return "Download Concluido";
        } catch (IOException ioe) {
            return "Erro de Conexão.";
        }
    }

}
