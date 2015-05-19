import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

//This class creates, displays, and takes in the input for the Yahtzee Game's GUI. It also does the logic of telling when the game has ended, and keeps track of player score on a table. 
public class YahtzeeGUI extends JFrame implements ActionListener {
    //Declares the Panels and Frame
    private JPanel scoreButtonPanel;
    private JPanel diceButtonPanel;
    private JPanel tablePanel;
    private JPanel rollPanel;
    private JPanel winnerPanel;
    private JFrame frame;
    //A specific button for rolling the dice
    private JButton rollButton;

    private JLabel winner;

    //An array of buttons representing the dice
    private JButton[] diceButtons;
    //A table for the scorecard.
    private JTable table;

    //The different players, and a marker for which play is currently taking his/her turn
    private PlayerCard player1, player2, currPlayer;

    //An array for the actual rolls of the dice
    private int[] rolls;
    //The amount of rolls the player has remaining on a given turn
    private int rollsLeft;
    //A 2D array of Images that represent each side of the dice. The first row is the white, unselected dice, and the second row are a copy that have a yellow background.
    //(JButton.setBackground() cannot be used because Mac Java treats transparent color as white.
    private ImageIcon[][] pics = {
            {new ImageIcon("Pictures/1.png"), new ImageIcon("Pictures/2.png"), new ImageIcon("Pictures/3.png"), 
                new ImageIcon("Pictures/4.png"), new ImageIcon("Pictures/5.png"), new ImageIcon("Pictures/6.png")}, 
            {new ImageIcon("Pictures/1Y.png"), new ImageIcon("Pictures/2Y.png"), new ImageIcon("Pictures/3Y.png"), 
                new ImageIcon("Pictures/4Y.png"), new ImageIcon("Pictures/5Y.png"), new ImageIcon("Pictures/6Y.png")}};
    private AI ai;

