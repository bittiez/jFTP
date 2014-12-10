package com.taylor.ActionQue;

import com.taylor.design.FileObject;

/**
 * Created by tad on 12/7/2014.
 */
public class Action {
    public ActionType actionType;
    public String directory;
    public String file;
    public String localFile;
    public FileObject fileObject;
    public String newDirectory;

    public Action(ActionType _actionType) {
        actionType = _actionType;
    }

    public void setLocalFile(String localFile) {
        this.localFile = localFile;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public void setNewDirectory(String newDirectory) {
        this.newDirectory = newDirectory;
    }

    public void setFileObject(FileObject fileObject) {
        this.fileObject = fileObject;
    }
}
