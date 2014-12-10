package com.taylor.helper;

import com.taylor.ActionQue.*;
import com.taylor.ActionQue.Action;
import com.taylor.design.FileObject;
import com.taylor.design.FolderName;
import com.taylor.design.Notification;
import com.taylor.manager.FileAndDirectoryManager;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Created by tad on 12/4/2014.
 */
public class FileRightClickMenu extends JPopupMenu{
        ArrayList<JMenuItem> Items;
        FileObject file;

        public FileRightClickMenu(final FileAndDirectoryManager fileAndDirectoryManager, final String currentDirectory){
            Items = new ArrayList<JMenuItem>();

            JMenuItem newFolder = new JMenuItem("New Folder");
            newFolder.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    FolderName folderName = new FolderName();
                    if(!folderName.canceled){
                        Action newFolder = new Action(ActionType.NEWDIRECTORY);
                        newFolder.setDirectory(currentDirectory);
                        newFolder.setNewDirectory(folderName.name.getText());
                        Action reloadDirectory = new Action(ActionType.RELOADDIRECTORY);
                        reloadDirectory.setDirectory(currentDirectory);

                        fileAndDirectoryManager.actionQue.actions.add(newFolder);
                        fileAndDirectoryManager.actionQue.actions.add(reloadDirectory);
                        new Thread(fileAndDirectoryManager.actionQue).start();
                        new Notification("New folder", "The new folder will be added soon!", 5);
                    }
                }
            });
            Items.add(newFolder);
            for(JMenuItem item : Items)
                add(item);
        }

        public FileRightClickMenu(FileObject FILE) {
            file = FILE;
            Items = new ArrayList<JMenuItem>();

            JMenuItem delete = new JMenuItem("Delete");
            delete.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    try {
                        file.deleteFile();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            });

            Items.add(delete);
            //Items.add(new JMenuItem("Rename"));
            //Items.add(new JMenuItem("Move"));
            for(JMenuItem item : Items)
                add(item);
        }
}
