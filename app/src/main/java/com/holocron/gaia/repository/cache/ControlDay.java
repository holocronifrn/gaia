package com.holocron.gaia.repository.cache;

import com.googlecode.jcsv.reader.CSVReader;
import com.googlecode.jcsv.reader.internal.CSVReaderBuilder;
import com.holocron.gaia.Constants;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;

/**
 * Created by jhoanes on 08/08/15.
 */
public class ControlDay {

    public String data() throws IOException {

        Reader reader = new FileReader(Constants.FILENAME);

        CSVReader<String[]> csvMeatReader = CSVReaderBuilder.newDefaultReader(reader);

        List<String[]> card = csvMeatReader.readAll();

        List<String> weekDay = Arrays.asList(card.get(0)[0]);

        String returnData[] = weekDay.get(0).split("[ ][,]");

        return returnData[1] + "/" + returnData[5] + "/" + returnData[7];
    }
}
