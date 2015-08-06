package com.holocron.gaia.repository.cache;

import android.content.Context;

import com.holocron.gaia.Constants;

import org.apache.http.util.ByteArrayBuffer;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by jhoanes on 30/07/15.
 */
public class FileManager {

    private Context context;
    private File file;

    public FileManager(Context context, File file) {
        this.context = context;
        this.file = file;
    }

    public void write(BufferedInputStream buf) throws IOException {

        try {
            ByteArrayBuffer baf = new ByteArrayBuffer(5000);
            int current = 0;
            while ((current = buf.read()) != -1) {
                baf.append((byte) current);
            }

            FileOutputStream fos = new FileOutputStream(file);
            fos.write(baf.toByteArray());
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public File read() {
        File file = new File(context.getCacheDir(), Constants.FILENAME).getAbsoluteFile();
        return file;
    }

    public long lastModified() {
        long lastModified = 0;
        if (isExists()) {
            return read().lastModified();
        } else {
            return 0;
        }
    }

    public boolean isExists() {
        return read().exists();
    }

    public void removerArquivos(File f) {
        // Se o arquivo passado for um diretório
        if (f.isDirectory()) {
                /* Lista todos os arquivos do diretório em um array
                   de objetos File */
            File[] files = f.listFiles();
            // Identa a lista (foreach) e deleta um por um
            for (File file : files) {
                file.delete();
            }
        }
    }
}