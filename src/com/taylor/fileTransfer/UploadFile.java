package com.taylor.fileTransfer;

import com.taylor.ActionQue.ActionType;
import com.taylor.design.Notification;
import com.taylor.design.WrapLayout;
import com.taylor.helper.FTPHandler;
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
    private Action queAction;
    public UploadFile(FTPHandler _FTP, FileAndDirectoryManager _fileAndDirectoryManager, Action queAction){
        FTP = _FTP;
        input = queAction.localFile;
        fileAndDirectoryManager = _fileAndDirectoryManager;
        uploadDirectory = queAction.directory;
        this.queAction = queAction;
    }

    @Override
    public void run() {
        new Notification("Uploading file", "Uploading " + input + "..", 5);
        System.out.println("Uploading " + input + " to " + uploadDirectory);
        try {
            FTP.changeDirectory(uploadDirectory);
            FTP.uploadFile(new File(input));
            fileAndDirectoryManager.unPause();
            Action reloadDirectory = new Action(ActionType.RELOADDIRECTORY);
            reloadDirectory.setDirectory(uploadDirectory);
            fileAndDirectoryManager.actionQue.actions.add(reloadDirectory);
            new Thread(fileAndDirectoryManager.actionQue).start();
        } catch (Exception e) {
            e.printStackTrace();
            new Notification("Upload failed!", input + " failed to upload, trying again in a moment..", 5);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            fileAndDirectoryManager.unPause();
            fileAndDirectoryManager.actionQue.actions.add(queAction);
            new Thread(fileAndDirectoryManager.actionQue).start();

        }
    }
}
