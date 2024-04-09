package org.example;

import javax.swing.*;
import java.awt.*;
import Editor.Herramientas;
import Editor.Inicio;
import Email.EmailValidator;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Crea y muestra la interfaz de inicio
            Inicio inicio = new Inicio();
            inicio.setVisible(true);

            // Crea y muestra la interfaz principal
            Herramientas gui = new Herramientas();
            gui.setVisible(true);
            gui.setLocationRelativeTo(null); // Centra la ventana en la pantalla

            // Añade un listener de movimiento del ratón al área de texto para actualizar la etiqueta de posición del ratón
            gui.getTextArea().addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseMoved(MouseEvent e) {
                    gui.setStatusBarText("Mouse Position: " + e.getX() + ", " + e.getY());
                }
            });

            // Añade el validador de correo electrónico a la GUI
            EmailValidator emailValidator = new EmailValidator();
            gui.getContentPane().add(emailValidator, BorderLayout.NORTH);
        });
    }
}