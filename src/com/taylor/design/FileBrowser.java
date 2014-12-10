package com.taylor.design;

import com.taylor.helper.FTPHandler;
import com.taylor.helper.FileListLoader;
import com.taylor.helper.FileRightClickMenu;
import com.taylor.helper.ToTransferHandler;
import com.taylor.manager.FileAndDirectoryManager;

import javax.swing.*;
import java.awt.*;
import java.awt.dnd.DropTarget;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Created by tad on 12/2/2014.
 */
public class FileBrowser {
    public JPanel mainPanel;
    public JPanel contentPanel;
    public JFrame frame;
    public FTPHandler FTP;
    public ArrayList<JPanel> panelList;
    public String initialDirectory;
    public FileAndDirectoryManager fileAndDirectoryManager;
    public String currentDirectory;
    private JScrollPane scrollPane;
    private String baseTitle = "FTPHub";
    private JLabel loadingLabel = new JLabel(new ImageIcon(getClass().getResource("/com/taylor/48px/ajax_loader_orange_64.gif")));

    public FileBrowser(FTPHandler FTP) {
        this.FTP = FTP;
        if (FTP == null)
            System.exit(1);

        setLayout();
    }


    public FileBrowser() {
        try {
            FTP = new FTPHandler();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (FTP == null)
            System.exit(1);

        setLayout();
    }

    public void setLayout() {
        frame = new JFrame(baseTitle);
        try {
            initialDirectory = FTP.getCurrentDirectory();
            frame.setTitle(baseTitle + " " + initialDirectory);
        } catch (Exception e) {
            e.printStackTrace();
        }
        currentDirectory = initialDirectory;
        fileAndDirectoryManager = new FileAndDirectoryManager(FTP);
        Thread t = new Thread(fileAndDirectoryManager);
        t.start();

        panelList = new ArrayList<JPanel>();


        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(750, 515));
        frame.setSize(frame.getPreferredSize());

        GridLayout GL = new GridLayout();
        GL.setColumns(3);
        GL.setRows(0);
        contentPanel.setLayout(GL);

        // Create the drag and drop listener
        ToTransferHandler myDragDropListener = new ToTransferHandler(FTP, this, fileAndDirectoryManager);

        // Connect the label with a drag and drop listener
        new DropTarget(contentPanel, myDragDropListener);

        listFiles();

        contentPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                maybeRightMenu(e);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                maybeRightMenu(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                maybeRightMenu(e);
            }
        });
        frame.pack();
        frame.setVisible(true);
    }

    public void maybeRightMenu(MouseEvent e) {
        if (e.isPopupTrigger()) {
            FileRightClickMenu menu = new FileRightClickMenu(fileAndDirectoryManager, currentDirectory);
            menu.show(e.getComponent(), e.getX(), e.getY());
        }
    }

    public void listFiles() {
        panelList.clear();
        contentPanel.removeAll();

        contentPanel.add(loadingLabel);
        contentPanel.updateUI();

        FileListLoader FLL = new FileListLoader(FTP, fileAndDirectoryManager, currentDirectory.toString(), this);

        Thread t = new Thread(FLL);
        t.start();
    }

    public void changeDir(String dir) {
        //FTP.changeDirectory(dir);
        frame.setTitle(baseTitle + " " + dir);
        currentDirectory = dir;
        listFiles();
    }
}
