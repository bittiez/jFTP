package com.taylor.ActionQue;

import com.taylor.design.Notification;
import com.taylor.fileTransfer.FileDownload;
import com.taylor.fileTransfer.UploadFile;
import com.taylor.helper.FTPHandler;
import com.taylor.manager.FileAndDirectoryManager;

import java.util.ArrayList;

/**
 * Created by tad on 12/7/2014.
 */
public class ActionQue implements Runnable {
    public ArrayList<Action> actions;
    private boolean running = false;
    private FTPHandler FTP;
    private FileAndDirectoryManager fileAndDirectoryManager;

    public ActionQue(FTPHandler _FTP, FileAndDirectoryManager _fileAndDirectoryManager) {
        actions = new ArrayList<Action>();
        FTP = _FTP;
        fileAndDirectoryManager = _fileAndDirectoryManager;
    }

    @Override
    public void run() {
        if (!running) {
            running = true;
            while (!actions.isEmpty()) {
                Action queItem = actions.get(0);

                if (queItem.actionType == ActionType.DELETE_FILE) {
                    if (!queItem.file.isEmpty() && !queItem.directory.isEmpty()) {
                        fileAndDirectoryManager.pauseManager();
                        try {
                            System.out.println("Deleting " + queItem.file + " from " + queItem.directory);
                            FTP.changeDirectory(queItem.directory);
                            FTP.deleteFile(queItem.file);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        fileAndDirectoryManager.unPause();
                    }
                } else if (queItem.actionType == ActionType.DELETE_DIRECTORY) {
                    if (!queItem.file.isEmpty() && !queItem.directory.isEmpty()) {
                        fileAndDirectoryManager.pauseManager();
                        try {
                            FTP.changeDirectory(queItem.directory);
                            FTP.deleteDirectory(queItem.file);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        System.out.println("Deleting directory: " + queItem.file);
                        fileAndDirectoryManager.unPause();
                    }
                } else if (queItem.actionType == ActionType.RELOADDIRECTORY) {
                    if (!queItem.directory.isEmpty()) {
                        fileAndDirectoryManager.pauseManager();
                        System.out.println("Reloading " + queItem.directory);
                        fileAndDirectoryManager.reloadDirectory(queItem.directory);
                        fileAndDirectoryManager.unPause();
                    }
                } else if (queItem.actionType == ActionType.UPLOAD) {
                    if (!queItem.directory.isEmpty() && !queItem.localFile.isEmpty()) {
                        fileAndDirectoryManager.pauseManager();
                        try {
                            new UploadFile(FTP, fileAndDirectoryManager, queItem).run();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else if (queItem.actionType == ActionType.DOWNLOAD) {
                    if (queItem.fileObject != null && !queItem.localFile.isEmpty()) {
                        try {
                            new Thread(new FileDownload(FTP, queItem.fileObject, queItem.localFile)).start();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else if (queItem.actionType == ActionType.NEWDIRECTORY) {
                    if (!queItem.directory.isEmpty() && !queItem.newDirectory.isEmpty()) {
                        fileAndDirectoryManager.pauseManager();
                        try {
                            FTP.changeDirectory(queItem.directory);
                            FTP.createDirectory(queItem.newDirectory);
                            System.out.println("Created new directory");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        fileAndDirectoryManager.unPause();
                        new Notification("Folder created", queItem.newDirectory + " has been created!", 5);
                    }
                }

                actions.remove(0);
            }
            running = false;
        }
    }


}
