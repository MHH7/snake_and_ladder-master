package ir.sharif.math.bp99_1.snake_and_ladder.model.pieces;

import ir.sharif.math.bp99_1.snake_and_ladder.model.Cell;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Color;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Player;
import ir.sharif.math.bp99_1.snake_and_ladder.model.prizes.Prize;

public class Thief extends Piece{
    public Prize getPrize() {
        return prize;
    }

    public Thief(Player player, Color color){
        super(player,color);
        type = "Thief";
        ability = true;
        prize = null;
    }

    @Override
    public boolean checkAbility(Cell destination) {
        if(getCurrentCell() == destination){
            if(getCurrentCell().getPrize() != null && prize == null){
                return true;
            }
            else if(prize != null){
                return true;
            }
            return false;
        }
        else{
            if(getCurrentCell().getBoard().distance(getCurrentCell(),destination) == getPlayer().getMoveLeft() && destination.getPiece() == null){
                return true;
            }
            return false;
        }
    }

    @Override
    public boolean castAbility(Cell destination) {
        if(getCurrentCell() == destination){
            if(getCurrentCell().getPrize() != null && prize == null){
                prize = getCurrentCell().getPrize();
                getCurrentCell().setPrize(null);
                prize.setCell(null);
                return true;
            }
            else if(prize != null){
                if(getCurrentCell().getPrize() != null){
                    getCurrentCell().getPrize().setCell(null);
                }
                getCurrentCell().setPrize(prize);
                prize.setCell(getCurrentCell());
                prize = null;
                return true;
            }
            return false;
        }
        else{
            if(getCurrentCell().getBoard().distance(getCurrentCell(),destination) == getPlayer().getMoveLeft() && destination.getPiece() == null){
                getCurrentCell().setPiece(null);
                setCurrentCell(destination);
                destination.setPiece(this);
                if (getColor() == destination.getColor()) getPlayer().applyOnScore(4);
                if (destination.getPrize() != null && prize == null){
                    getPlayer().usePrize(destination.getPrize());
                }
                if (destination.getTransmitter() != null) {
                    if(prize != null)
                    prize.setCell(null);
                    prize = null;
                    destination.getTransmitter().transmit(this);
                }
                return true;
            }
            return false;
        }
    }
}
