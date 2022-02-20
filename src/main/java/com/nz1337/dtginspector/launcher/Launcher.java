package com.nz1337.dtginspector.launcher;

import com.nz1337.dtginspector.Inspector;

public class Launcher {

    public static void main(String[] args) {
        try {
            new Inspector();
        } catch (final InterruptedException exception) {
            exception.printStackTrace();
        }
    }
}
