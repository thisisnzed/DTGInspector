package com.nz1337.dtginspector.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileUtils {

    public static List<File> getAllFiles(final File directory, final boolean dir) {
        final File[] fList = directory.listFiles();
        assert fList != null;
        List<File> resultList = new ArrayList<>(Arrays.asList(fList));
        for (File file : fList) {
            if (file.isFile()) {
                resultList.add(file);
            } else if (file.isDirectory() && dir) resultList.addAll(getAllFiles(file, true));
        }
        return resultList;
    }

    public static String read(final File file) {
        try {
            final BufferedReader reader = new BufferedReader(new FileReader(file));
            final StringBuilder inputBuffer = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) inputBuffer.append(line).append("\n");
            reader.close();
            return inputBuffer.toString().toLowerCase();
        } catch (final Exception exception) {
            if (!exception.toString().toLowerCase().contains("filenotfound")) exception.printStackTrace();
        }
        return "";
    }
}