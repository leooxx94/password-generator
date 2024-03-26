package com.passgen;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;

public class App 
{
    public static void main( String[] args ) throws IOException, CsvException
    {
        InputStream is = new BufferedInputStream(new FileInputStream("contatti.csv"));
        String source = "contatti.csv";
        String dest = "contatti_pass.csv";
        EditCSV(source, dest, is);
    }

    public static String passGen() throws IOException, CsvException{
        String str = "";
        ArrayList<String> ar = new ArrayList<String>();
        String[] list = {"a","b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
        String[] special = {"!","£","$","%","&","(",")","=","?","@","*"};
        int random = 0;  // 0 to 25
        int randomSpec = 0;  // 0 to 11

            
            for(int j = 0; j < 2; j++){ //2 maiuscole
                random = (int)(Math.random() * 26);
                ar.add(list[random].toUpperCase());
            }
            for(int j = 0; j < 4; j++){ //4 minuscole
                random = (int)(Math.random() * 26);
                ar.add(list[random]);
            }
            
            for(int j = 0; j < 2; j++){ //2 speciali
                randomSpec = (int)(Math.random() * 11);
                ar.add(special[randomSpec]);
            }

        Collections.shuffle(ar);
        return str = String.join("", ar);
    }

    public static int count(InputStream is) throws IOException {
        try {
        byte[] c = new byte[1024];
        int count = 0;
        int readChars = 0;
        boolean empty = true;
        while ((readChars = is.read(c)) != -1) {
            empty = false;
            for (int i = 0; i < readChars; ++i) {
                if (c[i] == '\n') {
                    ++count;
                }
            }
        }
        return (count == 0 && !empty) ? 1 : count;
        } finally {
        is.close();
       }
    }

    public static void EditCSV(String source, String dest, InputStream is) throws IOException, CsvException{
        CSVReader r = new CSVReader(new FileReader(source));
        List<String[]> body = r.readAll();
        int c = count(is);

        for(int i = 1; i < c; i++){
            body.get(i)[2] = passGen();
        }

        r.close();

        CSVWriter w = new CSVWriter(new FileWriter(dest));
        w.writeAll(body);
        w.flush();
        w.close();
    }

}
