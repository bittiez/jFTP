package com.taylor.design;

import com.taylor.helper.FTPHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by tad on 12/9/2014.
 */
public class ConnectionUI {
    private JPanel panel1;
    private JPanel connectionInfo;
    private JTextField address;
    private JTextField port;
    private JTextField username;
    private JTextField password;
    private JButton connectButton;
    private JLabel status;
    private JFrame frame;
    public FTPHandler FTP;

    public ConnectionUI() {
        frame = new JFrame("Connecting Info");
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Dimension size =  new Dimension(500, 200);
        frame.setSize(size);
        frame.setPreferredSize(size);
        frame.setLocationRelativeTo(null);


        connectButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {

            }
        });


        frame.pack();
        frame.setVisible(true);
    }
    public void connectButton(){
        if(checkFields()){
            status.setText("Attempting connection...");
            //FTP = new FTPHandler();
        }
    }
    private boolean checkFields(){
        if(!address.getText().isEmpty())
            if(!port.getText().isEmpty())
                if(!username.getText().isEmpty())
                    if(!password.getText().isEmpty())
                        return true;
        status.setText("One or more fields are empty.");
        return false;
    }
}
