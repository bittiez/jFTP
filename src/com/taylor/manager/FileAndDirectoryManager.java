package com.taylor.manager;

import com.taylor.helper.FTPDirectory;
import com.taylor.helper.FTPHandler;
import it.sauronsoftware.ftp4j.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tad on 12/6/2014.
 */
public class FileAndDirectoryManager implements Runnable{
    private FTPHandler FTP;
    private Map<String, FTPDirectory> directories;
    private ArrayList<String> subDirectories;

    public FileAndDirectoryManager(FTPHandler _FTP){
        FTP = _FTP;
        directories = new HashMap<String, FTPDirectory>();
        subDirectories = new ArrayList<String>();
    }


    @Override
    public void run() {
        FTPFile[] files = null;
        String dir = "";
        try {
            dir = FTP.getCurrentDirectory();
            files = FTP.listFiles();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(dir.isEmpty() || files == null)
            return;
        if(directories.containsKey(dir))
            directories.remove(dir);

        FTPDirectory tempDir = new FTPDirectory(files, dir);
        directories.put(dir, tempDir);

        for(FTPFile file : files){
            if(file.getType() == FTPFile.TYPE_DIRECTORY){
                subDirectories.add(file.getName());
            }
        }

        for(String tempDirectory : subDirectories) {
            boolean success = false;
            try {
                FTP.changeDirectory(tempDirectory);
                success = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(success){
                FTPFile[] files1 = null;
                String dir1 = "";
                try {
                    dir1 = FTP.getCurrentDirectory();
                    files1 = FTP.listFiles();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if(dir1.isEmpty() || files1 == null)
                    return;
                if(directories.containsKey(dir1))
                    directories.remove(dir1);

                tempDir = new FTPDirectory(files1, dir1);
                directories.put(dir1, tempDir);
            }
        }
    }
}
