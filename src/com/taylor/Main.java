package com.taylor;

import com.taylor.design.FileBrowser;
import com.taylor.helper.FTPHandler;
import it.sauronsoftware.ftp4j.FTPException;
import it.sauronsoftware.ftp4j.FTPIllegalReplyException;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        new Main();
    }



    public FTPHandler FTP;
    public Main(){
        try {
            FTP = new FTPHandler();
        } catch (FTPException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FTPIllegalReplyException e) {
            e.printStackTrace();
        }

        if(FTP == null)
            System.exit(1);
        FileBrowser GUI = new FileBrowser(FTP);


    }
}
