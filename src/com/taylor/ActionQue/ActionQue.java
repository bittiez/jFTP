package com.taylor.ActionQue;

import com.taylor.helper.FTPHandler;
import com.taylor.helper.FileDownload;
import com.taylor.helper.UploadFile;
import com.taylor.manager.FileAndDirectoryManager;

import java.util.ArrayList;

/**
 * Created by tad on 12/7/2014.
 */
public class ActionQue implements Runnable{
    private boolean running = false;
    public ArrayList<Action> actions;
    private FTPHandler FTP;
    private FileAndDirectoryManager fileAndDirectoryManager;
    public ActionQue(FTPHandler _FTP, FileAndDirectoryManager _fileAndDirectoryManager){
        actions = new ArrayList<Action>();
        FTP = _FTP;
        fileAndDirectoryManager = _fileAndDirectoryManager;
    }

    @Override
    public void run() {
        if(!running){
            running = true;
            while(!actions.isEmpty()){
                Action queItem = actions.get(0);

                if(queItem.actionType == ActionType.DELETE_FILE){
                    if(!queItem.file.isEmpty() && !queItem.directory.isEmpty()){
                        fileAndDirectoryManager.waitForCompletion();
                        try {
                            System.out.println("Deleting " +queItem.file+ " from "+queItem.directory);
                            FTP.changeDirectory(queItem.directory);
                            FTP.deleteFile(queItem.file);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else if(queItem.actionType == ActionType.RELOADDIRECTORY){
                    if(!queItem.directory.isEmpty()){
                        fileAndDirectoryManager.waitForCompletion();
                        System.out.println("Reloading "+queItem.directory);
                        fileAndDirectoryManager.reloadDirectory(queItem.directory);
                    }
                } else if(queItem.actionType == ActionType.UPLOAD) {
                    if(!queItem.directory.isEmpty() && !queItem.localFile.isEmpty()){
                        fileAndDirectoryManager.pauseManager();
                        try {
                            new UploadFile(FTP, queItem.localFile, fileAndDirectoryManager, queItem.directory).run();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else if(queItem.actionType == ActionType.DOWNLOAD){
                    if(queItem.fileObject != null && !queItem.localFile.isEmpty()){
                        try {
                            new Thread(new FileDownload(FTP, queItem.fileObject, queItem.localFile)).start();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                actions.remove(0);
            }
            running=false;
        }
    }


}