    //Resets the game without creating a new frame. All information is put back to their default values.
    private void newGame() {
        ai = new AI(this);
        // creates a new panel for the buttons
        scoreButtonPanel = new JPanel();
        scoreButtonPanel.setLayout(new GridLayout(13, 1));

        diceButtonPanel = new JPanel();
        diceButtonPanel.setLayout(new GridLayout(1,5));

        rollPanel = new JPanel();

        tablePanel = new JPanel();

        winnerPanel = new JPanel();

        winner = new JLabel();

        rolls = new int[5];

        player1 = new PlayerCard("A");
        player2 = ai;
        currPlayer = player1;

        rollsLeft = 3;

        initTable();
        initButton();

        frame.add(diceButtonPanel, BorderLayout.NORTH);
        frame.add(scoreButtonPanel, BorderLayout.WEST);
        frame.add(rollPanel, BorderLayout.CENTER);
        frame.add(tablePanel, BorderLayout.EAST);
        frame.add(winnerPanel, BorderLayout.SOUTH);
        frame.pack();
        frame.setVisible(true);
    }
    //Creates the frame, then starts the game. This is called on the first creation of the game.
    public YahtzeeGUI() {
        // creates a new frame for the scoreboard
        frame = new JFrame("Yahtzee!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        newGame();
    }

    // Sets up the buttons
    private void initButton() {
        diceButtons = new JButton[5];

        for(int i=0; i<diceButtons.length; i++){
            diceButtons[i] = new JButton();
            diceButtons[i].setBackground(Color.WHITE);
            diceButtons[i].addActionListener(this);
        }

        for(int i=0; i<13; i++){
            ((JButton)player1.getComponentAtIndex(i)).addActionListener(this);
            ((JButton)player2.getComponentAtIndex(i)).addActionListener(this);
            ((JButton)player1.getComponentAtIndex(i)).setPreferredSize(new Dimension(150, 50));
            ((JButton)player2.getComponentAtIndex(i)).setPreferredSize(new Dimension(150, 50));
        }

        rollButton = new JButton("Roll Unselected Dice (" + rollsLeft + " rolls remain");
        rollButton.addActionListener(this);
        rollButton.setPreferredSize(new Dimension(400, 75));

        rollUnselected();
    }

    private void initTable() {
        table = new JTable(21, 3);
        table.setRowHeight(24);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.getColumn("A").setMinWidth(125);
        table.getColumn("A").setMaxWidth(125);
        table.setShowGrid(true);
        table.setGridColor(Color.BLACK);
        table.setEnabled(false);

        table.setValueAt("Upper Section", 0, 0);
        table.setValueAt("Player 1", 0, 1);
        table.setValueAt("Player 2", 0, 2);
        table.setValueAt("Aces", 1, 0);
        table.setValueAt("Twos", 2, 0);
        table.setValueAt("Threes", 3, 0);
        table.setValueAt("Fours", 4, 0);
        table.setValueAt("Fives", 5, 0);
        table.setValueAt("Sixes", 6, 0);
        table.setValueAt("Total Score", 7, 0);
        table.setValueAt("Bonus", 8, 0);
        table.setValueAt("", 9, 0);
        table.setValueAt("Lower Section", 10, 0);
        table.setValueAt("3 of a Kind", 11, 0);
        table.setValueAt("4 of a Kind", 12, 0);
        table.setValueAt("Full House", 13, 0);
        table.setValueAt("Sm. Straight", 14, 0);
        table.setValueAt("Lg. Straight", 15, 0);
        table.setValueAt("Yahtzee!", 16, 0);
        table.setValueAt("Chance", 17, 0);
        table.setValueAt("Total (Lwr. Sec.)", 18, 0);
        table.setValueAt("Total (Upr. Sec.)", 19, 0);
        table.setValueAt("Grand Total", 20, 0);

        table.repaint();
    }

    public void actionPerformed(ActionEvent e){
        JButton button = (JButton) e.getSource();
        determineButton(button);
    }

    public void determineButton(JButton button){
        if(button.getIcon() != null)
            changeDieBackground(button); 
        else if(button.getText().equals(rollButton.getText()))
            rollUnselected();
        else
            scoreButtonClicked(button);
    }
    
    public void determineButton(int index, boolean isDie){
        if(isDie)
        changeDieBackground(diceButtons[index]);
        else
        scoreButtonClicked((JButton)currPlayer.getComponentAtIndex(index));
    }

    // The code executed when a button is clicked
    private void scoreButtonClicked(JButton button) {
        currPlayer.removeButton(currPlayer.indexOfButton(button));
        rollsLeft = 3;

        int col = 1;
        if(currPlayer == player2)
            col = 2;

        for(int j=0;j<21;j++)
            if(table.getValueAt(j, 0).equals(button.getText()))
                table.setValueAt("" + Points.getPoints(button, rolls), j, col);

        for(JButton j: diceButtons)
            j.setIcon(pics[0][0]);

        if(currPlayer == player1)
            currPlayer = player2;
        else
            currPlayer = player1;

        update();
        
        if(player1.isDone() && player2.isDone()){
            rollButton.setText("New Game");
            findTotals();
            update();
        }
        else
            rollUnselected();
            
        if(currPlayer == player2) 
            ai.turn();
    }

    private void changeDieBackground(JButton button) {
        boolean found = false;
        for(int color = 0; color < 2 && !found; color++)
            for(int die = 0; die < pics[0].length && !found; die++)
                if(button.getIcon() == pics[color][die]){
                    button.setIcon(pics[Math.abs(color-1)][die]);
                    found = true;}

        update();
    }

    public void rollUnselected(){
        if(rollButton.getText().equals("New Game")){
            frame.remove(tablePanel);
            frame.remove(diceButtonPanel);
            frame.remove(scoreButtonPanel);
            frame.remove(rollPanel);
            frame.remove(winnerPanel);
            newGame();
        }
        else if(rollsLeft > 0){
            rollsLeft--;
            for(int i=0; i<diceButtons.length; i++){
                if(!isSelectedButton(i)){
                    rolls[i] = (int)((6 * Math.random()) + 1);
                    diceButtons[i].setIcon(pics[0][rolls[i]-1]);
                }
            }
            for(int i: rolls)
                System.out.print(i + ", ");
            System.out.println();
            update();
        }
    }

    private void update(){
        if(!rollButton.getText().equals("New Game"))
            rollButton.setText("Roll Unselected Dice (" + rollsLeft + " rolls remaining)");

        diceButtonPanel.removeAll();
        scoreButtonPanel.removeAll();
        rollPanel.removeAll();
        tablePanel.removeAll();

        for(int i=0; i<13; i++)
            scoreButtonPanel.add(currPlayer.getComponentAtIndex(i));

        for(JButton button: diceButtons)
            diceButtonPanel.add(button);

        rollPanel.add(rollButton);

        tablePanel.add(table);

        diceButtonPanel.repaint();
        scoreButtonPanel.repaint();
        rollPanel.repaint();
        tablePanel.repaint();
        winnerPanel.repaint();
    }

    private void findTotals(){
        int upperSec = 0, lowerSec = 0;
        for(int player=1;player<=2;player++){
            upperSec = 0;
            lowerSec = 0;
            for(int i=1;i<=6;i++)
                upperSec += Integer.parseInt((String) table.getValueAt(i, player));
            table.setValueAt("" + upperSec, 7, player); 
            if(upperSec >= 63){
                table.setValueAt("35", 8, player);
                upperSec += 35;
            }
            else
                table.setValueAt("0", 8, player);
            for(int i=11;i<=17;i++)
                lowerSec += Integer.parseInt((String) table.getValueAt(i, player));

            table.setValueAt("" + lowerSec, 18, player);
            table.setValueAt("" + upperSec, 19, player);
            table.setValueAt("" + (lowerSec+upperSec), 20, player);
        }
        if(((String)table.getValueAt(20, 1)).compareTo((String)table.getValueAt(20, 2)) > 0)
            winner.setText("The Winner is Player 1!");
        else if(((String)table.getValueAt(20, 1)).compareTo((String)table.getValueAt(20, 2)) < 0)
            winner.setText("The Winner is Player 2!");
        else
            winner.setText("It is a tie!");
        winnerPanel.add(winner);
        update();
    }

    public boolean isSelectedButton(int index){
        for(ImageIcon aWhiteIcon: pics[0])
            if(diceButtons[index].getIcon() == aWhiteIcon || diceButtons[index].getIcon() == null)
                return false;
        return true;
    }

    public int getRoll(int index){
        return rolls[index];
    }
}
