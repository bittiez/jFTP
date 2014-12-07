package com.taylor.helper;

import it.sauronsoftware.ftp4j.FTPFile;

/**
 * Created by tad on 12/6/2014.
 */
public class FTPDirectory {
    public FTPFile[] files;
    public String directory;
    public FTPDirectory(FTPFile[] _file, String _directory){
        files = _file;
        directory = _directory;
    }
}
