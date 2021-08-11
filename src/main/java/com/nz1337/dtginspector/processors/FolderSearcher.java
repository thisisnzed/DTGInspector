package com.nz1337.dtginspector.processors;

import com.nz1337.dtginspector.manager.InspectorManager;
import com.nz1337.dtginspector.type.GrabberType;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class FolderSearcher {

    private final InspectorManager inspectorManager;
    private final ArrayList<String> core;

    public FolderSearcher(InspectorManager inspectorManager, ArrayList<String> core) {
        this.inspectorManager = inspectorManager;
        this.core = core;
    }

    public void scan() {
        final AtomicInteger count = new AtomicInteger();
        System.out.println("[*] Searching for malicious folder searcher...");
        final File download = new File(System.getenv("LOCALAPPDATA") + "\\Downloads");
        if (download.exists()) {
            System.out.println("[Folder Searcher Worker/" + count.getAndIncrement() + "] " + download.getAbsolutePath());
            this.inspectorManager.add(download, GrabberType.FOLDER);
        }
        this.core.forEach(dir -> {
            final File pirateStealer = new File(dir + "\\PirateStealerBTW");
            if (pirateStealer.exists()) {
                System.out.println("[Folder Searcher Worker/" + count.getAndIncrement() + "] " + pirateStealer.getAbsolutePath());
                this.inspectorManager.add(pirateStealer, GrabberType.FOLDER);
            }
        });
    }
}
