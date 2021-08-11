package com.nz1337.dtginspector.processors;

import com.nz1337.dtginspector.manager.InspectorManager;
import com.nz1337.dtginspector.type.GrabberType;
import com.nz1337.dtginspector.utils.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class TokenSearcher {

    private final String[] illegalStrings = {"\\Local Storage\\leveldb", "[\\w-]{24}\\.[\\w-]{6}\\.[\\w-]{27}", "mfa\\.[\\w-]{84}", "discord token grabber", "forgegrabber", "fgrabber", "&& r.default", "r&&r.__esmodule&&r.default", "req=webpackjsonp.push", "stangrab", "token grab", "tokengrab", "wtfismyip", "grabber", "4n4rchy", "injectors", "moddir", "webhook"};
    private final String[] extensions = {".py", ".java", ".jar", ".js", ".asar"};
    private final InspectorManager inspectorManager;
    private final ArrayList<String> directories;
    private final ArrayList<String> core;

    public TokenSearcher(InspectorManager inspectorManager, ArrayList<String> core, ArrayList<String> directories) {
        this.inspectorManager = inspectorManager;
        this.directories = directories;
        this.core = core;
    }

    public void scan() {
        final AtomicInteger count = new AtomicInteger();
        System.out.println("[*] Searching for token grabber injection...");
        this.core.forEach(dir -> {
            final File file = new File(dir + "\\index.js");
            if (file.exists()) {
                System.out.println("[Token Searcher Worker/" + count.getAndIncrement() + "] " + file.getAbsolutePath());
                if (Arrays.stream(illegalStrings).anyMatch(FileUtils.read(file)::contains))
                    this.inspectorManager.add(file, GrabberType.TOKEN);
            }
        });
        directories.forEach(dir -> {
            FileUtils.getAllFiles(new File(dir)).forEach(file -> {
                if (Arrays.stream(extensions).anyMatch(ext -> file.getAbsolutePath().endsWith(ext))) {
                    System.out.println("[Token Searcher Worker/" + count.getAndIncrement() + "] " + file.getAbsolutePath());
                    if (Arrays.stream(illegalStrings).anyMatch(FileUtils.read(file)::contains))
                        this.inspectorManager.add(file, GrabberType.TOKEN);
                }
            });
        });
    }
}