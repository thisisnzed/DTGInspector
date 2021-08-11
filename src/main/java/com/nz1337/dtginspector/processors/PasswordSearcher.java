package com.nz1337.dtginspector.processors;

import com.nz1337.dtginspector.manager.InspectorManager;
import com.nz1337.dtginspector.type.GrabberType;

import java.io.File;

public class PasswordSearcher {

    private final InspectorManager inspectorManager;

    public PasswordSearcher(InspectorManager inspectorManager) {
        this.inspectorManager = inspectorManager;
    }

    public void scan() {
        System.out.println("[*] Searching for password grabber injection...");
        final String userDir = System.getProperty("user.home");
        final File passwordList = new File(userDir + "\\password-list.txt");
        if (passwordList.exists()) this.inspectorManager.add(passwordList, GrabberType.PASSWORD);
        final File grabbedPass = new File(userDir + "\\grabbedpass.npg");
        if (grabbedPass.exists()) this.inspectorManager.add(grabbedPass, GrabberType.PASSWORD);
        final File infinityMat = new File(userDir + "\\.java\\infinityMat0.lock");
        if (infinityMat.exists()) this.inspectorManager.add(infinityMat, GrabberType.PASSWORD);
        final File magneraGrabber = new File(userDir + "\\.cache\\magn3r4.lock");
        if (magneraGrabber.exists()) this.inspectorManager.add(magneraGrabber, GrabberType.PASSWORD);
    }
}