package ir.sharif.math.bp99_1.snake_and_ladder.model.transmitters;

import ir.sharif.math.bp99_1.snake_and_ladder.model.Cell;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Color;
import ir.sharif.math.bp99_1.snake_and_ladder.model.pieces.Piece;

public class Killer extends Transmitter{
    public Killer(Cell firstCell, Cell lastCell){
        super(firstCell,lastCell);
        color = Color.RED;
    }

    @Override
    public void transmit(Piece piece){
        super.transmit(piece);
        piece.setIsAlive(false);
    }
}
