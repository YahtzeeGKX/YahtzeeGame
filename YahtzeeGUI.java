import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import java.awt.geom.Line2D;

public class YahtzeeGUI extends JFrame implements ActionListener {

    // creates constants for the buttons heights and widths
    private static final int BUTTON_HEIGHT = 20;
    private static final int BUTTON_WIDTH = 150;

    // initalizes the button variables
    private JPanel scoreButtonPanel;
    private JPanel diceButtonPanel;

    private JButton[] diceButtons;
    private JComponent[] scoreButtons;

    private int[] rolls;

    private final ImageIcon[] pics = {new ImageIcon("1.png"), new ImageIcon("2.png"), new ImageIcon("3.png"), new ImageIcon("4.png"), new ImageIcon("5.png"), new ImageIcon("6.png")};

    Die myDie;

    public void run() {
        // creates a new frame for the scoreboard
        JFrame frame = new JFrame("Yahtzee!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        myDie = new Die();
        rolls = new int[5];

        for(int i=0; i<rolls.length; i++)
            rolls[i] = myDie.roll();

        initButton();

        frame.setLayout(new GridLayout(2,1));
        frame.add(diceButtonPanel);
        frame.add(scoreButtonPanel);
        frame.pack();
        frame.setVisible(true);
    }

    // Sets up the buttons
    private void initButton() {
        // creates a new panel for the buttons
        scoreButtonPanel = new JPanel();
        scoreButtonPanel.setLayout(new GridLayout(13, 1));
        diceButtonPanel = new JPanel();
        diceButtonPanel.setLayout(new GridLayout(1,5));

        scoreButtons = new JComponent[14];
        diceButtons = new JButton[5];

        //Sets up all the different scoring options
        scoreButtons[0] = new JButton("Roll Unselected Dice");
        scoreButtons[1] = new JButton("Ones");
        scoreButtons[2] = new JButton("Twos");
        scoreButtons[3] = new JButton("Threes");
        scoreButtons[4] = new JButton("Fours");
        scoreButtons[5] = new JButton("Fives");
        scoreButtons[6] = new JButton("Sixes");
        scoreButtons[7] = new JButton("Three of a Kind");
        scoreButtons[8] = new JButton("Four of a Kind");
        scoreButtons[9] = new JButton("Full House");
        scoreButtons[10] = new JButton("Small Straight");
        scoreButtons[11] = new JButton("Large Straight");
        scoreButtons[12] = new JButton("Chance");
        scoreButtons[13] = new JButton("Yahtzee!");

        for(int i=0; i<diceButtons.length; i++){
            diceButtons[i] = new JButton();
            diceButtons[i].setIcon(pics[rolls[i]-1]);
            diceButtons[i].setBackground(Color.WHITE);
            diceButtons[i].addActionListener(this);
            diceButtonPanel.add(diceButtons[i]);
        }

        for(JComponent button: scoreButtons){
            ((JButton)button).addActionListener(this);
            scoreButtonPanel.add(button);
        }
    }

    public void actionPerformed(ActionEvent e){
        JButton button = (JButton) e.getSource();
        for(ImageIcon icon: pics){
            if(icon == button.getIcon()){
                changeDieBackground(button);
                return;
            }
        }
        scoreButtonClicked(button);
    }
    // The code executed when a button is clicked
    private void scoreButtonClicked(JButton button) {
        int score = Points.getPoints(button, rolls);
        for(int i=0; i<scoreButtons.length; i++)
            if(scoreButtons[i] == button){
                scoreButtonPanel.remove(button);
                scoreButtons[i] = new JLabel("" + ((JButton)scoreButtons[i]).getText() + ": " + score);
        }
        update();
    }

    private void changeDieBackground(JButton button) {
        if(button.getBackground() == Color.WHITE)
            button.setBackground(Color.YELLOW);
        else
            button.setBackground(Color.WHITE);
        update();
    }

    private void update(){
        for(JComponent compon: scoreButtons){
            scoreButtonPanel.add(compon);
        }
        for(JButton die: diceButtons){
            diceButtonPanel.add(die);
        }
        scoreButtonPanel.repaint();
        diceButtonPanel.repaint();
    }
}
