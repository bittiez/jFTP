package com.taylor.design;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by tad on 12/9/2014.
 */
public class Notification extends JFrame implements Runnable {
    private String title, description;
    private int timeout;
    private ArrayList<Object> objects;

    public Notification(String title, String description, int timeout) {
        this.title = title;
        this.description = description;
        this.timeout = timeout;
        objects = new ArrayList<Object>();
        setLayout();

        GraphicsEnvironment ge =
                GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        boolean isUniformTranslucencySupported =
                gd.isWindowTranslucencySupported(GraphicsDevice.WindowTranslucency.TRANSLUCENT);
        if (isUniformTranslucencySupported)
            this.setOpacity(0.75f);

    }

    private void setLayout() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createDashedBorder(Color.gray));
        mainPanel.setBackground(Color.darkGray);
        mainPanel.setForeground(Color.orange);


        JLabel titleLabel = new JLabel(title);
        titleLabel.setForeground(Color.orange);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        JLabel descriptionLabel = new JLabel(description);
        descriptionLabel.setForeground(Color.orange);
        descriptionLabel.setHorizontalAlignment(JLabel.CENTER);

        mainPanel.add(titleLabel, BorderLayout.PAGE_START);
        mainPanel.add(descriptionLabel, BorderLayout.CENTER);

        this.setTitle(title);
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        Dimension d = new Dimension(400, 100);
        setSize(d);
        setPreferredSize(d);
        setContentPane(mainPanel);

        objects.add(mainPanel);
        objects.add(titleLabel);
        objects.add(descriptionLabel);
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

        setLocation(
                (int) (gd.getDisplayMode().getWidth() - d.getWidth()) - 50,
                (int) (gd.getDisplayMode().getHeight() - d.getHeight()) - 50);

        setType(Type.UTILITY);
        pack();

        setVisible(true);
        new Thread(this).start();
    }

    @Override
    public void run() {
        try {
            Thread.sleep(timeout * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        this.dispose();
    }
}
