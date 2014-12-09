package com.taylor.helper;

import com.taylor.ActionQue.Action;
import com.taylor.ActionQue.ActionType;
import com.taylor.design.FileBrowser;
import com.taylor.manager.FileAndDirectoryManager;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.*;
import java.io.File;
import java.util.List;

/**
 * Created by tad on 12/4/2014.
 */
public class ToTransferHandler  implements DropTargetListener {
    private FTPHandler FTP;
    private FileBrowser fileBrowser;
    private FileAndDirectoryManager fileAndDirectoryManager;
    public ToTransferHandler(FTPHandler _FTP, FileBrowser _fileBrowser, FileAndDirectoryManager _fileAndDirectoryManager){
        fileBrowser = _fileBrowser;
        FTP = _FTP;
        fileAndDirectoryManager = _fileAndDirectoryManager;
    }

    @Override
    public void drop(DropTargetDropEvent event) {

        // Accept copy drops
        event.acceptDrop(DnDConstants.ACTION_COPY);

        // Get the transfer which can provide the dropped item data
        Transferable transferable = event.getTransferable();

        // Get the data formats of the dropped item
        DataFlavor[] flavors = transferable.getTransferDataFlavors();

        // Loop through the flavors
        for (DataFlavor flavor : flavors) {

            try {

                // If the drop items are files
                if (flavor.isFlavorJavaFileListType()) {

                    // Get all of the dropped files
                    List files = (List) transferable.getTransferData(flavor);

                    // Loop them through
                    for (Object filez : files) {
                        File file = (File)filez;
                        Action uploadFile = new Action(ActionType.UPLOAD);
                        uploadFile.setDirectory(fileBrowser.currentDirectory);
                        uploadFile.setLocalFile(file.getPath());
                        fileAndDirectoryManager.actionQue.actions.add(uploadFile);
                        new Thread(fileAndDirectoryManager.actionQue).start();
                    }

                }

            } catch (Exception e) {

                // Print out the error stack
                e.printStackTrace();

            }
        }

        // Inform that the drop is complete
        event.dropComplete(true);

    }

    @Override
    public void dragEnter(DropTargetDragEvent event) {
    }

    @Override
    public void dragExit(DropTargetEvent event) {
    }

    @Override
    public void dragOver(DropTargetDragEvent event) {
    }

    @Override
    public void dropActionChanged(DropTargetDragEvent event) {
    }

}
