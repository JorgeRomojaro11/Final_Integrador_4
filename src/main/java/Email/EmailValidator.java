package Email;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import javax.swing.text.BadLocationException;

public class EmailValidator extends JPanel implements DocumentListener {
    private static final String EMAIL_REGEX = "^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)*(\\.[a-zA-Z]{2,})$";
    private JLabel validationLabel;

    public EmailValidator() {
        validationLabel = new JLabel();
        add(validationLabel);
    }

    public void validateEmail(String email) {
        if (email.matches(EMAIL_REGEX)) {
            validationLabel.setText("Valid email");
            validationLabel.setForeground(Color.GREEN);
        } else {
            validationLabel.setText("Invalid email");
            validationLabel.setForeground(Color.RED);
        }
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        try {
            validateEmail(e.getDocument().getText(0, e.getDocument().getLength()));
        } catch (BadLocationException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        try {
            validateEmail(e.getDocument().getText(0, e.getDocument().getLength()));
        } catch (BadLocationException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        try {
            validateEmail(e.getDocument().getText(0, e.getDocument().getLength()));
        } catch (BadLocationException ex) {
            ex.printStackTrace();
        }
    }
}