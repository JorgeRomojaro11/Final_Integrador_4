package org.example;

import javax.swing.*;
import java.awt.*;
import Editor.Herramientas;
import Email.EmailValidator;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Herramientas gui = new Herramientas();
            gui.setVisible(true);

            // Add a mouse motion listener to the text area to update the mouse position label
            gui.getTextArea().addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseMoved(MouseEvent e) {
                    gui.setStatusBarText("Mouse Position: " + e.getX() + ", " + e.getY());
                }
            });

            // Add the email validator to the GUI
            EmailValidator emailValidator = new EmailValidator();
            gui.getContentPane().add(emailValidator, BorderLayout.NORTH);
        });
    }
}