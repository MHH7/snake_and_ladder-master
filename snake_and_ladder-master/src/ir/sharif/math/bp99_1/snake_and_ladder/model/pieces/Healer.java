package ir.sharif.math.bp99_1.snake_and_ladder.model.pieces;

import ir.sharif.math.bp99_1.snake_and_ladder.model.Cell;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Color;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Player;

public class Healer extends Piece{
    public Healer(Player player, Color color){
        super(player,color);
        type = "Healer";
    }

    @Override
    public boolean checkAbility(Cell destination){
        if(ability == false)return false;
        if(destination.getPiece() == null)return false;
        if(getCurrentCell().getBoard().distance(getCurrentCell(),destination) > getPlayer().getMoveLeft())return false;
        return true;
    }

    @Override
    public boolean castAbility(Cell destination) {
        if(ability == true){
            if(getCurrentCell().getBoard().distance(getCurrentCell(),destination) <= getPlayer().getMoveLeft() && !destination.getPiece().getIsAlive()) {
                destination.getPiece().setIsAlive(true);
                ability = false;
                return true;
            }
            return false;
        }
        return false;
    }
}
