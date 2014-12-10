package com.taylor.design;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FolderName extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JPanel mainPanel;
    public boolean canceled = true;
    public JTextField name;

    public FolderName() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

// call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

// call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        Dimension size = new Dimension(250, 120);
        setSize(size);
        setPreferredSize(size);
        setTitle("Folder Name");
        pack();
        setVisible(true);

    }

    private void onOK() {
// add your code here
        if(!name.getText().isEmpty())
            canceled = false;
        else
            canceled = true;
        dispose();
    }

    private void onCancel() {
        canceled = true;
        dispose();
    }
}
