
/**
 * Write a description of class Table here.
 * 
 * @author (your name) 
 * @version (a
 version number or a date)
 */
import javax.swing.*;
import java.awt.*;
public class Table
{
    public static void main(String[] args) {
        JFrame frame = new JFrame("Scoreboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setLayout(new GridLayout(2, 1));
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 1));
        
        JTable table = new JTable(24, 3);
        table.setRowHeight(24);
        //table.setSize(400, 260);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        
        /*JTable lowerSection = new JTable(13, 3);
        lowerSection.setRowHeight(24);
        lowerSection.setSize(400, 260);
        lowerSection.setFont(new Font("Arial", Font.PLAIN, 14));*/
        
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
        //table.setValueAt("Total", 9, 0);
        table.setSize(400, 260);
                
        table.setShowGrid(true);
        table.setGridColor(Color.black);
        table.setEnabled(false);
        
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
        //panel.add(lowerSection);
        panel.repaint();
        
        frame.setContentPane(panel);
        //frame.getContentPane().add(table);
        //frame.getContentPane().add(lowerSection);
        frame.setSize(400, 598);
        frame.repaint();
        frame.setVisible(true);
    }
}
