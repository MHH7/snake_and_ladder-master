package ir.sharif.math.bp99_1.snake_and_ladder.model.transmitters;

import ir.sharif.math.bp99_1.snake_and_ladder.model.Cell;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Color;
import ir.sharif.math.bp99_1.snake_and_ladder.model.pieces.Piece;

public class Transmitter {
    protected Cell firstCell, lastCell;
    protected Color color;

    public Transmitter(Cell firstCell, Cell lastCell) {
        this.firstCell = firstCell;
        this.lastCell = lastCell;
        color = Color.YELLOW;
    }

    public Color getColor(){
        return color;
    }

    public Cell getFirstCell() {
        return firstCell;
    }

    public Cell getLastCell() {
        return lastCell;
    }

    /**
     * transmit piece to lastCell
     */
    public void transmit(Piece piece) {
        piece.getPlayer().applyOnScore(-3);
        if(lastCell.canEnter(piece))piece.moveTo(lastCell);
    }

}
