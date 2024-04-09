package Editor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Inicio extends JFrame {
    private String nombreUsuario;

    public Inicio() {
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JDialog dialog = new JDialog(this, "Inicio", true);
        dialog.setLayout(new BorderLayout());

        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("resources/UAX.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ImageIcon icon = new ImageIcon((Image) img);
        JLabel label = new JLabel(icon);
        dialog.add(label, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new BorderLayout());
        JLabel instructionLabel = new JLabel("Introduce el nombre de usuario");
        inputPanel.add(instructionLabel, BorderLayout.NORTH);

        JTextField usernameField = new JTextField();
        usernameField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nombreUsuario = usernameField.getText();
                if (isValidUsername(nombreUsuario)) {
                    dialog.dispose(); // Closes the dialog
                } else {
                    JOptionPane.showMessageDialog(dialog, "Nombre de usuario no válido. Inténtalo de nuevo.");
                }
            }
        });
        inputPanel.add(usernameField, BorderLayout.SOUTH);
        dialog.add(inputPanel, BorderLayout.SOUTH);

        dialog.pack();
        dialog.setSize(800, 600); // Set the size of the dialog
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);

        setLocationRelativeTo(null);
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    private boolean isValidUsername(String username) {
        // Add your validation logic here. For example:
        return username != null && !username.isEmpty() && username.matches("[A-Za-z0-9_]+");
    }
}