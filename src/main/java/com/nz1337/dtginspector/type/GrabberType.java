package com.nz1337.dtginspector.type;

public enum GrabberType {

    TOKEN("Token Stealer"),
    PASSWORD("Password Stealer"),
    FOLDER("Malicious Folder"),
    INVALID("Invalid content length"),
    WEBHOOK("Webhook Sender");

    private final String grabberType;

    GrabberType(final String grabberType) {
        this.grabberType = grabberType;
    }

    public String getGrabberType() {
        return grabberType;
    }
}