package com.nz1337.dtginspector;

import com.nz1337.dtginspector.manager.InspectorManager;

public class Inspector {

    private final InspectorManager inspectorManager;

    public Inspector() throws InterruptedException {
        this.inspectorManager = new InspectorManager();
        final long start = System.currentTimeMillis();
        log("[*] Starting scan...");
        inspectorManager.scan();
        log("\n\n" +
                "__________                    .__   __          \n" +
                "\\______   \\ ____   ________ __|  |_/  |_  ______\n" +
                " |       _// __ \\ /  ___/  |  \\  |\\   __\\/  ___/\n" +
                " |    |   \\  ___/ \\___ \\|  |  /  |_|  |  \\___ \\ \n" +
                " |____|_  /\\___  >____  >____/|____/__| /____  >\n" +
                "        \\/     \\/     \\/                     \\/ \n\n");
        log("(SCAN) Finished after " + getDifference(start, System.currentTimeMillis()) + " seconds\n");
        if (!inspectorManager.isClean()) {
            log("[-] Infected paths :");
            inspectorManager.getInfections().keySet().forEach(path -> log(" - [" + path + "] | Infected by " + inspectorManager.getInfections().get(path)));
            log("[-] Be sure to remove all the grabber from your Discord by reinstalling Discord properly.");
        } else log("[+] DTGInspector didn't find any token grabber in your Discord.\nHowever, be careful not to open just any file.");
        Thread.sleep(Integer.MAX_VALUE);
    }

    private void log(String text) {
        System.out.println(text);
    }

    private double getDifference(long start, long end) {
        return Double.parseDouble(String.valueOf((end - start) / 1000));
    }
}