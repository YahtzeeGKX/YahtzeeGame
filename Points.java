/*
 * File: Points.java
 * Name: Giacalone/Kelly/Xue
 * Date: 05/10/2015
 * -------------------------
 * This takes an array od dice values and the clicked button, and returns the
 * correct number of points.
 * NOTE: Author of the selectionSort method: Lewis/Loftus/Cocking
 */

import javax.swing.JButton;

public class Points
{
    public static int getPoints(JButton button, int[] arr) {
        int[] sort = arr;
        sort = selectionSort(sort);
            
        int total = 0;

        if(button.getText().equals("Ones")) { // total = sum of the ones
            for(int i: sort)
                if(i == 1) total += 1;
        }
        else if(button.getText().equals("Twos")) { // total = sum of the twos
            for(int i: sort)
                if(i == 2) total += 2;
        }
        else if(button.getText().equals("Threes")) { // total = sum of the threes
            for(int i: sort)
                if(i == 3) total += 3;
        }
        else if(button.getText().equals("Fours")) { // total = sum of the fours
            for(int i: sort)
                if(i == 4) total += 4;
        }
        else if(button.getText().equals("Fives")) { // total = sum of the fives
            for(int i: sort)
                if(i == 5) total += 5;
        }
        else if(button.getText().equals("Sixes")) { // total = sum of the sixes
            for(int i: sort)
                if(i == 6) total += 6;
        }
        else if(button.getText().equals("Three of a Kind")) {
            if((sort[0] == sort[1] && sort[1] == sort[2])
                || (sort[1] == sort[2] && sort[2] == sort[3])
                || (sort[2] == sort[3] && sort[3] == sort[4])) {
                for(int i: sort)
                    total += i;
            }
        }
        else if(button.getText().equals("Four of a Kind")) {
            if((sort[0] == sort[1] && sort[1] == sort[2] && sort[2] == sort[3])
                || (sort[1] == sort[2] && sort[2] == sort[3] && sort[3] == sort[4])) {
                for(int i: sort)
                    total += i;
            }
        }
        else if(button.getText().equals("Full House")) {
            if(sort[0] == sort[1] && sort[2] == sort[3] && (sort[3] == sort[4]))
                total = 25;
            else if(sort[0] == sort[1] && sort[1] == sort[2] && sort[3] == sort[4])
                total = 25;
        }
        else if(button.getText().equals("Small Straight")) {
            total = 30;
            for(int i = 1; i < sort.length - 1; i++)
                if(sort[i] != sort[i - 1] + 1)
                    total = 0;
            if(total == 0) {
                total = 30;
                for(int i = 2; i < sort.length; i++)
                if(sort[i] != sort[i - 1] + 1)
                    total = 0;
            }
            
        }
        else if(button.getText().equals("Large Straight")) {
            total = 40;
            for(int i = 1; i < sort.length; i++)
                if(sort[i] != sort[i - 1] + 1)
                    total = 0;
        }
        else if(button.getText().equals("Chance")) {
            for(int i: sort)
                total += i;
        }
        else if(button.getText().equals("Yahtzee!")) {
            if(sort[0] == sort[1] && sort[1] == sort[2] && sort[2] == sort[3] && sort[3] == sort[4])
                return 50;
        }

        return total;
    }

    //-----------------------------------------------------------------
    //  Author of this method: Lewis/Loftus/Cocking
    //  Sorts the specified array of integers using the selection
    //  sort algorithm.
    //-----------------------------------------------------------------
    private static int[] selectionSort (int[] numbers)
    {
        int min, temp;

        for (int index = 0; index < numbers.length-1; index++)
        {
            min = index;
            for (int scan = index+1; scan < numbers.length; scan++)
                if (numbers[scan] < numbers[min])
                    min = scan;

            // Swap the values
            temp = numbers[min];
            numbers[min] = numbers[index];
            numbers[index] = temp;
        }
        
        return numbers;
    }
}
