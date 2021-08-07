package ir.sharif.math.bp99_1.snake_and_ladder.model.transmitters;

import ir.sharif.math.bp99_1.snake_and_ladder.model.Cell;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Color;
import ir.sharif.math.bp99_1.snake_and_ladder.model.pieces.Piece;

import java.util.Random;

public class Worm extends Transmitter{
    public Worm(Cell firstCell,Cell lastCell){
        super(firstCell,lastCell);
        color = Color.GREEN;
    }

    @Override
    public void transmit(Piece piece) {
        super.transmit(piece);
        Random rnd = new Random();
        int x = rnd.nextInt(7) + 1;
        int y = rnd.nextInt(16) + 1;
        Cell c = firstCell.getBoard().getCell(x,y);
        if(c.canEnter(piece))piece.moveTo(c);
    }
}
