package com.taylor.design;

import com.taylor.ActionQue.*;
import com.taylor.ActionQue.Action;
import com.taylor.helper.FileRightClickMenu;
import it.sauronsoftware.ftp4j.FTPFile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class FileObject extends JPanel {
    public FTPFile file;
    private JLabel label;
    private ImageIcon icon;
    private int fileType;
    private final int iconSize = 25;
    public FileBrowser fileBrowser;
    private Color backgroundColor;
    private String Directory;


    public FileObject(FileBrowser FILEBROWSER, String DIRECTORY) {
        Directory = DIRECTORY;
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
                    String upDir = getFullPath().substring(0, getFullPath().lastIndexOf("/") + 1);
                    if(upDir.isEmpty())
                        upDir = fileBrowser.initialDirectory;
                    if(upDir.length() > 1 && upDir.charAt(upDir.length()-1) == "/".toCharArray()[0])
                        upDir = upDir.substring(0, upDir.length() - 1);
                    fileBrowser.changeDir(upDir);
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
            public void mouseExited(MouseEvent e) { label.setBorder(BorderFactory.createDashedBorder(backgroundColor)); }

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

    public FileObject(FTPFile FILE, FileBrowser FILEBROWSER, final String DIRECTORY) {
        Directory = DIRECTORY;
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
                        fileBrowser.changeDir(getFullPath());
                    } else if (fileType == FTPFile.TYPE_FILE) {
                        JFileChooser saveFile = new JFileChooser();
                        saveFile.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                        saveFile.setDialogType(JFileChooser.DIRECTORIES_ONLY);
                        saveFile.setName("Where would you like to save this file?");
                        int sf = saveFile.showSaveDialog(null);
                        if (sf == JFileChooser.APPROVE_OPTION) {
                            com.taylor.ActionQue.Action action = new Action(ActionType.DOWNLOAD);
                            action.setFileObject(getThis());
                            action.setLocalFile(saveFile.getSelectedFile().getAbsolutePath());
                            fileBrowser.fileAndDirectoryManager.actionQue.actions.add(action);
                            new Thread(fileBrowser.fileAndDirectoryManager.actionQue).start();

                            Action action2 = new Action(ActionType.RELOADDIRECTORY);
                            action2.directory = Directory;
                            fileBrowser.fileAndDirectoryManager.actionQue.actions.add(action2);
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

    public FileObject getThis(){ return this; }

    public String getFullPath(){
        String ret;
        if(file != null)
            ret = Directory + "/" + file.getName();
        else
            ret = Directory;
        while(ret.contains("\\"))
            ret = ret.replace("\\", "/");
        ret = ret.replaceAll("//", "/");
        return ret;
    }

    public void deleteFile(){
        if(fileType == FTPFile.TYPE_FILE) {
            Action action = new Action(ActionType.DELETE_FILE);
            action.setDirectory(Directory);
            action.setFile(file.getName());
            Action action2 = new Action(ActionType.RELOADDIRECTORY);
            action2.setDirectory(Directory);
            fileBrowser.fileAndDirectoryManager.actionQue.actions.add(action);
            fileBrowser.fileAndDirectoryManager.actionQue.actions.add(action2);
        } else if(fileType == FTPFile.TYPE_DIRECTORY){

        }
        new Thread(fileBrowser.fileAndDirectoryManager.actionQue).start();
        fileBrowser.contentPanel.remove(this);
        fileBrowser.contentPanel.updateUI();
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
