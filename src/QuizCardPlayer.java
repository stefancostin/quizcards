import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class QuizCardPlayer {
    private JTextArea display;
    private JTextArea answer;
    private ArrayList<QuizCard> cardList;
    private QuizCard currentCard;
    private int currentCardIndex;
    private JFrame frame;
    private JButton nextButton;
    private boolean isShowAnswer;

    public void go() {
        // build and display gui
        frame = new JFrame("Quiz Card Player");
        JPanel mainPanel = new JPanel();
        Font bigFont = new Font(Font.SANS_SERIF, Font.BOLD, 24);

        display = new JTextArea(10, 20);
        display.setFont(bigFont);
        display.setLineWrap(true);
        display.setEditable(false);

        JScrollPane qScroller = new JScrollPane(display);
        qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        nextButton = new JButton("Show Question");

        mainPanel.add(qScroller);
        mainPanel.add(nextButton);

        nextButton.addActionListener(new NextCardListener());

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu(("File"));
        JMenuItem loadMenuItem = new JMenuItem("Load Card Set");
        loadMenuItem.addActionListener(new OpenMenuListener());
        fileMenu.add(loadMenuItem);
        menuBar.add(fileMenu);

        frame.setJMenuBar(menuBar);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
        frame.setSize(500, 640);
        frame.setVisible(true);
    }

    private class NextCardListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ev) {
            // if this is a question, show the answer, otherwise show next question
            // set a flag for whether we're viewing a question or answer
            if (isShowAnswer) {
                // show the answer because the user has seen the question
                display.setText(currentCard.getAnswer());
                nextButton.setText("Next Card");
                isShowAnswer = false;
            } else {
                // show the next question
                if (currentCardIndex < cardList.size()) {

                    showNextCard();

                } else {
                    // there are no more cards
                    display.setText("That was the last card");
                    nextButton.setEnabled(false);
                }
            }
        }
    }

    private class OpenMenuListener implements  ActionListener {
        @Override
        public void actionPerformed(ActionEvent ev) {
            // bring up a file dialog box
            // let the user navigate to and choose a card set to open
            JFileChooser fileOpen = new JFileChooser();
            fileOpen.showOpenDialog(frame);
            loadFile(fileOpen.getSelectedFile());
        }
    }

    private void loadFile(File file) {
        // must build an ArrayList of cards, by reading them from a text file
        // called from the OpenMenuListener event handler, reads the file one line at a time
        // and tells the makeCard() method to make a new card out of the line
        // (one line in the file holds both the question and answer, separated by a "/")
        cardList = new ArrayList<QuizCard>();
        try ( BufferedReader reader = new BufferedReader (new FileReader(file)) ) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                makeCard(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File was not found");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Couldn't read the card file");
            e.printStackTrace();
        }
    }

    private void makeCard(String lineToParse) {
        // called by the loadFile method, takes a line from the text file
        // and parses into two pieces -- question and answer -- and creates a new QuizCard
        // and adds it to the ArrayList called cardList
        String[] result = lineToParse.split("/");
        QuizCard card = new QuizCard(result[0], result[1]);
        cardList.add(card);
        System.out.println("made a card");
    }

    private void showNextCard() {
        currentCard = cardList.get(currentCardIndex);
        currentCardIndex++;
        display.setText(currentCard.getQuestion());
        nextButton.setText("Show Answer");
        isShowAnswer = true;
    }

}
