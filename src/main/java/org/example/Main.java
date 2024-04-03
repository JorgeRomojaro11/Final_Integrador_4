package org.example;

import javax.swing.*;
import Editor.TextEditor;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TextEditor gui = new TextEditor();
            gui.setVisible(true);
        });
    }
}