import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import java.awt.geom.Line2D;

public class YahtzeeGUI extends JFrame implements ActionListener {
    private JPanel scoreButtonPanel;
    private JPanel diceButtonPanel;
    private JPanel scorePanel;
    
    private JButton[] diceButtons;
    private JComponent[] scoreButtons;

    private int[] rolls;
    private int rollsLeft, catsLeft, totScore, uppBonus, sessionHighScore;

    private final ImageIcon[] pics = {new ImageIcon("Pictures/1.png"), new ImageIcon("Pictures/2.png"), new ImageIcon("Pictures/3.png"), 
            new ImageIcon("Pictures/4.png"), new ImageIcon("Pictures/5.png"), new ImageIcon("Pictures/6.png")};

    Die myDie;

    public void run() {
        // creates a new frame for the scoreboard
        JFrame frame = new JFrame("Yahtzee!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(3,1));
        
        // creates a new panel for the buttons
        scoreButtonPanel = new JPanel();
        scoreButtonPanel.setLayout(new GridLayout(13, 1));
        diceButtonPanel = new JPanel();
        diceButtonPanel.setLayout(new GridLayout(1,5));
        scorePanel = new JPanel();
        scorePanel.setLayout(new GridLayout(1,1));
        
        myDie = new Die();
        rolls = new int[5];

        rollsLeft = 3;
        catsLeft = 13;
        uppBonus = 0;
        totScore = 0;
        sessionHighScore = 0;

        initButton();
        
        frame.add(diceButtonPanel);
        frame.add(scoreButtonPanel);
        frame.add(scorePanel);
        frame.pack();
        frame.setVisible(true);
    }

    private void reset(){
        rollsLeft = 3;
        catsLeft = 13;
        uppBonus = 0;
        totScore = 0;
        initButton();
    }
    
    // Sets up the buttons
    private void initButton() {
        scorePanel.add(new JLabel("Total Score: " + totScore));

        scoreButtons = new JComponent[14];
        diceButtons = new JButton[5];

        //Sets up all the different scoring options
        scoreButtons[0] = new JButton("Roll Unselected Dice (" + rollsLeft + " rolls remaining)");
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
            diceButtons[i].setBackground(Color.WHITE);
            diceButtons[i].addActionListener(this);
        }

        for(JComponent button: scoreButtons)
            ((JButton)button).addActionListener(this);

        rollUnselected();            

        update();
    }

    public void actionPerformed(ActionEvent e){
        JButton button = (JButton) e.getSource();
        if(button.getIcon() != null)
            changeDieBackground(button);
        else if(button == scoreButtons[0])
            rollUnselected();
        else
            scoreButtonClicked(button);
    }
    // The code executed when a button is clicked
    private void scoreButtonClicked(JButton button) {
        int score = Points.getPoints(button, rolls);
        totScore += score;
        rollsLeft = 3;
        catsLeft--;
        for(int i=0;i<scoreButtons.length;i++){
            if(scoreButtons[i] == button){
                scoreButtonPanel.remove(button);
                scoreButtons[i] = new JLabel("" + button.getText() + ": " + score);
                update();
            }
        }

        for(JButton j: diceButtons)
            j.setBackground(Color.WHITE);

        if(button.getText().equals("Ones") || button.getText().equals("Twos") || button.getText().equals("Threes")
        || button.getText().equals("Fours") || button.getText().equals("Five") || button.getText().equals("Sixes"))
            uppBonus += score;

        if(catsLeft == 0){
            if(uppBonus >= 63)
                totScore += 35;
            ((JButton)scoreButtons[0]).setText("New Game");
            if(totScore > sessionHighScore)
                sessionHighScore = totScore;
        }
        else
            rollUnselected();
    }

    private void changeDieBackground(JButton button) {
        if(button.getBackground() == Color.WHITE)
            button.setBackground(Color.YELLOW);
        else
            button.setBackground(Color.WHITE);
        update();
    }

    private void rollUnselected(){
        if(((JButton)scoreButtons[0]).getText().equals("New Game")){
            reset();
        }
        else if(rollsLeft > 0){
            rollsLeft--;
            for(int i=0; i<diceButtons.length; i++){
                if(diceButtons[i].getBackground() == Color.WHITE){
                    rolls[i] = myDie.roll();
                    diceButtons[i].setIcon(pics[rolls[i]-1]);
                }
            }
            update();
        }
    }

    private void update(){
        ((JButton)scoreButtons[0]).setText("Roll Unselected Dice (" + rollsLeft + " rolls remaining)");

        scoreButtonPanel.removeAll();
        diceButtonPanel.removeAll();
        scorePanel.removeAll();

        for(JComponent compo: scoreButtons)
            scoreButtonPanel.add(compo);
        for(JButton button: diceButtons)
            diceButtonPanel.add(button);
        scorePanel.add(new JLabel("Total Score: " + totScore + "           High-Score: " + sessionHighScore));

        scorePanel.repaint();
        scoreButtonPanel.repaint();
        diceButtonPanel.repaint();
    }
}
