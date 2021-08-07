package ir.sharif.math.bp99_1.snake_and_ladder.model;

import java.nio.charset.IllegalCharsetNameException;
import java.util.LinkedList;
import java.util.Random;

public class Dice {

    /**
     * add some fields to store :
     * 1) chance of each dice number ( primary chance of each number, should be 1 )
     * currently our dice has 1 to 6.
     * 2) generate a random number
     * <p>
     * initialize these fields in constructor.
     */

    private int[] Chance = new int[7];

    public Dice() {
        for(int i = 1;i <= 6;i++)Chance[i] = 1;
    }

    /**
     * create an algorithm generate a random number(between 1 to 6) according to the
     * chance of each dice number( you store them somewhere)
     * return the generated number
     */
    public int roll() {
        LinkedList<Integer> temp = new LinkedList<Integer>();
        for(int i = 1;i <= 6;i++){
            for (int j = 0;j < Chance[i];j++) {
                temp.add(i);
            }
        }
        Random rnd = new Random();
        return temp.get(rnd.nextInt(temp.size()));
    }

    /**
     * give a dice number and a chance, you should UPDATE chance
     * of that number.
     * pay attention chance of none of the numbers must not be negetive(it can be zero)
     */
    public void addChance(int number, int chance) {
        Chance[number] += chance;
        Chance[number] = Math.max(Chance[number],0 );
        Chance[number] = Math.min(Chance[number],8);
    }

    public int getChence(int x){
        return Chance[x];
    }

    public void setChance(int x, int y){
        Chance[x] = y;
    }
    /**
     * you should return the details of the dice number.
     * sth like:
     * "1 with #1 chance.
     * 2 with #2 chance.
     * 3 with #3 chance
     * .
     * .
     * . "
     * where #i is the chance of number i.
     */
    public String getDetails() {
        String ans = "";
        for(int i = 1;i <= 6;i++){
            ans += i;
            ans += ' ';
            ans += "with";
            ans += ' ';
            ans += '#';
            ans += Chance[i];
            ans += ' ';
            ans += "chance";
            ans += '.';
            ans += '\n';
        }
        return ans;
    }
}
