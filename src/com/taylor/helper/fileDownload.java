package com.taylor.helper;

import com.taylor.design.WrapLayout;
import it.sauronsoftware.ftp4j.FTPFile;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.nio.file.Paths;

public class FileDownload implements Runnable {
    private FTPHandler FTP;
    private FTPFile input;
    private String output;

    public FileDownload(FTPHandler _FTP, FTPFile file, String saveDir){
        FTP = _FTP;
        input = file;
        output = saveDir;
    }

    @Override
    public void run() {
        JFrame DP = new JFrame("Downloading " + input.getName());
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new WrapLayout());
        DP.setContentPane(mainPanel);
        mainPanel.add(new JLabel("Downloading " + input.getName() + "to " + output));
        Dimension size = new Dimension(400,  75);
        DP.setSize(size);
        DP.setPreferredSize(size);
        DP.pack();
        DP.setVisible(true);
        try {
            FTP.downloadFile(input.getName(), new File(Paths.get(output, input.getName()).toAbsolutePath().toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        DP.dispose();
    }
}
