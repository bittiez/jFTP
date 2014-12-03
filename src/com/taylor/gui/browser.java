package com.taylor.gui;

import com.taylor.WrapLayout;
import com.taylor.fileObject;
import com.taylor.ftpHandler;
import it.sauronsoftware.ftp4j.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by tad on 12/2/2014.
 */
public class browser {
    private JPanel mainPanel;
    private JScrollPane scrollPane;
    private JFrame frame;
    private ftpHandler FTP;
    private ArrayList<JPanel> Panels;



    public browser(ftpHandler _FTP) {

        FTP = _FTP;
        Panels = new ArrayList<JPanel>();

        frame = new JFrame("File Browser");
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
        Panels.clear();
        FTPFile[] list = null;
        try {
            list = FTP.listFiles();
        } catch (Exception e) {
            e.printStackTrace();
        }

        for(FTPFile file : list){
            fileObject FO = new fileObject(file);
            Panels.add(FO);
            scrollPane.add(FO);
        }

        scrollPane.updateUI();
    }

    public void changeDir(){

    }

    public ImageIcon resizeImage(ImageIcon II, int Size){
        Image img = II.getImage();
        BufferedImage resizedImg = new BufferedImage(Size, Size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(img, 0, 0, Size, Size, null);
        g2.dispose();
        return new ImageIcon(resizedImg);

    }
}
