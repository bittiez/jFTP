package com.taylor.manager;

import com.taylor.ActionQue.ActionQue;
import com.taylor.helper.FTPDirectory;
import com.taylor.helper.FTPHandler;
import it.sauronsoftware.ftp4j.FTPFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tad on 12/6/2014.
 */
public class FileAndDirectoryManager implements Runnable{
    private FTPHandler FTP;
    private Map<String, FTPDirectory> directories;
    public ActionQue actionQue;
    private boolean paused = false;
    public boolean pause = false;

    public boolean complete;

    public FileAndDirectoryManager(FTPHandler _FTP){
        FTP = _FTP;
        directories = new HashMap<String, FTPDirectory>();
        complete = false;
        actionQue = new ActionQue(FTP, this);
    }

    public void pauseManager(){
        pause = true;
        System.out.println("Paused");
        while(!paused) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public void unPause(){
        pause = false;
        System.out.println("UnPaused");
        while(paused){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
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

    public void waitForCompletion(){
        while(complete == false)
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    }

    public void reloadDirectory(String dir){
        FTPFile[] files = null;
        try {
            FTP.changeDirectory(dir);
            files = FTP.listFiles();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(dir.isEmpty() || files == null)
            return;

        FTPDirectory tempDir = new FTPDirectory(files, dir);
        directories.put(dir, tempDir);
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

        FTPDirectory tempDir = new FTPDirectory(files, dir);
        directories.put(dir, tempDir);

        for(FTPFile file : files){
            if(file.getType() == FTPFile.TYPE_DIRECTORY){
                subDirectories.add(dir + "/" +  file.getName());
            }
        }
        forEachSubDirectory(subDirectories.toArray(), true);
    }

    private void pause(){
        if(pause)
        {
            paused = true;
            while(pause) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            paused = false;
        }
    }

    public void forEachSubDirectory(Object[] _subDirectories, boolean initialDirectory){
        for(Object subDir : _subDirectories) {
            pause();
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
        if(initialDirectory)
            complete = true;
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
            forEachSubDirectory(subDirectories.toArray(), false);
    }
}
