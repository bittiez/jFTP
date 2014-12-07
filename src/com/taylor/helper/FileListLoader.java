package com.taylor.helper;

import com.taylor.design.FileBrowser;
import com.taylor.design.FileObject;
import com.taylor.manager.FileAndDirectoryManager;
import it.sauronsoftware.ftp4j.FTPException;
import it.sauronsoftware.ftp4j.FTPFile;
import it.sauronsoftware.ftp4j.FTPIllegalReplyException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by tad on 12/4/2014.
 */
public class FileListLoader implements Runnable {
    private FTPHandler FTP;
    private FileBrowser fileBrowser;
    private FileAndDirectoryManager fileAndDirectoryManager;
    private String currentDirectory;
    public FileListLoader(FTPHandler _FTP, FileAndDirectoryManager fileAndDirMan, String directory){
        FTP = _FTP;
        fileBrowser = FTP.GUI;
        fileAndDirectoryManager = fileAndDirMan;
        currentDirectory = directory;
    }

    @Override
    public void run() {
        FTPFile[] list = null;
        try {
            list = fileAndDirectoryManager.GetFiles(currentDirectory);
        } catch (Exception e) {
            e.printStackTrace();
        }
        list = sortList(list);
        fileBrowser.contentPanel.removeAll();
            if(!currentDirectory.equals(fileBrowser.initialDirectory)){

                FileObject fileObjectDir = new FileObject(fileBrowser, currentDirectory);
                fileBrowser.panelList.add(fileObjectDir);
                fileBrowser.contentPanel.add((fileObjectDir));
            }


        for(FTPFile file : list){
            FileObject fileObjectPanel = new FileObject(file, fileBrowser, currentDirectory.toString());
            fileBrowser.panelList.add(fileObjectPanel);
            fileBrowser.contentPanel.add(fileObjectPanel);
        }

        fileBrowser.mainPanel.updateUI();
    }

    private FTPFile[] sortList(FTPFile[] sortMe) {
        ArrayList<FTPFile> dir = new ArrayList<FTPFile>();
        ArrayList<FTPFile> file = new ArrayList<FTPFile>();
        ArrayList<FTPFile> combine = new ArrayList<FTPFile>();


        for (FTPFile filez : sortMe) {
            switch (filez.getType()) {
                case FTPFile.TYPE_FILE:
                    file.add(filez);
                    break;
                case FTPFile.TYPE_DIRECTORY:
                    dir.add(filez);
                    break;
            }
        }
        for(FTPFile filez : dir){
            combine.add(filez);
        }
        for(FTPFile filez : file){
            combine.add(filez);
        }
        FTPFile[] fa = new FTPFile[combine.size()];
        return combine.toArray(fa);

    }
}
