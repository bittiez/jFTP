package com.taylor;

import com.taylor.gui.browser;
import it.sauronsoftware.ftp4j.FTPException;
import it.sauronsoftware.ftp4j.FTPIllegalReplyException;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        new Main();
    }



    public ftpHandler FTP;
    public Main(){
        try {
            FTP = new ftpHandler();
        } catch (FTPException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FTPIllegalReplyException e) {
            e.printStackTrace();
        }

        if(FTP == null)
            System.exit(1);
        browser GUI = new browser(FTP);


    }
}
