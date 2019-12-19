import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class QuizCardBuilder {

    public void go() {
        // build and display gui
    }

    private class NextCardListener implements ActionListener {

        public void actionPerformed(ActionEvent ev) {
            // add the current card to the list and clear the text areas
        }

    }

    private class SaveMenuListener implements ActionListener {

        public void actionPerformed(ActionEvent ev) {
            // bring up the file dialog box
            // let the user name and save the set
        }

    }

    private class NewMenuListener implements ActionListener {

        public void actionPerformed(ActionEvent ev) {
            // clear out the card list and clear out the text areas
        }

    }

    private void saveFile(File file) {
        // iterate through the lsit of cards, and write each one out to a text file
        // in a prasable way (in other words, with clear separations between parts)
    }

}
