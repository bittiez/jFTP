package com.taylor.design;

import com.taylor.helper.FileRightClickMenu;
import com.taylor.helper.fileDownload;
import it.sauronsoftware.ftp4j.FTPException;
import it.sauronsoftware.ftp4j.FTPFile;
import it.sauronsoftware.ftp4j.FTPIllegalReplyException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class FileObject extends JPanel {
    private FTPFile file;
    private JLabel label;
    private ImageIcon icon;
    private int fileType;
    private final int iconSize = 25;
    public FileBrowser fileBrowser;
    private Color backgroundColor;


    public FileObject(FileBrowser FILEBROWSER) {
        fileBrowser = FILEBROWSER;
        fileType = FTPFile.TYPE_DIRECTORY;
        this.setLayout(new WrapLayout());


        icon = resizeImage(new ImageIcon(getClass().getResource("/com/taylor/48px/folder.png")), iconSize);
        label = new JLabel("<-- Back", icon, JLabel.LEFT);
        label.setHorizontalTextPosition(JLabel.CENTER);
        label.setVerticalTextPosition(JLabel.BOTTOM);

        label.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() > 1 && SwingUtilities.isLeftMouseButton(e)) {
                    if (fileType == FTPFile.TYPE_DIRECTORY) {
                        fileBrowser.changeDirectoryUp();
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                maybeShowPopup(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                maybeShowPopup(e);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                label.setBorder(BorderFactory.createDashedBorder(Color.gray));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                label.setBorder(BorderFactory.createDashedBorder(backgroundColor));
            }

            private void maybeShowPopup(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    FileRightClickMenu menu = new FileRightClickMenu(FileObject.this);
                    menu.show(e.getComponent(),
                            e.getX(), e.getY());
                }
            }
        });


        this.add(label);

    }

    public FileObject(FTPFile FILE, FileBrowser FILEBROWSER) {

        file = FILE;
        fileBrowser = FILEBROWSER;
        fileType = file.getType();
        backgroundColor = FILEBROWSER.frame.getBackground();

        this.setLayout(new WrapLayout());

        switch (fileType) {
            case FTPFile.TYPE_DIRECTORY:
                icon = resizeImage(new ImageIcon(getClass().getResource("/com/taylor/48px/folder.png")), iconSize);
                break;
            case FTPFile.TYPE_FILE:
                try {
                    String FileExtension = file.getName().substring(file.getName().lastIndexOf(".") + 1);
                    icon = resizeImage(new ImageIcon(getClass().getResource("/com/taylor/48px/" + FileExtension + ".png")), iconSize);
                } catch (Exception e) {
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
        label.setBorder(BorderFactory.createDashedBorder(backgroundColor));


        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() > 1 && SwingUtilities.isLeftMouseButton(e)) {
                    if (fileType == FTPFile.TYPE_DIRECTORY) {
                        fileBrowser.changeDir(file.getName());
                    } else if (fileType == FTPFile.TYPE_FILE) {
                        JFileChooser saveFile = new JFileChooser();
                        saveFile.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                        saveFile.setDialogType(JFileChooser.DIRECTORIES_ONLY);
                        saveFile.setName("Where would you like to save this file?");
                        int sf = saveFile.showSaveDialog(null);
                        if (sf == JFileChooser.APPROVE_OPTION) {
                            Thread t = new Thread(new fileDownload(fileBrowser.FTP, file, saveFile.getSelectedFile().getAbsolutePath()));
                            t.start();
                        }
                    }
                }
            }


            @Override
            public void mousePressed(MouseEvent e) {
                maybeShowPopup(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                maybeShowPopup(e);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                label.setBorder(BorderFactory.createDashedBorder(Color.gray));
                super.mouseEntered(e);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                label.setBorder(BorderFactory.createDashedBorder(backgroundColor));
                super.mouseExited(e);
            }

            private void maybeShowPopup(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    FileRightClickMenu menu = new FileRightClickMenu(FileObject.this);
                    menu.show(e.getComponent(),
                            e.getX(), e.getY());
                }
            }
        });


        this.add(label);

    }

    public void deleteFile(){
        try {
            //String path = Paths.get(fileBrowser.FTP.getCurrentDirectory(), file.getName()).toString().replace("\\", "/");
            String path = file.getName();
            //System.out.println(path);
            if(fileType == FTPFile.TYPE_FILE)
                fileBrowser.FTP.deleteFile(path);
            else if(fileType == FTPFile.TYPE_DIRECTORY)
                fileBrowser.FTP.deleteDirectory(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        fileBrowser.listFiles();
    }


    private ImageIcon resizeImage(ImageIcon II, int Size) {
        Image img = II.getImage();
        BufferedImage resizedImage = new BufferedImage(Size, Size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImage.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(img, 0, 0, Size, Size, null);
        g2.dispose();
        return new ImageIcon(resizedImage);
    }
}
