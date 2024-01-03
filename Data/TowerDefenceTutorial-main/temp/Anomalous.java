import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HangmanGUI extends JFrame implements ActionListener {
    private String word = "example";
    private StringBuilder hiddenWord = new StringBuilder();
    private int attempts = 6;
    private JLabel attemptsLabel;
    private JLabel wordLabel;
    private JTextField inputField;

    public HangmanGUI() {
        for (int i = 0; i < word.length(); i++) {
            hiddenWord.append("_");
        }
        setLayout(new GridLayout(3, 1));
        attemptsLabel = new JLabel("Attempts left: " + attempts);
        add(attemptsLabel);
        wordLabel = new JLabel("Word: " + hiddenWord);
        add(wordLabel);
        inputField = new JTextField();
        inputField.addActionListener(this);
        add(inputField);
        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String guess = inputField.getText();
        inputField.setText("");
        if (guess.length() != 1) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter a single letter.");
            return;
        }
        char letter = guess.charAt(0);
        if (word.contains(guess)) {
            for (int i = 0; i < word.length(); i++) {
                if (word.charAt(i) == letter) {
                    hiddenWord.setCharAt(i, letter);
                }
            }
            wordLabel.setText("Word: " + hiddenWord);
            if (!hiddenWord.toString().contains("_")) {
                JOptionPane.showMessageDialog(this, "You won! The word was: " + word);
                System.exit(0);
            }
        } else {
            attempts--;
            attemptsLabel.setText("Attempts left: " + attempts);
            if (attempts == 0) {
                JOptionPane.showMessageDialog(this, "You lost! The word was: " + word);
                System.exit(0);
            }
        }
    }

    public static void main(String[] args) {
        new HangmanGUI();
    }
}
