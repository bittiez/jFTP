package com.taylor.helper;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by tad on 12/4/2014.
 */
public class FileRightClickMenu extends JPopupMenu{
        ArrayList<JMenuItem> Items;

        public FileRightClickMenu() {
            Items = new ArrayList<JMenuItem>();
            Items.add(new JMenuItem("Delete"));
            Items.add(new JMenuItem("Rename"));
            Items.add(new JMenuItem("Move"));
            for(JMenuItem item : Items)
                add(item);
        }
}
