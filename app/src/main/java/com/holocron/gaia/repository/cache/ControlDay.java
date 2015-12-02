package com.holocron.gaia.repository.cache;

import android.util.Log;

import com.googlecode.jcsv.reader.CSVReader;
import com.googlecode.jcsv.reader.internal.CSVReaderBuilder;
import com.holocron.gaia.Constants;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;

public class ControlDay {

    public String data() throws IOException {

        Log.d("Activity1", "class ControlDay init");

        Reader reader = new FileReader(Constants.FILENAME);

        Log.d("Activity1", "class ControlDay file");

        CSVReader<String[]> csvMeatReader = CSVReaderBuilder.newDefaultReader(reader);

        Log.d("Activity1", "class ControlDay csv");

        List<String[]> card = csvMeatReader.readAll();

        List<String> weekDay = Arrays.asList(card.get(0)[0]);

        String returnData[];
        String returnData2[];

        try{
            returnData = weekDay.get(0).split(" ");
            returnData2 = returnData[7].split(",");
        }catch (Exception e){
            return "16/10/2014";
        }

        Log.d("Activity1", "class ControlDay listCsv");
        returnData[5] = returnData[5].toUpperCase();

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
        }else if(returnData[5].equals(Constants.dezembro)){
            returnData[5] = "12";
        } else{
            return "16/10/2014";
        }
        return returnData[1] + "/" + returnData[5] + "/" + returnData2[0];
    }
}