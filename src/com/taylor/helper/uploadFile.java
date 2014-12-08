package com.taylor.helper;

import com.taylor.ActionQue.ActionType;
import com.taylor.design.WrapLayout;
import com.taylor.manager.FileAndDirectoryManager;
import com.taylor.ActionQue.Action;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class UploadFile implements Runnable {
    private FTPHandler FTP;
    private String input;
    private FileAndDirectoryManager fileAndDirectoryManager;
    private String uploadDirectory;
    public UploadFile(FTPHandler _FTP, String file, FileAndDirectoryManager _fileAndDirectoryManager, String _uploadDirectory){
        FTP = _FTP;
        input = file;
        fileAndDirectoryManager = _fileAndDirectoryManager;
        uploadDirectory = _uploadDirectory;
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
            fileAndDirectoryManager.unPause();
            Action reloadDirectory = new Action(ActionType.RELOADDIRECTORY);
            reloadDirectory.setDirectory(uploadDirectory);
            fileAndDirectoryManager.actionQue.actions.add(reloadDirectory);
            new Thread(fileAndDirectoryManager.actionQue).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        DP.dispose();
    }
}
