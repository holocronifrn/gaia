package com.holocron.gaia.repository.cache;

import android.content.Context;

import com.googlecode.jcsv.reader.CSVReader;
import com.googlecode.jcsv.reader.internal.CSVReaderBuilder;
import com.holocron.gaia.Constants;
import com.holocron.gaia.model.WeekDay;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;

public class CsvFileManagerRead {

    private Context context;

    public CsvFileManagerRead(Context context){
        this.context = context;
    }

    public CsvFileManagerRead(){}

    public String meatType(WeekDay day, boolean meatType) throws IOException {


        Reader reader = new FileReader(Constants.FILENAME);

        CSVReader<String[]> csvMeatReader = CSVReaderBuilder.newDefaultReader(reader);

        List<String[]> card = csvMeatReader.readAll();

        List<String> lunchWeek = Arrays.asList(card.get(3)[0].split(","));

        List<String> dinnerWeek = Arrays.asList(card.get(6)[0].split(","));

        if (meatType) {
            return lunchWeek.get(day.getValue());
        } else {
            return dinnerWeek.get(day.getValue());
        }
    }
}