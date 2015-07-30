package com.holocron.gaia.repository.cache;

import android.content.Context;

import com.holocron.gaia.Constants;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by jhoanes on 30/07/15.
 */
public class FileManager {

    private Context context;

    public FileManager(Context context) {
        this.context = context;
    }

    FileOutputStream out;

    public void write(BufferedInputStream buf) throws IOException {
        try {
            out = context.openFileOutput(Constants.FILENAME, Context.MODE_PRIVATE);
            int i = 0;
            byte[] bytesIn = new byte[1024];
            while ((i = buf.read(bytesIn)) >= 0) {
                out.write(bytesIn, 0, i);
            }
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public File read(){
        File file = new File(context.getFilesDir(), Constants.FILENAME);
        return file;
    }
}
