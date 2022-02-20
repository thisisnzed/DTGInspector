package com.nz1337.dtginspector.processors;

import com.nz1337.dtginspector.manager.InspectorManager;
import com.nz1337.dtginspector.type.GrabberType;
import com.nz1337.dtginspector.utils.FileUtils;

import java.io.File;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class WebhookSearcher {

    private final String[] illegalStrings = {"\\Local Storage\\leveldb", "[\\w-]{24}\\.[\\w-]{6}\\.[\\w-]{27}", "mfa\\.[\\w-]{84}", "discord token grabber", "&& r.default", "r&&r.__esmodule&&r.default", "req=webpackjsonp.push", "stangrab", "token grab", "tokengrab", "wtfismyip", "4n4rchy", "forgegrabber", "fgrabber", "/api/webhooks/"};
    private final String[] extensions = {".lnk", ".exe", ".java", ".jar", ".js", ".asar", ".txt", ".config", ".cfg", ".dat", ".py"};
    private final InspectorManager inspectorManager;

    public WebhookSearcher(final InspectorManager inspectorManager) {
        this.inspectorManager = inspectorManager;
    }

    public void scan() {
        System.out.println("[*] Searching for webhook sender...");
        final File file = new File(System.getProperty("user.home"));
        final AtomicInteger count = new AtomicInteger();
        FileUtils.getAllFiles(file, false).forEach(files -> {
            if (files.exists() && Arrays.stream(this.extensions).anyMatch(ext -> files.getAbsolutePath().endsWith(ext))) {
                System.out.println("[Webhook Searcher Worker/" + count.getAndIncrement() + "] " + files.getAbsolutePath());
                if (Arrays.stream(this.illegalStrings).anyMatch(FileUtils.read(files)::contains))
                    this.inspectorManager.add(files, GrabberType.WEBHOOK);
            }
        });
    }
}