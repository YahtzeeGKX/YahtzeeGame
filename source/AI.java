/*
 * File: AI.java
 * Name: Giacalone/Kelly/Xue
 * Date: 05/21/2015
 * -------------------------
 * This is the AI for the yahtzee game. It is named Hairy Harrie and is pretty stupid.
 * It goes in order on the scorecard (e.g. on turn 1 it looks for Aces, on turn 2 it looks
 * for Twos, etc.).
 */

public class AI extends PlayerCard{
    private int button;
    private int turn;
    private int[] freq;
    private YahtzeeGUI gui;

    // Sets the instance variables, the name, and gets the YahtzeeGUI object used.
    public AI(YahtzeeGUI gui) {
        super("Computer");
        this.gui = gui;
        button = 0;
        turn = 0;
        freq = null;
    }

    // Executes a different command for choosing which dice to save depending on the turn
    public void turn() {
        for(int roll = 0; roll < 3; roll++) {
            findFreq();
            int lookFor = findMost();
            int lookFor2 = findSecondMost();
            findFreq();
            for(int b = 0; b < 5; b++) {
                // Aces through Sixes
                if(turn < 6 && !gui.isSelectedButton(b) && gui.getRoll(b) == turn + 1)
                    gui.determineButton(b, true);

                // Three of a Kind and Four of a Kind
                else if((turn == 6 || turn == 7) && !gui.isSelectedButton(b) && gui.getRoll(b) == findMost())
                    gui.determineButton(b, true);

                // Full House
                else if(turn == 8) {
                    if(!gui.isSelectedButton(b) && gui.getRoll(b) == lookFor && freq[lookFor - 1] <= 3)
                        gui.determineButton(b, true);
                    else if(!gui.isSelectedButton(b) && gui.getRoll(b) == lookFor2 && freq[lookFor2 - 1] <= 3)
                        gui.determineButton(b, true);
                }

                // Small Straight
                else if(turn == 9) {
                    if(!gui.isSelectedButton(b) && gui.getRoll(b) == 3){
                        if(freq[2] == 1)
                        gui.determineButton(b, true);
                        else
                        freq[2]--;
                    }//determines whether to keep a three
                    else if(!gui.isSelectedButton(b) && gui.getRoll(b) == 4){
                        if(freq[3] == 1)
                        gui.determineButton(b, true);
                        else
                        freq[3]--;
                    }//fours
                    else if((!gui.isSelectedButton(b) && gui.getRoll(b) == 2) && (freq[0] >= 1 || !(freq[4] >= 1 && freq[5] >= 1))){
                        if(freq[1] == 1)
                        gui.determineButton(b, true);
                        else
                        freq[1]--;
                    }//twos
                    else if((!gui.isSelectedButton(b) && gui.getRoll(b) == 5) && (freq[5] >= 1 || !(freq[1] >= 1 && freq[0] >= 1))){
                        if(freq[4] == 1)
                        gui.determineButton(b, true);
                        else
                        freq[4]--;
                    }//fives
                    else if(!gui.isSelectedButton(b) && gui.getRoll(b) == 1 && (freq[1] >= 2 || freq[5] < 1)){
                        if(freq[0] == 1)
                        gui.determineButton(b, true);
                        else
                        freq[0]--;
                    }//ones
                    else if(!gui.isSelectedButton(b) && gui.getRoll(b) == 6 && !(freq[0] >= 1 && freq[1] >= 1)){
                        if(freq[5] == 1)
                        gui.determineButton(b, true);
                        else
                        freq[5]--;
                    }//sixes
                }

                // Large Straight
                else if(turn == 10) {
                    if(!gui.isSelectedButton(b) && gui.getRoll(b) == 2){
                        if(freq[1] == 1)
                        gui.determineButton(b, true);
                        else
                        freq[1]--;
                    }//twos
                    else if(!gui.isSelectedButton(b) && gui.getRoll(b) == 3){
                        if(freq[2] == 1)
                        gui.determineButton(b, true);
                        else
                        freq[2]--;
                    }//threes
                    else if(!gui.isSelectedButton(b) && gui.getRoll(b) == 4){
                        if(freq[3] == 1)
                        gui.determineButton(b, true);
                        else
                        freq[3]--;
                    }//fours
                    else if(!gui.isSelectedButton(b) && gui.getRoll(b) == 5){
                        if(freq[4] == 1)
                        gui.determineButton(b, true);
                        else 
                        freq[4]--;
                    }//fives
                    else if(!gui.isSelectedButton(b) && gui.getRoll(b) == 1 && freq[5] < 1){
                        if(freq[0] == 1)
                        gui.determineButton(b, true);
                        else
                        freq[0]--;
                    }//ones
                    else if(!gui.isSelectedButton(b) && gui.getRoll(b) == 6){
                        if(freq[5] == 1)
                        gui.determineButton(b, true);
                        else
                        freq[5]--;
                    }//sixes
                }

                // Yahtzee
                else if(turn == 11 && !gui.isSelectedButton(b) && gui.getRoll(b) == findMost())
                    gui.determineButton(b, true);

                // Chance
                else if(turn == 12 && !gui.isSelectedButton(b) && (gui.getRoll(b) == 6 || gui.getRoll(b) == 5))
                    gui.determineButton(b, true);
            }
            gui.rollUnselected();
        }
        gui.determineButton(turn, false);
        turn++;
    }

    // Returns the value of the die that is most frequent. If there is a tie, it returns the value
    // that is highest to maximize the score for three and four of a kind.
    private int findMost() {
        int max = Math.max(Math.max(Math.max(freq[0], freq[1]), freq[2]), Math.max(Math.max(freq[3], freq[4]), freq[5]));
        if(freq[5] == max) return 6;
        else if(freq[4] == max) return 5;
        else if(freq[3] == max) return 4;
        else if(freq[2] == max) return 3;
        else if(freq[1] == max) return 2;
        else return 1;
    }

    // Does the same thing as findMost() except with the second most common value.
    private int findSecondMost() {  
        freq[findMost() - 1] = -1;

        int max = Math.max(Math.max(Math.max(freq[0], freq[1]), freq[2]), Math.max(Math.max(freq[3], freq[4]), freq[5]));
        if(freq[5] == max) return 6;
        else if(freq[4] == max) return 5;
        else if(freq[3] == max) return 4;
        else if(freq[2] == max) return 3;
        else if(freq[1] == max) return 2;
        else return 1;
    }

    // Updates freq with list of ints that correspond to the number of dice of that value on the board.
    private void findFreq() {
        freq = new int[6];

        for(int i = 0; i < 5; i++) {
            int num = gui.getRoll(i);
            if(num == 1) freq[0]++;
            else if(num == 2) freq[1]++;
            else if(num == 3) freq[2]++;
            else if(num == 4) freq[3]++;
            else if(num == 5) freq[4]++;
            else freq[5]++;
        }
    }
}
