package com.taylor.manager;

import com.taylor.helper.FTPDirectory;
import com.taylor.helper.FTPHandler;
import it.sauronsoftware.ftp4j.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tad on 12/6/2014.
 */
public class FileAndDirectoryManager implements Runnable{
    private FTPHandler FTP;
    private Map<String, FTPDirectory> directories;
    private ArrayList<String> removeSubDirectories;
    public boolean complete;

    public FileAndDirectoryManager(FTPHandler _FTP){
        FTP = _FTP;
        directories = new HashMap<String, FTPDirectory>();
        removeSubDirectories = new ArrayList<String>();
        complete = false;
    }

    public FTPFile[] GetFiles(String Directory){
        if(directories.containsKey(Directory))
            return directories.get(Directory).files;
        else
        {
            while(true) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(directories.containsKey(Directory))
                    return directories.get(Directory).files;
            }

        }
    }

    @Override
    public void run() {
        ArrayList<String> subDirectories = new ArrayList<String>();
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
        //if(directories.containsKey(dir))
        //   directories.remove(dir);
        //System.out.println(dir);

        FTPDirectory tempDir = new FTPDirectory(files, dir);
        directories.put(dir, tempDir);

        for(FTPFile file : files){
            if(file.getType() == FTPFile.TYPE_DIRECTORY){
                subDirectories.add(dir + "/" +  file.getName());
            }
        }
        forEachSubDirectory(subDirectories.toArray());
    }

    public void forEachSubDirectory(Object[] _subDirectories){
        for(Object subDir : _subDirectories) {
            boolean success = false;
            try {
                FTP.changeDirectory((String)subDir);
                success = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(success){
                runSubDirectory();
            }
        }
    }

    public void runSubDirectory(){
        ArrayList<String> subDirectories = new ArrayList<String>();
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
        //if(directories.containsKey(dir))
        //    directories.remove(dir);
        //System.out.println(dir);

        FTPDirectory tempDir = new FTPDirectory(files, dir);
        directories.put(dir, tempDir);

        for(FTPFile file : files){
            if(file.getType() == FTPFile.TYPE_DIRECTORY){
                subDirectories.add(dir + "/" +  file.getName());
            }
        }
        if(subDirectories.size() > 0)
            forEachSubDirectory(subDirectories.toArray());
    }
}
