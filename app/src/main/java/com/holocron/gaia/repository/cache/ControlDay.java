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


        //A classe Date so recebe o nome do mes em ingles e as inicias, caso contrário tem que se usar numeros
        if(returnData[5].equals(Constants.janeiro)){
            returnData[5] = "01";
        }else if(returnData[5].equals(Constants.fevereiro)){
            returnData[5] = "02";
        }else if(returnData[5].equals(Constants.marco)){
            returnData[5] = "03";
        }else if(returnData[5].equals(Constants.abril)){
            returnData[5] = "04";
        }else if(returnData[5].equals(Constants.maio)){
            returnData[5] = "05";
        }else if(returnData[5].equals(Constants.junho)){
            returnData[5] = "06";
        }else if(returnData[5].equals(Constants.julho)){
            returnData[5] = "07";
        }else if(returnData[5].equals(Constants.agosto)){
            returnData[5] = "08";
        }else if(returnData[5].equals(Constants.setembro)){
            returnData[5] = "09";
        }else if(returnData[5].equals(Constants.outubro)){
            returnData[5] = "10";
        }else if(returnData[5].equals(Constants.novembro)){
            returnData[5] = "11";
        }else /*if(returnData[5].equals(Constants.dezembro))*/{
            returnData[5] = "12";
        }

        return returnData[1] + "/" + returnData[5] + "/" + returnData[7];
    }
}
