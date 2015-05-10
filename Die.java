/*
 * File: Die.java
 * Name: Giacalone/Kelly/Xue
 * Date: 05/08/2015
 * -------------------------
 * This class represents a die (singular of dice).
 */

public class Die {
    
    private int numSides;
    private int faceValue;
    
    public Die() {
        numSides = 6;
        faceValue = roll();
    }
    
    public Die(int numSides) {
        this.numSides = numSides;
        faceValue = roll();
    }
    
    private int roll() {
        return (int) (numSides * Math.random() + 1);
    }
    
    public int getNumSides() {
        return numSides;
    }
    
}
