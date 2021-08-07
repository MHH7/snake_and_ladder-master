package ir.sharif.math.bp99_1.snake_and_ladder.model.prizes;

import ir.sharif.math.bp99_1.snake_and_ladder.model.Cell;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Player;
import ir.sharif.math.bp99_1.snake_and_ladder.model.pieces.Piece;

public class Prize {
    private Cell cell;
    private final int point;
    private final int chance, diceNumber;
    private int index;


    public Prize(Cell cell, int point, int chance, int diceNumber) {
        this.cell = cell;
        this.point = point;
        this.chance = chance;
        this.diceNumber = diceNumber;
    }

    public void setIndex(int x) { index = x;}

    public int getIndex() {
        return index;
    }

    public int getPoint() {
        return point;
    }

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell c) {cell = c;}

    public int getChance() {
        return chance;
    }

    public int getDiceNumber() {
        return diceNumber;
    }


    /**
     * apply the prize.
     * you can use method "usePrize" in class "Player" (not necessary, but recommended)
     */
    public void using(Piece piece) {
        Player pl = piece.getPlayer();
        pl.usePrize(this);
    }

}
