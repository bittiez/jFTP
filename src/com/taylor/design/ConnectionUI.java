package com.taylor.design;

import com.taylor.helper.FTPHandler;
import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPException;
import it.sauronsoftware.ftp4j.FTPIllegalReplyException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import  java.util.prefs.*;


/**
 * Created by tad on 12/9/2014.
 */
public class ConnectionUI implements Runnable{
    public FTPHandler FTP;
    private JPanel panel1;
    private JPanel connectionInfo;
    private JTextField address;
    private JTextField port;
    private JTextField username;
    private JTextField password;
    private JButton connectButton;
    private JLabel status;
    private JFrame frame;

    public ConnectionUI(){
        frame = new JFrame("Connecting Info");
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Dimension size = new Dimension(500, 200);
        frame.setSize(size);
        frame.setPreferredSize(size);
        frame.setLocationRelativeTo(null);

        address.setText(Preferences.userRoot().get("SERVER", "jshare.ddns.net"));
        port.setText(Preferences.userRoot().get("PORT", "21"));
        username.setText(Preferences.userRoot().get("USERNAME", "Username"));
        password.setText(Preferences.userRoot().get("PASSWORD", "Password"));

        connectButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                connectButton();
            }
        });


        frame.pack();
        frame.setVisible(true);
    }

    public void connectButton() {
        if (checkFields()) {
            new Thread(this).start();
        }
    }

    private boolean checkFields() {
        if (!address.getText().isEmpty())
            if (!port.getText().isEmpty())
                if (!username.getText().isEmpty())
                    if (!password.getText().isEmpty())
                        return true;
        status.setText("One or more fields are empty.");
        return false;
    }

    @Override
    public void run() {
        status.setText("Attempting connection...");
        try {
            FTP = new FTPHandler();
            FTP.client = new FTPClient();
            FTP.client.connect(address.getText(), Integer.parseInt(port.getText()));
            FTP.client.login(username.getText(), password.getText());
        } catch (FTPException e) {
            e.printStackTrace();
            status.setText("Failed to connect..");
        } catch (IOException e) {
            e.printStackTrace();
            status.setText("Failed to connect..");
        } catch (FTPIllegalReplyException e) {
            e.printStackTrace();
            status.setText("Failed to connect..");
        } finally {
            status.setText("Connection successful..");
            FileBrowser fb = new FileBrowser(FTP);
            frame.dispose();
            Preferences.userRoot().put("SERVER", address.getText());
            Preferences.userRoot().put("PORT", port.getText());
            Preferences.userRoot().put("USERNAME", username.getText());
            Preferences.userRoot().put("PASSWORD", password.getText());
        }
    }
}
