package com.taylor.design;

import com.taylor.helper.FTPHandler;
import it.sauronsoftware.ftp4j.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by tad on 12/2/2014.
 */
public class FileBrowser {
    private JPanel mainPanel;
    private JScrollPane scrollPane;
    private JPanel contentPanel;
    public JFrame frame;
    public FTPHandler FTP;
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
        frame.setPreferredSize(new Dimension(750, 515));
        frame.setSize(frame.getPreferredSize());
        //frame.setMinimumSize(frame.getSize());

        //mainPanel
        //--scrollPane
        //   --contentPanel

        GridLayout GL = new GridLayout();
        GL.setColumns(3);
        GL.setRows(0);
        contentPanel.setLayout(GL);
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
        list = sortList(list);

        try {
            if(!FTP.getCurrentDirectory().equals(initialDirectory)){
                FileObject fileObjectDir = new FileObject(this);
                panelList.add(fileObjectDir);
                contentPanel.add((fileObjectDir));
            }
        } catch (FTPException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FTPIllegalReplyException e) {
            e.printStackTrace();
        }

        for(FTPFile file : list){
            FileObject fileObjectPanel = new FileObject(file, this);
            panelList.add(fileObjectPanel);
            contentPanel.add(fileObjectPanel);
        }

        mainPanel.updateUI();
    }

    private FTPFile[] sortList(FTPFile[] sortMe) {
        ArrayList<FTPFile> dir = new ArrayList<FTPFile>();
        ArrayList<FTPFile> file = new ArrayList<FTPFile>();
        ArrayList<FTPFile> combine = new ArrayList<FTPFile>();


        for (FTPFile filez : sortMe) {
            switch (filez.getType()) {
                case FTPFile.TYPE_FILE:
                    file.add(filez);
                    break;
                case FTPFile.TYPE_DIRECTORY:
                    dir.add(filez);
                    break;
            }
        }
        for(FTPFile filez : dir){
            combine.add(filez);
        }
        for(FTPFile filez : file){
            combine.add(filez);
        }
        FTPFile[] fa = new FTPFile[combine.size()];
        return combine.toArray(fa);

    }

    public void changeDirectoryUp(){
        try {
            FTP.changeDirectoryUp();
            listFiles();
        } catch (FTPException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FTPIllegalReplyException e) {
            e.printStackTrace();
        }
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
