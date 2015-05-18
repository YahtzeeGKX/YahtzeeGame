/*
 * File: Table.java
 * Name: Giacalone/Kelly/Xue
 * Date: 05/19/2015
 * -------------------------
 * The scorecard's table for the Yahtzee table.
 */

import javax.swing.*;
import java.awt.*;
public class Table
{
    public static void main(String[] args) {
        JFrame frame = new JFrame("Scoreboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 1));
        
        JTable table = new JTable(24, 3);
        table.setRowHeight(24);
        table.setFont(new Font("Arial", Font.PLAIN, 14));               
        table.setShowGrid(true);
        table.setGridColor(Color.black);
        table.setEnabled(false);
        
        table.setShowGrid(true);
        table.setGridColor(Color.black);
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
        table.setSize(400, 260);
        
        table.setValueAt("Lower Section", 10, 0);
        table.setValueAt("3 of a Kind", 11, 0);
        table.setValueAt("4 of a Kind", 12, 0);
        table.setValueAt("Full House", 13, 0);
        table.setValueAt("Sm. Straight", 14, 0);
        table.setValueAt("Lg. Straight", 15, 0);
        table.setValueAt("YAHTZEE", 16, 0);
        table.setValueAt("Fours", 17, 0);
        table.setValueAt("Fives", 18, 0);
        table.setValueAt("Sixes", 19, 0);
        table.setValueAt("Chance", 20, 0);
        table.setValueAt("Total (Lwr. Sec.)", 21, 0);
        table.setValueAt("Total (Upr. Sec.)", 22, 0);
        table.setValueAt("Grand Total", 23, 0);
        
        table.repaint();
        panel.add(table);
        panel.repaint();
        
        frame.setContentPane(panel);
        frame.setSize(400, 598);
        frame.repaint();
        frame.setVisible(true);
    }
    
    private static int total(JTable table, int col) {
        int upperSection = 0;
        int lowerSection = 0;
        
        for(int i = 1; i < 7; i++)
            upperSection += Integer.parseInt((String) table.getValueAt(i, col));
        table.setValueAt(upperSection, 7, col);
        if(upperSection >= 63)
            table.setValueAt(35, 8, col);
        
        for(int i = 11; i < 21; i++)
            lowerSection += Integer.parseInt((String) table.getValueAt(i, col));
        table.setValueAt(lowerSection, 21, col);
        table.setValueAt(upperSection + Integer.parseInt((String) table.getValueAt(8, col)), 22, col);
        int grandTotal = Integer.parseInt((String) table.getValueAt(21, col)) 
            + Integer.parseInt((String) table.getValueAt(22, col));
        table.setValueAt(grandTotal, 23, col);
        return grandTotal;
    }
}
