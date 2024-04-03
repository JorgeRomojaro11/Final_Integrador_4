package Editor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class Herramientas extends TextEditor {
    public Herramientas() {
        super();

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
    }

    private class WordCountAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String text = getText();
            String[] words = text.split("\\s+");
            JOptionPane.showMessageDialog(null, "Word count: " + words.length);
        }
    }

    private class TextStatsAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String text = getText();
            String[] words = text.split("\\s+");
            Map<String, Integer> wordCounts = new HashMap<>();
            for (String word : words) {
                wordCounts.put(word, wordCounts.getOrDefault(word, 0) + 1);
            }
            StringBuilder stats = new StringBuilder();
            for (Map.Entry<String, Integer> entry : wordCounts.entrySet()) {
                stats.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
            }
            JOptionPane.showMessageDialog(null, "Text statistics:\n" + stats.toString());
        }
    }

    private class SearchWordAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String wordToSearch = JOptionPane.showInputDialog("Enter the word to search");
            String text = getText();
            String[] words = text.split("\\s+");
            int count = 0;
            for (String word : words) {
                if (word.equals(wordToSearch)) {
                    count++;
                }
            }
            JOptionPane.showMessageDialog(null, "The word '" + wordToSearch + "' appears " + count + " times.");
        }
    }
}
