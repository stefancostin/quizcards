import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    JFrame frame;

    public static void main(String[] args) {
        Main main = new Main();
        main.go();
    }

    private void go() {
        JButton builderButton = new JButton("Build Card Set");
        JButton playerButton = new JButton("Load Quiz");
        builderButton.addActionListener(new InitBuilderListener());
        playerButton.addActionListener(new InitPlayerListener());

        JPanel mainPanel = new JPanel();
        mainPanel.add(builderButton);
        mainPanel.add(playerButton);

        frame = new JFrame("QuizCards");
        frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(280, 60);
        frame.setVisible(true);
    }

    private class InitBuilderListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            QuizCardBuilder builder = new QuizCardBuilder();
            builder.go();
            frame.setVisible(false);
        }
    }

    private class InitPlayerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            QuizCardPlayer reader = new QuizCardPlayer();
            reader.go();
            frame.setVisible(false);
        }
    }

}
