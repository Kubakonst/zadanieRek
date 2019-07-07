package com.britenet;

import com.britenet.converter.Converter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;
import java.util.Scanner;

class Application {

    private Map<String, Converter> converters;

    Application(Map<String, Converter> converters) {
        this.converters = converters;
    }

    void convert() {
        String s;
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the file path");
        s = sc.nextLine();
        try {
            InputStream inputStream = new FileInputStream(new File(s));
            System.out.println("File loaded");
            String ext = "";

            int i = s.lastIndexOf('.');
            if (i > 0) {
                ext = s.substring(i + 1);
            }

            if (converters.containsKey(ext)) {
                Converter converter = converters.get(ext);
                converter.convert(inputStream);
            } else {
                System.out.println("Wrong file type");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


}