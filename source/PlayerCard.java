/*
 * File: PlayerCard.java
 * Name: Giacalone/Kelly/Xue
 * Date: 05/21/2015
 * -------------------------
 * This class manages the buttons/moves avaliable for each player. It also
 * keeps track of the player name.
 */

import javax.swing.JComponent;
import javax.swing.JButton;
import javax.swing.JLabel;
public class PlayerCard
{
    protected JComponent[] scoreButtons;
    private int catsLeft;
    private String name;
    
    public PlayerCard(){
        catsLeft = 13;
        name = "Player";
        setUpButtons();
    }
    
    public PlayerCard(String name){
        catsLeft = 13;
        this.name = name;
        setUpButtons();
    }
    
    private void setUpButtons(){
        scoreButtons = new JComponent[13];
        
        scoreButtons[0] = new JButton("Aces");
        scoreButtons[1] = new JButton("Twos");
        scoreButtons[2] = new JButton("Threes");
        scoreButtons[3] = new JButton("Fours");
        scoreButtons[4] = new JButton("Fives");
        scoreButtons[5] = new JButton("Sixes");
        scoreButtons[6] = new JButton("3 of a Kind");
        scoreButtons[7] = new JButton("4 of a Kind");
        scoreButtons[8] = new JButton("Full House");
        scoreButtons[9] = new JButton("Sm. Straight");
        scoreButtons[10] = new JButton("Lg. Straight");
        scoreButtons[11] = new JButton("Yahtzee!");
        scoreButtons[12] = new JButton("Chance");
    }
    
    public int indexOfButton(JButton button){
        for(int i=0; i<scoreButtons.length;i++)
        if(button == scoreButtons[i])
        return i;
        return -1;
    }
    
    public void removeButton(int index){
        scoreButtons[index] = new JLabel();
        catsLeft--;
    }
    
    //@Pre: 0 <= index < scoreButtons.length
    public JComponent getComponentAtIndex(int index){
        return scoreButtons[index];
    }
    
    public boolean isDone(){
        if(catsLeft <= 0)
        return true;
        return false;
    }
    
    public String getName(){
        return name;
    }
    
    public void setName(String name){
        this.name = name;
    }
}
