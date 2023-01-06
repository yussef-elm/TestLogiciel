package com.enseirb.tcas;

import javafx.scene.control.TextArea;

public class TCASAlert {
    private TextArea messageArea;

    public TCASAlert(TextArea messageArea) {
        this.messageArea = messageArea;
    }

    public void sendWarning(String message){
        if (messageArea!=null)
            messageArea.setText(message);
        System.out.println(message);
    }


}
