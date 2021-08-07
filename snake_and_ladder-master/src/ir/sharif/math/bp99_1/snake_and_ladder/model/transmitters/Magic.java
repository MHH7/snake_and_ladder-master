package ir.sharif.math.bp99_1.snake_and_ladder.model.transmitters;

import ir.sharif.math.bp99_1.snake_and_ladder.model.Cell;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Color;
import ir.sharif.math.bp99_1.snake_and_ladder.model.pieces.Piece;

public class Magic extends Transmitter{
    public Magic(Cell firstCell,Cell lastCell){
        super(firstCell,lastCell);
        color = Color.BLUE;
    }

    @Override
    public void transmit(Piece piece) {
        super.transmit(piece);
        piece.getPlayer().applyOnScore(6);
        piece.setAbility(true);
    }
}
