package org.example;

import javax.swing.*;
import Editor.Herramientas;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Herramientas gui = new Herramientas();
            gui.setVisible(true);
        });
    }
}