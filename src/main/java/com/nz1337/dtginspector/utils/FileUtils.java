package com.nz1337.dtginspector.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileUtils {

    public static List<File> getAllFiles(File directory) {
        File[] fList = directory.listFiles();
        assert fList != null;
        List<File> resultList = new ArrayList<>(Arrays.asList(fList));
        for (File file : fList) {
            if (file.isFile()) {
                resultList.add(file);
            } else if (file.isDirectory()) resultList.addAll(getAllFiles(file));
        }
        return resultList;
    }

    public static List<File> getOnlyFiles(File directory) {
        File[] fList = directory.listFiles();
        assert fList != null;
        List<File> resultList = new ArrayList<>(Arrays.asList(fList));
        for (File file : fList)
            if (file.isFile())
                resultList.add(file);
        return resultList;
    }

    public static String read(File file) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            StringBuilder inputBuffer = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) inputBuffer.append(line).append("\n");
            reader.close();
            return inputBuffer.toString().toLowerCase();
        } catch (Exception e) {
            if (!e.toString().toLowerCase().contains("filenotfound")) e.printStackTrace();
        }
        return "";
    }
}