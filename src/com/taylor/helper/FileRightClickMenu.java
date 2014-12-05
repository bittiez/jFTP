package com.taylor.helper;

import com.taylor.design.FileObject;
import it.sauronsoftware.ftp4j.FTPException;
import it.sauronsoftware.ftp4j.FTPIllegalReplyException;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by tad on 12/4/2014.
 */
public class FileRightClickMenu extends JPopupMenu{
        ArrayList<JMenuItem> Items;
        FileObject file;

        public FileRightClickMenu(FileObject FILE) {
            file = FILE;
            Items = new ArrayList<JMenuItem>();

            JMenuItem delete = new JMenuItem("Delete");
            delete.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
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
