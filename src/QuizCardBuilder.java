import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class QuizCardBuilder {
    private JTextArea question;
    private JTextArea answer;
    private ArrayList<QuizCard> cardList;
    private JFrame frame;

    public void go() {
        // build and display gui

        frame = new JFrame("Quiz Card Builder");
        JPanel mainPanel = new JPanel();
        Font bigFont = new Font(Font.SANS_SERIF, Font.BOLD, 24);


        question = new JTextArea(6, 20);
        question.setLineWrap(true);
        question.setWrapStyleWord(true);
        question.setFont(bigFont);

        JScrollPane qScroller = new JScrollPane(question);
        qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);


        answer = new JTextArea(6, 20);
        answer.setLineWrap(true);
        answer.setWrapStyleWord(true);
        answer.setFont(bigFont);

        JScrollPane aScroller = new JScrollPane(answer);
        aScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        aScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);


        JButton nextButton = new JButton("Next Card");

        cardList = new ArrayList<QuizCard>();

        JLabel qLabel = new JLabel("Question:");
        JLabel aLabel = new JLabel("Answer:");


        mainPanel.add(qLabel);
        mainPanel.add(qScroller);
        mainPanel.add(aLabel);
        mainPanel.add(aScroller);
        mainPanel.add(nextButton);

        nextButton.addActionListener(new NextCardListener());

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem newMenuItem = new JMenuItem("New");
        JMenuItem saveMenuItem = new JMenuItem("Save");
        newMenuItem.addActionListener(new NewMenuListener());
        saveMenuItem.addActionListener(new SaveMenuListener());
        fileMenu.add(newMenuItem);
        fileMenu.add(saveMenuItem);
        menuBar.add(fileMenu);

        frame.setJMenuBar(menuBar);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
        frame.setSize(500, 600);
        frame.setVisible(true);

    }

    private class NextCardListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ev) {
            // add the current card to the list and clear the text areas
            saveCard();
            clearCard();
        }
    }

    private class SaveMenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ev) {
            // bring up the file dialog box
            // let the user name and save the set
            saveCard();

            JFileChooser fileSave = new JFileChooser();
            fileSave.showSaveDialog(frame);
            saveFile(fileSave.getSelectedFile());
        }
    }

    private class NewMenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ev) {
            // clear out the card list and clear out the text areas
            cardList.clear();
            clearCard();
        }
    }

    private void clearCard() {
        question.setText("");
        answer.setText("");

        question.requestFocus();
    }

    private void saveCard() {
        QuizCard card = new QuizCard(question.getText(), answer.getText());
        cardList.add(card);
    }

    private void saveFile(File file) {
        // iterate through the list of cards, and write each one out to a text file
        // in a parsable way (in other words, with clear separations between parts)

        try ( BufferedWriter writer = new BufferedWriter(new FileWriter(file)) ) {

            for(QuizCard card : cardList) {
                writer.write(card.getQuestion() + "/");
                writer.write(card.getAnswer() + "\n");
            }

        } catch (IOException e) {
            System.out.println("Couldn't write the cardlist out.");
            e.printStackTrace();
        } finally {
            clearCard();
        }

    }

}
