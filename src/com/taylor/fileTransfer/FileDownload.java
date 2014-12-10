package com.taylor.fileTransfer;

import com.taylor.design.FileObject;
import com.taylor.design.WrapLayout;
import com.taylor.helper.FTPHandler;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.nio.file.Paths;

public class FileDownload implements Runnable {
    private FTPHandler FTP;
    private FileObject fileObject;
    private String output;

    public FileDownload(FTPHandler _FTP, FileObject _fileObject, String saveDir) {
        FTP = _FTP;
        output = saveDir;
        fileObject = _fileObject;
    }

    @Override
    public void run() {
        JFrame DP = new JFrame("Downloading " + fileObject.file.getName());
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new WrapLayout());
        DP.setContentPane(mainPanel);
        mainPanel.add(new JLabel("Downloading " + fileObject.file.getName() + " to " + output));
        Dimension size = new Dimension(400, 75);
        DP.setSize(size);
        DP.setPreferredSize(size);
        DP.pack();
        DP.setVisible(true);
        try {
            FTP.downloadFile(fileObject.getFullPath(), new File(Paths.get(output, fileObject.file.getName()).toAbsolutePath().toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        DP.dispose();
    }
}
