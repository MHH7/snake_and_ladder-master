package ir.sharif.math.bp99_1.snake_and_ladder.model.pieces;

import ir.sharif.math.bp99_1.snake_and_ladder.model.Cell;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Color;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Player;

public class Sniper extends Piece{
    public Sniper(Player player, Color color){
        super(player,color);
        type = "Sniper";
    }

    @Override
    public boolean checkAbility(Cell destination) {
        if(destination.getPiece() == null)return false;
        if(ability == true){
            if(destination.getPiece().getIsAlive() && destination.getPiece().getType() != "Healer"){
                return true;
            }
            return false;
        }
        else{
            return false;
        }
    }

    @Override
    public boolean castAbility(Cell destination){
        if(destination.getPiece() == null)return false;
        if(ability == true){
            if(destination.getPiece().getIsAlive() && destination.getPiece().getType() != "Healer"){
                destination.getPiece().setIsAlive(false);
                ability = false;
                return true;
            }
            return false;
        }
        else{
            return false;
        }
    }
}
