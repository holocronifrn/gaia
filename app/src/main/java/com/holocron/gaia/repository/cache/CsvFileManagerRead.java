package com.holocron.gaia.repository.cache;

import android.content.Context;

import com.googlecode.jcsv.reader.CSVReader;
import com.googlecode.jcsv.reader.internal.CSVReaderBuilder;
import com.holocron.gaia.Constants;
import com.holocron.gaia.activity.MainActivity;
import com.holocron.gaia.net.XlsxDownloadAsyncTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

/**
 * Created by jhoanes on 05/08/15.
 */
public class CsvFileManagerRead {

    private Context context;

    public CsvFileManagerRead(Context context){
        this.context = context;
    }

    public CsvFileManagerRead(){}

    public String meatType(int day, boolean meatType) throws IOException {

        String[] stringLunch = new String[6];
        String[] stringDinner = new String[6];

        Reader reader = new FileReader(Constants.FILENAME);

        CSVReader<String[]> csvMeatReader = CSVReaderBuilder.newDefaultReader(reader);

        List<String[]> card = csvMeatReader.readAll();

        List<String> lunchWeek = Arrays.asList(card.get(3)[0].split(","));

        List<String> dinnerWeek = Arrays.asList(card.get(6)[0].split(","));

        //Fazer nova classe para retornar apenas a string;
        if (meatType) {

            int aux = 0;

            stringLunch[aux] = "";
            stringDinner[aux] = "";

            for (String meat : lunchWeek) {

                stringLunch[aux + 1] = meat;

                aux++;

            }

            for (int i = 0; i < 6; i++){

                if (day == i)
                    return stringLunch[i];

            }


        } else {

            int aux = 0;

            for (String meat : dinnerWeek) {

                stringDinner[aux + 1] = meat;

                aux++;

            }

            for (int i = 0; i < 6; i++){

                if (day == i)
                    return stringDinner[i];

            }
        }
        return "";
    }
}