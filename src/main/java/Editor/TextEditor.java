package Editor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class TextEditor extends JFrame {
    private JTextArea textArea;

    public TextEditor() {
        setTitle("Text Editor");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        textArea = new JTextArea();
        add(textArea, BorderLayout.CENTER);

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem newMenuItem = new JMenuItem("New");
        JMenuItem openMenuItem = new JMenuItem("Open");
        JMenuItem saveMenuItem = new JMenuItem("Save");
        JMenuItem compareMenuItem = new JMenuItem("Compare");

        newMenuItem.addActionListener(new NewAction());
        openMenuItem.addActionListener(new OpenAction());
        saveMenuItem.addActionListener(new SaveAction());
        compareMenuItem.addActionListener(new CompareAction());

        fileMenu.add(newMenuItem);
        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.add(compareMenuItem);
        menuBar.add(fileMenu);

        setJMenuBar(menuBar);
    }

    private class NewAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            textArea.setText("");
        }
    }

    private class OpenAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                    textArea.read(reader, null);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }
    }

    private class SaveAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showSaveDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(selectedFile))) {
                    textArea.write(writer);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }
    }

    private class CompareAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setMultiSelectionEnabled(false);
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File file1 = fileChooser.getSelectedFile();
                returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File file2 = fileChooser.getSelectedFile();
                    try {
                        BufferedReader reader1 = new BufferedReader(new FileReader(file1));
                        BufferedReader reader2 = new BufferedReader(new FileReader(file2));

                        String line1 = reader1.readLine();
                        String line2 = reader2.readLine();

                        boolean areEqual = true;
                        int lineNum = 1;
                        StringBuilder differences = new StringBuilder();

                        while (line1 != null || line2 != null) {
                            if(line1 == null || line2 == null) {
                                areEqual = false;
                                differences.append("One of the files has more lines than the other.\n");
                                break;
                            } else if (! line1.equalsIgnoreCase(line2)) {
                                areEqual = false;
                                differences.append("Line ").append(lineNum).append(" is different.\n");
                            }

                            line1 = reader1.readLine();
                            line2 = reader2.readLine();
                            lineNum++;
                        }

                        if (areEqual) {
                            JOptionPane.showMessageDialog(null, "The files are identical.");
                        } else {
                            JOptionPane.showMessageDialog(null, "The files are not identical. Differences:\n" + differences.toString());
                        }

                        reader1.close();
                        reader2.close();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
        }
    }
    public String getText() {
        return textArea.getText();
    }
}