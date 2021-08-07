package ir.sharif.math.bp99_1.snake_and_ladder.model.pieces;

import ir.sharif.math.bp99_1.snake_and_ladder.model.Cell;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Color;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Player;
import ir.sharif.math.bp99_1.snake_and_ladder.model.prizes.Prize;
import ir.sharif.math.bp99_1.snake_and_ladder.model.transmitters.Transmitter;

public abstract class Piece {
    private Cell currentCell;
    private final Color color;
    private final Player player;
    private boolean isSelected;
    protected boolean isAlive;
    protected boolean ability;
    protected String type;
    protected Prize prize;

    public Piece(Player player, Color color) {
        this.color = color;
        this.player = player;
        isSelected = false;
        isAlive = true;
        ability = false;
    }

    public Prize getPrize() {return prize;}

    public void setPrize(Prize prize) {
        this.prize = prize;
    }

    public Player getPlayer() {
        return player;
    }

    public Color getColor() {
        return color;
    }

    public Cell getCurrentCell() {
        return currentCell;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public void setCurrentCell(Cell currentCell) {
        this.currentCell = currentCell;
    }

    public boolean getIsAlive() {
        return isAlive;
    }

    public void setIsAlive(boolean state) {
        this.isAlive = state;
    }

    public boolean getAbility() {
        return ability;
    }

    public void setAbility(boolean state) {
        this.ability = state;
    }

    public String getType(){return type;}
    /**
     * @return "true" if your movement is valid  , else return " false"
     * <p>
     * In this method, you should check if the movement is valid of not.
     * <p>
     * You can use some methods ( they are recommended )
     * <p>
     * 1) "canEnter" method in class "Cell"
     * <p>
     * if your movement is valid, return "true" , else return " false"
     */
    abstract public boolean castAbility(Cell destination);

    abstract public boolean checkAbility(Cell destination);

    public boolean isValidMove(Cell destination, int diceNumber) {
        if (currentCell.getBoard().getCell(destination.getX(), destination.getY()) == null) return false;
        if (destination.getX() != currentCell.getX() && destination.getY() != currentCell.getY()) return false;
        if ((int) currentCell.getBoard().distance(currentCell, destination) != diceNumber) return false;
        if (currentCell.getBoard().checkWall(currentCell, destination)) return false;
        return true;
    }

    /**
     * @param destination move selected piece from "currentCell" to "destination"
     */
    public void moveTo(Cell destination) {
        this.currentCell.setPiece(null);
        currentCell = destination;
        destination.setPiece(this);
        if (this.color == destination.getColor()) this.player.applyOnScore(4);
        if (destination.getPrize() != null){
            this.player.usePrize(destination.getPrize());
        }
        if (destination.getTransmitter() != null) {
            destination.getTransmitter().transmit(this);
        }
    }
}
