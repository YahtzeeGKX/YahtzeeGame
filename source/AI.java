
/**
 * Write a description of class AI here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class AI extends PlayerCard{
    private int button;
    private int turn;
    private YahtzeeGUI gui;
    
    public AI(YahtzeeGUI gui) {
        super("Hairy Harrie");
        this.gui = gui;
        button = 0;
        turn = 0;
    }
    
    public void turn() {
        for(int roll = 0; roll < 3; roll++) {
            for(int b = 0; b < 5; b++) {
                if(!gui.isSelectedButton(b) && gui.getRoll(b) == turn + 1)
                    gui.determineButton(b, true);
            }
            gui.rollUnselected();
        }
        gui.determineButton(turn, false);
        turn++;
    }
}
