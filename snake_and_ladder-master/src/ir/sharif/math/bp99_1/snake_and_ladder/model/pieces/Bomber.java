package ir.sharif.math.bp99_1.snake_and_ladder.model.pieces;

import ir.sharif.math.bp99_1.snake_and_ladder.model.Color;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Player;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Cell;

public class Bomber extends Piece{
    public Bomber(Player player ,Color color){
        super(player,color);
        type = "Bomber";
    }

    @Override
    public boolean checkAbility(Cell destination) {
        if(ability == true){
            if(destination != getCurrentCell())return false;
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public boolean castAbility(Cell destination){
        if(ability == true){
            if(destination != getCurrentCell())return false;
            getCurrentCell().setColor(Color.BLACK);
            isAlive = false;
            if(getCurrentCell().getPrize() != null){
                getCurrentCell().getPrize().setCell(null);
                getCurrentCell().setPrize(null);
            }
            for(Cell c : getCurrentCell().getBoard().getCells()){
                if(Math.max(Math.abs(c.getX() - getCurrentCell().getX()),Math.abs(c.getY() - getCurrentCell().getY())) > 1)continue;
                if(c.getPrize() != null){
                    c.getPrize().setCell(null);
                    c.setPrize(null);
                }
                if(c.getPiece() != null){
                    if(c.getPiece().getType() != "Healer")c.getPiece().setIsAlive(false);
                }
            }
            ability = false;
            return true;
        }
        else{
            return false;
        }
    }
}
