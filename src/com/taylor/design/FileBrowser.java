package com.taylor.design;

import com.taylor.helper.FTPHandler;
import it.sauronsoftware.ftp4j.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by tad on 12/2/2014.
 */
public class FileBrowser {
    private JPanel mainPanel;
    private JScrollPane scrollPane;
    private JFrame frame;
    private FTPHandler FTP;
    private ArrayList<JPanel> panelList;

    public FileBrowser(FTPHandler _FTP) {
        FTP = _FTP;
        panelList = new ArrayList<JPanel>();

        frame = new JFrame("jFTP File Browser");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(750, 500));
        frame.setSize(frame.getPreferredSize());
        frame.setMinimumSize(frame.getSize());

        mainPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        listFiles();

        frame.pack();
        frame.setVisible(true);
    }

    public void listFiles(){
        panelList.clear();
        FTPFile[] list = null;
        try {
            list = FTP.listFiles();
        } catch (Exception e) {
            e.printStackTrace();
        }

        for(FTPFile file : list){
            FileObject fileObjectPanel = new FileObject(file);
            panelList.add(fileObjectPanel);
            scrollPane.add(fileObjectPanel);
        }

        scrollPane.updateUI();
    }

    public void changeDir(){

    }
}
