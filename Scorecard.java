import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import java.awt.geom.Line2D;

public class Scorecard extends JFrame {

    // creates constants for the buttons heights and widths
    private static final int BUTTON_HEIGHT = 40;
    private static final int BUTTON_WIDTH = 150;

    // initalizes the button variables
    JButton onesButton;
    JButton twosButton;
    JButton threesButton;
    JButton foursButton;
    JButton fivesButton;
    JButton sixesButton;
    JButton threeOfAKindButton;
    JButton fourOfAKindButton;
    JButton fullHouseButton;
    JButton smallStraightButton;
    JButton largeStraightButton;
    JButton chanceButton;
    JButton yahtzeeButton;
    JPanel buttonPanel;
    
    JLabel onesLabel;
    JPanel labelPanel;

    Die die = new Die();

    public void run() {
        // creates a new frame for the scoreboard
        JFrame frame = new JFrame("Scoreboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initButton();

        initLabel();
        
        // adds the panel to the frame
        frame.setContentPane(buttonPanel);
        //frame.getContentPane().add(labelPanel);
        frame.setSize(BUTTON_WIDTH, 450);
        frame.setVisible(true);
    }

    // Sets up the buttons
    private void initButton() {
        // creates a new panel for the buttons
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(13, 1));

        // assigns buttons to all the button variables
        onesButton = new JButton("Ones");
        twosButton = new JButton("Twos");
        threesButton = new JButton("Threes");
        foursButton = new JButton("Fours");
        fivesButton = new JButton("Fives");
        sixesButton = new JButton("Sixes");
        threeOfAKindButton = new JButton("Three of a Kind");
        fourOfAKindButton = new JButton("Four of a Kind");
        fullHouseButton = new JButton("Full House");
        smallStraightButton = new JButton("Small Straight");
        largeStraightButton = new JButton("Large Straight");
        chanceButton = new JButton("Chance");
        yahtzeeButton = new JButton("Yahtzee!");

        // sets the size of all the buttons
        onesButton.setPreferredSize(new Dimension(BUTTON_HEIGHT, BUTTON_WIDTH));
        twosButton.setPreferredSize(new Dimension(BUTTON_HEIGHT, BUTTON_WIDTH));
        threesButton.setPreferredSize(new Dimension(BUTTON_HEIGHT, BUTTON_WIDTH));
        foursButton.setPreferredSize(new Dimension(BUTTON_HEIGHT, BUTTON_WIDTH));
        fivesButton.setPreferredSize(new Dimension(BUTTON_HEIGHT, BUTTON_WIDTH));
        sixesButton.setPreferredSize(new Dimension(BUTTON_HEIGHT, BUTTON_WIDTH));
        threeOfAKindButton.setPreferredSize(new Dimension(BUTTON_HEIGHT, BUTTON_WIDTH));
        fourOfAKindButton.setPreferredSize(new Dimension(BUTTON_HEIGHT, BUTTON_WIDTH));
        fullHouseButton.setPreferredSize(new Dimension(BUTTON_HEIGHT, BUTTON_WIDTH));
        smallStraightButton.setPreferredSize(new Dimension(BUTTON_HEIGHT, BUTTON_WIDTH));
        largeStraightButton.setPreferredSize(new Dimension(BUTTON_HEIGHT, BUTTON_WIDTH));
        chanceButton.setPreferredSize(new Dimension(BUTTON_HEIGHT, BUTTON_WIDTH));
        yahtzeeButton.setPreferredSize(new Dimension(BUTTON_HEIGHT, BUTTON_WIDTH));

        //adds the buttons to the JPanel
        buttonPanel.add(onesButton);
        buttonPanel.add(twosButton);
        buttonPanel.add(threesButton);
        buttonPanel.add(foursButton);
        buttonPanel.add(fivesButton);
        buttonPanel.add(sixesButton);
        buttonPanel.add(threeOfAKindButton);
        buttonPanel.add(fourOfAKindButton);
        buttonPanel.add(fullHouseButton);
        buttonPanel.add(smallStraightButton);
        buttonPanel.add(largeStraightButton);
        buttonPanel.add(chanceButton);
        buttonPanel.add(yahtzeeButton);

        // actionListeners added for all the buttons
        onesButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    buttonClicked(onesButton);
                }
            });
        twosButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    buttonClicked(twosButton);
                }
            });
        threesButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    buttonClicked(threesButton);
                }
            });
        foursButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    buttonClicked(foursButton);
                }
            });
        fivesButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    buttonClicked(fivesButton);
                }
            });
        sixesButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    buttonClicked(sixesButton);
                }
            });
        threeOfAKindButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    buttonClicked(threeOfAKindButton);
                }
            });
        fourOfAKindButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    buttonClicked(fourOfAKindButton);
                }
            });
        fullHouseButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    buttonClicked(fullHouseButton);
                }
            });
        smallStraightButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    buttonClicked(smallStraightButton);
                }
            });
        largeStraightButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    buttonClicked(largeStraightButton);
                }
            });
        chanceButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    buttonClicked(chanceButton);
                }
            });
        yahtzeeButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    buttonClicked(yahtzeeButton);
                }
            });
    }
    
    private void initLabel() {
        labelPanel = new JPanel();
        labelPanel.setLayout(new GridLayout(1, 1));
        onesLabel = new JLabel("Ones");
        labelPanel.add(onesLabel);
    }

    // The code executed when a button is clicked
    private void buttonClicked(JButton button) {
        int[] rolls = new int[5];
        for(int i = 0; i < rolls.length; i++)
            rolls[i] = die.roll();

        System.out.println("" + Points.getPoints(button, rolls)); // gets the points for the roll
        
        buttonPanel.remove(button);
        buttonPanel.repaint(); // resets panel
    }

}
