package com.nz1337.dtginspector.processors;

import com.nz1337.dtginspector.manager.InspectorManager;
import com.nz1337.dtginspector.type.GrabberType;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class IndexSearcher {

    private final InspectorManager inspectorManager;
    private final ArrayList<String> directories;

    public IndexSearcher(InspectorManager inspectorManager, ArrayList<String> directories) {
        this.inspectorManager = inspectorManager;
        this.directories = directories;
    }

    public void scan() {
        final AtomicInteger count = new AtomicInteger();
        System.out.println("[*] Searching for null index injection...");
        this.directories.forEach(dir -> {
            final File file = new File(dir + "\\index.js");
            if (file.exists()) {
                System.out.println("[Index Searcher Worker/" + count.getAndIncrement() + "] " + file.getAbsolutePath());
                final int length = this.getSize(file);
                if (length > 43 || length < 2) this.inspectorManager.add(file, GrabberType.INVALID);
            }
        });
    }

    private int getSize(File file) {
        try {
            final BufferedReader reader = new BufferedReader(new FileReader(file));
            final StringBuilder inputBuffer = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) inputBuffer.append(line);
            reader.close();
            return inputBuffer.toString().replaceAll("\n", "").length();
        } catch (final Exception exception) {
            exception.printStackTrace();
        }
        return 40;
    }
}
