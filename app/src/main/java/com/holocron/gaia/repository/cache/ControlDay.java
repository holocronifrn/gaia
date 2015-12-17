package com.holocron.gaia.repository.cache;

import android.util.Log;

import com.googlecode.jcsv.reader.CSVReader;
import com.googlecode.jcsv.reader.internal.CSVReaderBuilder;
import com.holocron.gaia.Constants;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;

public class ControlDay {

    public String data() throws IOException {

        //Log.d("Activity1", "class ControlDay init");

        Reader reader = new FileReader(Constants.FILENAME);

        //Log.d("Activity1", "class ControlDay file");

        CSVReader<String[]> csvMeatReader = CSVReaderBuilder.newDefaultReader(reader);

        //Log.d("Activity1", "class ControlDay csv");

        List<String[]> card = csvMeatReader.readAll();

        List<String> weekDay = Arrays.asList(card.get(0)[0]);

        String returnData[];
        String returnData2[];

        try{
            returnData = weekDay.get(0).split(" ");
            returnData2 = returnData[6].split(",");
        }catch (Exception e){
            //Log.d("DataAt1", "Entrou aqui!");
            return "16/10/2014";
        }

        //Log.d("Activity1", "class ControlDay listCsv");
        returnData[4] = returnData[4].toUpperCase();

        //Log.d("DataAt1", returnData[4]);

        //A classe Date so recebe o nome do mes em ingles e as inicias, caso contrário tem que se usar numeros
        if(returnData[4].equals(Constants.janeiro)){
            returnData[4] = "01";
        }else if(returnData[4].equals(Constants.fevereiro)){
            returnData[4] = "02";
        }else if(returnData[4].equals(Constants.marco)){
            returnData[4] = "03";
        }else if(returnData[4].equals(Constants.abril)){
            returnData[4] = "04";
        }else if(returnData[4].equals(Constants.maio)){
            returnData[4] = "05";
        }else if(returnData[4].equals(Constants.junho)){
            returnData[4] = "06";
        }else if(returnData[4].equals(Constants.julho)){
            returnData[4] = "07";
        }else if(returnData[4].equals(Constants.agosto)){
            returnData[4] = "08";
        }else if(returnData[4].equals(Constants.setembro)){
            returnData[4] = "09";
        }else if(returnData[4].equals(Constants.outubro)){
            returnData[4] = "10";
        }else if(returnData[4].equals(Constants.novembro)){
            returnData[4] = "11";
        }else if(returnData[4].equals(Constants.dezembro)){
            //Log.d("DataAt1", "Sávio é gay!");
            returnData[4] = "12";
        } else{
            //Log.d("DataAt1", "Sávio não é gay!");
            return "16/10/2014";
        }
        return returnData[1] + "/" + returnData[5] + "/" + returnData2[0];
    }

    public String dataAtualizacao() throws IOException {

        Reader reader = new FileReader(Constants.FILENAME);

        CSVReader<String[]> csvMeatReader = CSVReaderBuilder.newDefaultReader(reader);

        List<String[]> card = csvMeatReader.readAll();

        String a[] = card.get(0)[0].split(",");

        //a[0].toLowerCase();

        Log.d("DataAt", a[0]);

        return a[0];
    }
}