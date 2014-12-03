package com.taylor;

import it.sauronsoftware.ftp4j.FTPFile;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by tad on 12/3/2014.
 */
public class fileObject extends JPanel {
    private FTPFile file;
    private JLabel Label;
    private ImageIcon Icon;
    private int FileType;
    private final int IconSize = 25;
    private final Dimension panelSize = new Dimension(150, 40);

    public fileObject(FTPFile FILE){
        file = FILE;
        FileType = file.getType();
        this.setSize(panelSize);
        this.setPreferredSize(panelSize);
        this.setMinimumSize(panelSize);
        this.setLayout(new WrapLayout());

        switch (FileType){
            case FTPFile.TYPE_DIRECTORY:
                Icon = resizeImage(new ImageIcon(getClass().getResource("/com/taylor/48px/folder.png")),IconSize);
                break;
            case FTPFile.TYPE_FILE:
                try {
                    String FileExtension = file.getName().substring(file.getName().lastIndexOf(".")+1);
                    Icon = resizeImage(new ImageIcon(getClass().getResource("/com/taylor/48px/"+FileExtension+".png")),IconSize);
                } catch(Exception e) {
                    Icon = resizeImage(new ImageIcon(getClass().getResource("/com/taylor/48px/_blank.png")),IconSize);
                }
                break;
            case FTPFile.TYPE_LINK:
                Icon = resizeImage(new ImageIcon(getClass().getResource("/com/taylor/48px/_page.png")),IconSize);
                break;
        }
        Label = new JLabel(file.getName(), Icon, JLabel.LEFT);
        this.add(Label);

    }

    private ImageIcon resizeImage(ImageIcon II, int Size){
        Image img = II.getImage();
        BufferedImage resizedImage = new BufferedImage(Size, Size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImage.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(img, 0, 0, Size, Size, null);
        g2.dispose();
        return new ImageIcon(resizedImage);
    }
}
