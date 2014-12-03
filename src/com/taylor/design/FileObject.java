package com.taylor.design;

import com.taylor.design.WrapLayout;
import it.sauronsoftware.ftp4j.FTPFile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 * Created by tad on 12/3/2014.
 */
public class FileObject extends JPanel {
    private FTPFile file;
    private JLabel label;
    private ImageIcon icon;
    private int fileType;
    private final int iconSize = 25;
    private FileBrowser fileBrowser;

    public FileObject(FTPFile FILE, FileBrowser FILEBROWSER){
        file = FILE;
        fileBrowser = FILEBROWSER;
        fileType = file.getType();
        this.setLayout(new WrapLayout());

        switch (fileType){
            case FTPFile.TYPE_DIRECTORY:
                icon = resizeImage(new ImageIcon(getClass().getResource("/com/taylor/48px/folder.png")), iconSize);
                break;
            case FTPFile.TYPE_FILE:
                try {
                    String FileExtension = file.getName().substring(file.getName().lastIndexOf(".")+1);
                    icon = resizeImage(new ImageIcon(getClass().getResource("/com/taylor/48px/"+FileExtension+".png")), iconSize);
                } catch(Exception e) {
                    icon = resizeImage(new ImageIcon(getClass().getResource("/com/taylor/48px/_blank.png")), iconSize);
                }
                break;
            case FTPFile.TYPE_LINK:
                icon = resizeImage(new ImageIcon(getClass().getResource("/com/taylor/48px/_page.png")), iconSize);
                break;
        }
        label = new JLabel(file.getName(), icon, JLabel.LEFT);
        label.setHorizontalTextPosition(JLabel.CENTER);
        label.setVerticalTextPosition(JLabel.BOTTOM);


        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() > 1){
                    if(fileType == FTPFile.TYPE_DIRECTORY){
                        fileBrowser.changeDir(file.getName());
                    }
                }
                super.mouseClicked(e);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                label.setBorder(BorderFactory.createDashedBorder(Color.gray));
                super.mouseEntered(e);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                label.setBorder(BorderFactory.createEmptyBorder());
                super.mouseExited(e);
            }
        });


        this.add(label);

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
