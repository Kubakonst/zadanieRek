package com.britenet.repository;

import java.io.*;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

public class Configuration {

    public Map<String, String> configureConnection() {
        Map<String, String> configMap = new HashMap<>();
        String line;
        try {
            InputStream inputStream = new FileInputStream(new File("./configuration.txt"));
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, Charset.defaultCharset()))) {
                while ((line = bufferedReader.readLine()) != null) {
                    String[] parameters = line.split("=", 2);
                    if (parameters.length == 2) {
                        configMap.put(parameters[0], parameters[1]);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return configMap;
    }
}
