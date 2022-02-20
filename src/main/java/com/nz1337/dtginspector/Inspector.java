package com.nz1337.dtginspector;

import com.nz1337.dtginspector.manager.InspectorManager;

public class Inspector {

    private final InspectorManager inspectorManager;

    public Inspector() throws InterruptedException {
        this.inspectorManager = new InspectorManager();
        final long start = System.currentTimeMillis();
        this.log("[*] Starting scan...");
        this.inspectorManager.scan();
        this.log("\n\n" +
                "__________                    .__   __          \n" +
                "\\______   \\ ____   ________ __|  |_/  |_  ______\n" +
                " |       _// __ \\ /  ___/  |  \\  |\\   __\\/  ___/\n" +
                " |    |   \\  ___/ \\___ \\|  |  /  |_|  |  \\___ \\ \n" +
                " |____|_  /\\___  >____  >____/|____/__| /____  >\n" +
                "        \\/     \\/     \\/                     \\/ \n\n");
        this.log("(SCAN) Finished after " + this.getDifference(start, System.currentTimeMillis()) + " seconds\n");
        if (!this.inspectorManager.isClean()) {
            this.log("[-] Infected paths :");
            this.inspectorManager.getInfections().keySet().forEach(path -> log(" - [" + path + "] | Infected by " + this.inspectorManager.getInfections().get(path)));
            this.log("[-] Be sure to remove all the grabber from your Discord by reinstalling Discord properly.");
        } else this.log("[+] DTGInspector didn't find any token grabber in your Discord.\nHowever, be careful not to open just any file.");
        Thread.sleep(Integer.MAX_VALUE);
    }

    private void log(final String text) {
        System.out.println(text);
    }

    private double getDifference(final long start, final long end) {
        return Double.parseDouble(String.valueOf((end - start) / 1000));
    }
}