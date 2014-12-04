package com.taylor.helper;

import com.taylor.design.WrapLayout;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class uploadFile implements Runnable {
    private FTPHandler FTP;
    private String input;

    public uploadFile(FTPHandler _FTP, String file){
        FTP = _FTP;
        input = file;
    }

    @Override
    public void run() {
        JFrame DP = new JFrame("Uploading " + input);
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new WrapLayout());
        DP.setContentPane(mainPanel);
        mainPanel.add(new JLabel("Uploading " + input));
        DP.setSize(new Dimension(400,  75));
        DP.pack();
        DP.setVisible(true);
        try {
            FTP.uploadFile(new File(input));
            FTP.GUI.listFiles();
        } catch (Exception e) {
            e.printStackTrace();
        }
        DP.dispose();
    }
}
