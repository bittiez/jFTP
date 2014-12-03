package com.taylor.design;

import com.taylor.helper.FTPHandler;
import it.sauronsoftware.ftp4j.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by tad on 12/2/2014.
 */
public class FileBrowser {
    private JPanel mainPanel;
    private JScrollPane scrollPane;
    private JPanel contentPanel;
    private JFrame frame;
    private FTPHandler FTP;
    private ArrayList<JPanel> panelList;
    private String initialDirectory;


    public FileBrowser(FTPHandler _FTP) {
        FTP = _FTP;
        try {
            initialDirectory = FTP.getCurrentDirectory();
        } catch (FTPException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FTPIllegalReplyException e) {
            e.printStackTrace();
        }
        panelList = new ArrayList<JPanel>();

        frame = new JFrame("jFTP File Browser");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(750, 500));
        frame.setSize(frame.getPreferredSize());
        frame.setMinimumSize(frame.getSize());

        //mainPanel
        //--scrollPane
        //   --contentPanel

        //contentPanel.setLayout(new WrapLayout(WrapLayout.LEFT));
        contentPanel.setLayout(new GridLayout(0, 5));
        listFiles();

        frame.pack();
        frame.setVisible(true);
    }

    public void listFiles(){
        panelList.clear();
        contentPanel.removeAll();
        FTPFile[] list = null;
        try {
            list = FTP.listFiles();
        } catch (Exception e) {
            e.printStackTrace();
        }


        for(FTPFile file : list){
            FileObject fileObjectPanel = new FileObject(file, this);
            panelList.add(fileObjectPanel);
            contentPanel.add(fileObjectPanel);
        }

        mainPanel.updateUI();
    }

    public void changeDir(String dir){
        try {
            FTP.changeDirectory(dir);
            listFiles();
        } catch (FTPException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FTPIllegalReplyException e) {
            e.printStackTrace();
        }
    }
}
