package com.taylor.helper;

import com.taylor.design.FileBrowser;
import it.sauronsoftware.ftp4j.*;

import java.io.File;
import java.io.IOException;

public class FTPHandler {
    public FTPClient client;
    public FileBrowser GUI;

    public FTPHandler(FileBrowser _GUI) throws FTPException, IOException, FTPIllegalReplyException {
        client = new FTPClient();
        client.connect("jshare.ddns.net");
        client.login("Tad", "ubt7smh8");
        GUI = _GUI;
    }

    public void changeDirectoryUp() throws FTPException, IOException, FTPIllegalReplyException {
        client.changeDirectoryUp();
    }

    public void uploadFile(File LOCALFILE) throws FTPException, IOException, FTPDataTransferException, FTPIllegalReplyException, FTPAbortedException {
        client.upload(LOCALFILE);
    }

    public void uploadFile(File LOCALFILE, FTPDataTransferListener TRANSFERLISTENER) throws FTPException, IOException, FTPDataTransferException, FTPIllegalReplyException, FTPAbortedException {
        client.upload(LOCALFILE, TRANSFERLISTENER);
    }

    public void downloadFile(String FILE, File LOCALFILE) throws FTPException, IOException, FTPDataTransferException, FTPIllegalReplyException, FTPAbortedException {
        client.download(FILE, LOCALFILE);
    }

    public void downloadFile(String FILE, File LOCALFILE, FTPDataTransferListener TRANSFERLISTENER) throws FTPException, IOException, FTPDataTransferException, FTPIllegalReplyException, FTPAbortedException {
        client.download(FILE, LOCALFILE, TRANSFERLISTENER);
    }


    public FTPFile[] listFiles() throws FTPException, IOException, FTPDataTransferException, FTPListParseException, FTPIllegalReplyException, FTPAbortedException {
        return client.list();
    }

    public void deleteDirectory(String DIRECTORY) throws FTPException, IOException, FTPIllegalReplyException {
        client.deleteDirectory(DIRECTORY);
    }

    public void createDirectory(String DIRECTORY) throws FTPException, IOException, FTPIllegalReplyException {
        client.createDirectory(DIRECTORY);
    }

    public void deleteFile(String FILE) throws FTPException, IOException, FTPIllegalReplyException {
        client.deleteFile(FILE);
    }

    public void moveFile(String FILE, String NEWDIRECTORY) throws FTPException, IOException, FTPIllegalReplyException {
        client.rename(FILE, NEWDIRECTORY + File.separator + FILE);
    }

    public void renameFile(String OLDNAME, String NEWNAME) throws FTPException, IOException, FTPIllegalReplyException {
        client.rename(OLDNAME, NEWNAME);
    }

    public void changeDirectory(String DIR) throws FTPException, IOException, FTPIllegalReplyException {
        client.changeDirectory(DIR);
    }

    public String getCurrentDirectory() throws FTPException, IOException, FTPIllegalReplyException {
        return client.currentDirectory();
    }

    public void disconnect() throws FTPException, IOException, FTPIllegalReplyException {
        client.disconnect(true);
    }
}
