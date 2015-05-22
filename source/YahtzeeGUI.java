/*
 * File: YahtzeeGUI.java
 * Authors: Giacalone/Kelly/Xue
 * Date: 05/22/2015
 * ----------------------------
 * This class is the main class of the Yahtzee game. When a new objected is created
 * (in the driver class), the game begins, and a GUI appears which is the
 * implementation of the Yahtzee game.
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class YahtzeeGUI extends JFrame implements ActionListener {
    //Declares the Panels and Frame
    private JPanel scoreButtonPanel;
    private JPanel diceButtonPanel;
    private JPanel tablePanel;
    private JPanel rollPanel;
    private JPanel winnerPanel;
    private JMenuBar menuBar;
    private JFrame frame;
    
    //A specific button for rolling the dice
    private JButton rollButton;

    // The JLabel displaying the winner message
    private JLabel winner;

    //The JMenu displayed at the top of the GUI
    private JMenu options, restartGame;
    private JMenuItem soloPlay, multiPlay, setUpPlayers;

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
    
    //A 2D array of Images that represent each side of the dice. The first row is the white, 
    //unselected dice, and the second row are a copy that have a yellow background.
    //(JButton.setBackground() cannot be used because Mac Java treats transparent color as white.
    private ImageIcon[][] pics = {
            {createImageIcon("Pictures/1.png"), createImageIcon("Pictures/2.png"), createImageIcon("Pictures/3.png"), 
                createImageIcon("Pictures/4.png"), createImageIcon("Pictures/5.png"), createImageIcon("Pictures/6.png")}, 
            {createImageIcon("Pictures/1Y.png"), createImageIcon("Pictures/2Y.png"), createImageIcon("Pictures/3Y.png"), 
                createImageIcon("Pictures/4Y.png"), createImageIcon("Pictures/5Y.png"), createImageIcon("Pictures/6Y.png")}};
    
    //The AI Player
    private AI ai;
    
    //Boolean to keep track of whether the game is one or two players
    private boolean isOnePlayer;

    //Resets the game without creating a new frame. All information is put back to their default values.
    private void newGame() {
        // creates a new frame for the scoreboard
        if(frame != null) frame.dispose();
        frame = new JFrame("Yahtzee!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        
        

        //AI is created
        ai = new AI(this);
        //Creates the panel for the scoring buttons
        scoreButtonPanel = new JPanel();
        scoreButtonPanel.setLayout(new GridLayout(13, 1));
        //Creates Panel for the dice
        diceButtonPanel = new JPanel();
        diceButtonPanel.setLayout(new GridLayout(1,5));
        //Creates panel for the "roll" button
        rollPanel = new JPanel();
        //Creates panel for the scorecard table
        tablePanel = new JPanel();
        //Creates a panel that is swapped with rollPanel when somebody wins the game
        winnerPanel = new JPanel();
        //Creates the menu for restarting the game and for the opening the options
        menuBar = new JMenuBar();
        options = new JMenu("Options");
        restartGame = new JMenu("New Game");
        soloPlay = new JMenuItem("1 Player Game");
        multiPlay = new JMenuItem("2 Player Game");
        soloPlay.addActionListener(this);
        multiPlay.addActionListener(this);
        restartGame.add(soloPlay);
        restartGame.add(multiPlay);
        setUpPlayers = new JMenuItem("Player Options");
        setUpPlayers.addActionListener(this);
        options.add(restartGame);
        options.add(setUpPlayers);
        menuBar.add(options);
        
        //The JLabel for the winning player
        winner = new JLabel();

        rolls = new int[5];
        //Creates 2 new playerCards to track each player's possible moves/Buttons availible to press
        //Uses player1's name from the last game
        player1 = new PlayerCard(player1.getName());

        //If the game is one player, the 2nd player is initalized to the AI
        if(isOnePlayer)
            player2 = ai;
        //If there are two players, use the name from the player options.
        else
            player2 = new PlayerCard(player2.getName());
        //Player 1 goes first
        currPlayer = player1;

        rollsLeft = 3;

        initTable();
        initButton();

        //Adds everything to the frame
        frame.add(diceButtonPanel, BorderLayout.NORTH);
        frame.add(scoreButtonPanel, BorderLayout.WEST);
        frame.add(rollPanel, BorderLayout.CENTER);
        frame.add(tablePanel, BorderLayout.EAST);
        frame.add(winnerPanel, BorderLayout.SOUTH);
        frame.setJMenuBar(menuBar);
        frame.pack();
        frame.setVisible(true);
    }
    //Creates the frame, then starts the game. This is called on the first creation of the game.
    public YahtzeeGUI() {
        isOnePlayer = true;
        player1 = new PlayerCard("Player 1");
        player2 = new PlayerCard("Player 2");
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
        rollButton.setPreferredSize(new Dimension(300, 75));

        //Rolls for the first set of die rolls
        rollUnselected();
    }
    
    //Creates and sets up the table for keeping track of each player, along with how many points they earned in each category.
    private void initTable() {
        // sets size, font, and grid of the JTable
        table = new JTable(21, 3);
        table.setRowHeight(24);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.getColumn("A").setMinWidth(125);
        table.getColumn("A").setMaxWidth(125);
        table.getColumn("B").setMinWidth(115);
        table.getColumn("B").setMaxWidth(115);
        table.getColumn("C").setMinWidth(115);
        table.getColumn("C").setMaxWidth(115);
        table.setShowGrid(true);
        table.setGridColor(Color.BLACK);
        table.setEnabled(false);

        // sets the preset values in the JTable
        table.setValueAt("Upper Section", 0, 0);
        table.setValueAt(player1.getName(), 0, 1);
        table.setValueAt(player2.getName(), 0, 2);
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
    
    // The games response when an action is performed
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == soloPlay){
            frame.remove(tablePanel);
            frame.remove(diceButtonPanel);
            frame.remove(scoreButtonPanel);
            frame.remove(rollPanel);
            frame.remove(winnerPanel);
            isOnePlayer = true;
            openPlayerOptions();
            newGame();
        }
        else if(e.getSource() == multiPlay){
            frame.remove(tablePanel);
            frame.remove(diceButtonPanel);
            frame.remove(scoreButtonPanel);
            frame.remove(rollPanel);
            frame.remove(winnerPanel);
            isOnePlayer = false;
            openPlayerOptions();
            newGame();
        }
        else if(e.getSource() == setUpPlayers)
            openPlayerOptions();
        else{
            JButton button = (JButton) e.getSource();
            determineButton(button);
        }
    }

    // determines which type of button is clicked and directs the method call appropriately
    public void determineButton(JButton button){
        if(button.getIcon() != null)
            changeDieBackground(button); 
        else if(button.getText().equals(rollButton.getText()))
            rollUnselected();
        else
            scoreButtonClicked(button);
    }

    // determines which type of button is clicked and directs the method call appropriately
    public void determineButton(int index, boolean isDie){
        if(isDie)
            changeDieBackground(diceButtons[index]);
        else
            scoreButtonClicked((JButton)currPlayer.getComponentAtIndex(index));
    }

    // The code executed when a acore button is clicked
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

        if(currPlayer == ai)
            ai.turn();
    }

    // changes the background of a die button when clicked from white to yellow and vise versa
    private void changeDieBackground(JButton button) {
        boolean found = false;
        for(int color = 0; color < 2 && !found; color++)
            for(int die = 0; die < pics[0].length && !found; die++)
                if(button.getIcon() == pics[color][die]){
                    button.setIcon(pics[Math.abs(color-1)][die]);
                    found = true;}

        update();
    }

    // rolls all the unselected dice
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
            update();
        }
    }

    // updates and repaints all parts of the GUI
    public void update(){
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

    // finds the totals for each player, and sets the winner message appropriately
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
        
        if(Integer.parseInt((String)table.getValueAt(20, 1)) > Integer.parseInt((String)table.getValueAt(20, 2)))
            winner.setText(player1.getName() + " wins with a score of " + (String)table.getValueAt(20,1) + "!");
        else if(Integer.parseInt((String)table.getValueAt(20, 1)) < Integer.parseInt((String)table.getValueAt(20, 2)))
            winner.setText(player2.getName() + " wins with a score of " + (String)table.getValueAt(20,2) + "!");
        else
            winner.setText("It's a tie between " + player1.getName() + " and " + player2.getName() + "!");
            
        winner.setFont(new Font("Arial", Font.PLAIN, 32));
        winnerPanel.add(winner);
        winnerPanel.setBackground(Color.GREEN);
        frame.remove(rollPanel);
        frame.remove(scoreButtonPanel);
        frame.add(winnerPanel, BorderLayout.CENTER);
        update();
    }

    // returns wheter a die is selected or not
    public boolean isSelectedButton(int index){
        for(ImageIcon aWhiteIcon: pics[0])
            if(diceButtons[index].getIcon() == aWhiteIcon || diceButtons[index].getIcon() == null)
                return false;
        return true;
    }

    // returns the roll of a certain die
    public int getRoll(int index){
        return rolls[index];
    }

    // opens and allows to be edited the player options (the name)
    private void openPlayerOptions(){
        final JDialog playerOptions = new JDialog(frame, "Player Options", true);
        playerOptions.setResizable(false);
        playerOptions.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        JPanel textFields = new JPanel();
        final JTextField player1Name = new JTextField(player1.getName());
        final JTextField player2Name = new JTextField(player2.getName());
        player1Name.setPreferredSize(new Dimension(150, 30));
        player2Name.setPreferredSize(new Dimension(150, 30));
        JButton accept = new JButton("Accept");
        JButton cancel = new JButton("Cancel");
        accept.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e){
                    player1.setName(player1Name.getText());
                    player2.setName(player2Name.getText());
                    table.setValueAt(player1.getName(), 0, 1);
                    table.setValueAt(player2.getName(), 0, 2);
                    playerOptions.dispose();
                }
            });
        cancel.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e){
                    playerOptions.dispose();
                }
            });
        textFields.add(player1Name);
        if(!isOnePlayer)
            textFields.add(player2Name);
        textFields.add(accept);
        textFields.add(cancel);
        playerOptions.add(textFields);
        playerOptions.pack();
        playerOptions.setLocationRelativeTo(frame);
        playerOptions.setVisible(true);
    }
    
    // Solves the problem where java does not load resources properly
    // when being bundled into a JAR file. 
    protected ImageIcon createImageIcon (String path) {
        java.net.URL source = getClass().getResource(path);
        if (source != null) return new ImageIcon (source);
        else return null;
    }
}
