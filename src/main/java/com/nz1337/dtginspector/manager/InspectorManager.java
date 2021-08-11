package com.nz1337.dtginspector.manager;

import com.nz1337.dtginspector.processors.*;
import com.nz1337.dtginspector.type.GrabberType;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class InspectorManager {

    private final FolderSearcher folderSearcher;
    private final PasswordSearcher passwordSearcher;
    private final WebhookSearcher webhookSearcher;
    private final IndexSearcher indexSearcher;
    private final TokenSearcher tokenSearcher;
    private final HashMap<String, String> infections;
    private final ArrayList<String> directories;
    private final ArrayList<String> core;
    private boolean clean;

    public InspectorManager() {
        this.clean = true;
        this.infections = new HashMap<>();
        this.directories = new ArrayList<>();
        this.core = new ArrayList<>();
        this.registerAllVersions("Discord", "DiscordDevelopment", "DiscordCanary", "DiscordPTB");
        this.registerAllCore();
        this.tokenSearcher = new TokenSearcher(this, core, directories);
        this.folderSearcher = new FolderSearcher(this, core);
        this.indexSearcher = new IndexSearcher(this, core);
        this.passwordSearcher = new PasswordSearcher(this);
        this.webhookSearcher = new WebhookSearcher(this);
    }

    public void scan() {
        //this.searchAt(TokenSearcher.class);
        this.searchAt(IndexSearcher.class);
        this.searchAt(WebhookSearcher.class);
        this.searchAt(PasswordSearcher.class);
        this.searchAt(FolderSearcher.class);
    }

    private void searchAt(Class<?> c) {
        switch (c.getSimpleName()) {
            case "PasswordSearcher":
                this.passwordSearcher.scan();
                break;
            case "TokenSearcher":
                this.tokenSearcher.scan();
                break;
            case "WebhookSearcher":
                this.webhookSearcher.scan();
                break;
            case "FolderSearcher":
                this.folderSearcher.scan();
                break;
            case "IndexSearcher":
                this.indexSearcher.scan();
                break;
            default:
                System.out.println("[-] Cannot associate " + c.getSimpleName() + " with a scanner");
                break;
        }
    }

    public void add(File file, GrabberType type) {
        final String path = file.getAbsolutePath();
        final String grabberType = type.getGrabberType();
        if (this.infections.containsKey(path)) {
            this.infections.put(path, this.infections.get(path) + ", " + grabberType);
        } else this.infections.put(path, grabberType);
        if (this.isClean()) this.setClean(false);
    }

    private void registerAllVersions(String... discordType) {
        for (String type : discordType) {
            final File file = new File(System.getenv("LOCALAPPDATA") + "\\" + type + "\\");
            String[] files = file.list();
            if (files == null) return;
            for (String string : files)
                if (string.toLowerCase().startsWith("app-")) this.directories.add(file.getPath() + "\\" + string);
        }
    }

    private void registerAllCore() {
        this.directories.forEach(dir -> {
            final File file = new File(dir + "\\modules");
            String[] files = file.list();
            if (files == null) return;
            for (String string : files)
                if (string.startsWith("discord_desktop_core-")) core.add(file.getPath() + "\\" + string + "\\discord_desktop_core\\");
        });
    }

    public boolean isClean() {
        return clean;
    }

    private void setClean(boolean clean) {
        this.clean = clean;
    }

    public HashMap<String, String> getInfections() {
        return infections;
    }
}
