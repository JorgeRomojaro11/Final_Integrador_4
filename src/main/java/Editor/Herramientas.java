package Editor;

import Email.EmailValidator;
import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Herramientas extends TextEditor {
    private GestorDeContactos gestorDeContactos;
    private JLabel mousePositionLabel;
    private JPanel statusBar;
    private JTextArea textArea;
    private JScrollPane scrollPane;

    public Herramientas() {
        super();
        this.gestorDeContactos = new GestorDeContactos();

        statusBar = new JPanel(new BorderLayout());
        textArea = new JTextArea();
        scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        JMenuBar menuBar = getJMenuBar();

        JMenu toolsMenu = new JMenu("Tools");
        JMenuItem wordCountMenuItem = new JMenuItem("Word Count");
        JMenuItem textStatsMenuItem = new JMenuItem("Text Statistics");
        JMenuItem searchWordMenuItem = new JMenuItem("Search Word");

        wordCountMenuItem.addActionListener(new WordCountAction());
        textStatsMenuItem.addActionListener(new TextStatsAction());
        searchWordMenuItem.addActionListener(new SearchWordAction());

        toolsMenu.add(wordCountMenuItem);
        toolsMenu.add(textStatsMenuItem);
        toolsMenu.add(searchWordMenuItem);
        menuBar.add(toolsMenu);

        JMenu contactsMenu = new JMenu("Contacts");
        JMenuItem addContactMenuItem = new JMenuItem("Add Contact");
        JMenuItem deleteContactMenuItem = new JMenuItem("Delete Contact");
        JMenuItem searchContactMenuItem = new JMenuItem("Search Contact");

        addContactMenuItem.addActionListener(new AddContactAction());
        deleteContactMenuItem.addActionListener(new DeleteContactAction());
        searchContactMenuItem.addActionListener(new SearchContactAction());

        contactsMenu.add(addContactMenuItem);
        contactsMenu.add(deleteContactMenuItem);
        contactsMenu.add(searchContactMenuItem);
        menuBar.add(contactsMenu);

        JMenu filesMenu = new JMenu("Windows");
        JMenuItem newWindowMenuItem = new JMenuItem("New Window");

        newWindowMenuItem.addActionListener(new NewWindowAction());

        filesMenu.add(newWindowMenuItem);
        menuBar.add(filesMenu);

        JMenu openFilesMenu = new JMenu("Open Files");
        JMenuItem openFilesMenuItem = new JMenuItem("Open Files");
        openFilesMenuItem.addActionListener(new OpenFilesAction());
        openFilesMenu.add(openFilesMenuItem);
        menuBar.add(openFilesMenu);

        mousePositionLabel = new JLabel();
        mousePositionLabel.setHorizontalAlignment(JLabel.RIGHT);
        statusBar.add(mousePositionLabel, BorderLayout.EAST);

        textArea.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                mousePositionLabel.setText("Mouse Position: " + e.getX() + ", " + e.getY());
            }
        });

        getContentPane().add(statusBar, BorderLayout.SOUTH);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
    }

    public JPanel getStatusBar() {
        return statusBar;
    }

    public JTextArea getTextArea() {
        return textArea;
    }

    public void setStatusBarText(String text) {
        mousePositionLabel.setText(text);
    }

    private class WordCountAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String text = textArea.getText();
            String[] words = text.split("\\s+");
            int wordCount = words.length;
            JOptionPane.showMessageDialog(null, "Word count: " + wordCount);
        }
    }

    private class TextStatsAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String text = textArea.getText();
            String[] words = text.split("\\s+");
            String[] lines = text.split("\n");
            int wordCount = words.length;
            int lineCount = lines.length;
            int charCount = text.length();
            JOptionPane.showMessageDialog(null, "Word count: " + wordCount + "\nLine count: " + lineCount + "\nCharacter count: " + charCount);
        }
    }

    private class SearchWordAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String wordToSearch = JOptionPane.showInputDialog("Enter the word to search");
            String text = textArea.getText();
            if (text.contains(wordToSearch)) {
                JOptionPane.showMessageDialog(null, "The word '" + wordToSearch + "' was found in the text.");
            } else {
                JOptionPane.showMessageDialog(null, "The word '" + wordToSearch + "' was not found in the text.");
            }
        }
    }

    private class AddContactAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String nombre = JOptionPane.showInputDialog("Enter the contact's name");

            JTextField emailField = new JTextField();
            EmailValidator emailValidator = new EmailValidator();
            emailField.getDocument().addDocumentListener(emailValidator);
            JOptionPane.showConfirmDialog(null, new Object[]{ "Enter the contact's email", emailField, emailValidator }, "Input", JOptionPane.OK_CANCEL_OPTION);
            String email = emailField.getText();

            String numeroDeTelefono = JOptionPane.showInputDialog("Enter the contact's phone number");

            if (!Contacto.isValidEmail(email)) {
                JOptionPane.showMessageDialog(null, "Invalid email address");
                return;
            }

            Contacto contacto = new Contacto(nombre, email, numeroDeTelefono);
            gestorDeContactos.agregarContacto(contacto);
        }
    }

    private class DeleteContactAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String nombre = JOptionPane.showInputDialog("Enter the name of the contact to delete");
            Contacto contacto = gestorDeContactos.buscarContacto(nombre);
            if (contacto != null) {
                gestorDeContactos.eliminarContacto(contacto);
            } else {
                JOptionPane.showMessageDialog(null, "Contact not found");
            }
        }
    }

    private class SearchContactAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String nombre = JOptionPane.showInputDialog("Enter the name of the contact to search");
            Contacto contacto = gestorDeContactos.buscarContacto(nombre);
            if (contacto != null) {
                JOptionPane.showMessageDialog(null, "Contact found: \nName: " + contacto.getNombre() + "\nEmail: " + contacto.getEmail() + "\nPhone number: " + contacto.getNumeroDeTelefono());
            } else {
                JOptionPane.showMessageDialog(null, "Contact not found");
            }
        }
    }

    private class NewWindowAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            SwingUtilities.invokeLater(() -> {
                Herramientas newWindow = new Herramientas();
                newWindow.setVisible(true);
            });
        }
    }

    private class OpenFileAction implements ActionListener {
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

    private class OpenFilesAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setMultiSelectionEnabled(true);
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File[] selectedFiles = fileChooser.getSelectedFiles();
                for (File file : selectedFiles) {
                    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                        textArea.read(reader, null);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
        }
    }
}