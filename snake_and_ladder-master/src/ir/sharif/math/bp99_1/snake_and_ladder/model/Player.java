package ir.sharif.math.bp99_1.snake_and_ladder.model;

import ir.sharif.math.bp99_1.snake_and_ladder.model.pieces.*;
import ir.sharif.math.bp99_1.snake_and_ladder.model.prizes.Prize;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Player {
    private final String name;
    private int score;
    private final List<Piece> pieces;
    private final Dice dice;
    private Player rival;
    private final int id;
    private int playerNumber;
    private boolean isReady;
    private boolean dicePlayedThisTurn;
    private int moveLeft;
    private int lastMoveLeft;
    private Piece selectedPiece;

    public Player(String name, int score, int id, int playerNumber) {
        this.name = name;
        this.score = score;
        this.id = id;
        this.playerNumber = playerNumber;
        this.dice = new Dice();
        this.pieces = new ArrayList<>();
        this.pieces.add(new Sniper(this,Color.RED));
        this.pieces.add(new Bomber(this,Color.BLUE));
        this.pieces.add(new Thief(this,Color.GREEN));
        this.pieces.add(new Healer(this,Color.YELLOW));
        this.moveLeft = 0;
        lastMoveLeft = -1;
        this.selectedPiece = null;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Dice getDice() {
        return dice;
    }

    public int getScore() {
        return score;
    }

    public List<Piece> getPieces() {
        return pieces;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    public Player getRival() {
        return rival;
    }

    public int getMoveLeft() {
        return moveLeft;
    }

    public int getLastMoveLeft(){return lastMoveLeft;}

    public void setLastMoveLeft(int x) { lastMoveLeft = x;}

    public Piece getSelectedPiece() {
        return selectedPiece;
    }

    public boolean isDicePlayedThisTurn() {
        return dicePlayedThisTurn;
    }

    public void setDicePlayedThisTurn(boolean dicePlayedThisTurn) {
        this.dicePlayedThisTurn = dicePlayedThisTurn;
    }

    public void setSelectedPiece(Piece selectedPiece) {
        this.selectedPiece = selectedPiece;
    }

    public void setMoveLeft(int moveLeft) {
        this.moveLeft = moveLeft;
    }

    public void setRival(Player rival) {
        this.rival = rival;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void applyOnScore(int score) {
        this.score += score;
    }

    public boolean isReady() {
        return isReady;
    }

    public void setReady(boolean ready) {
        isReady = ready;
    }

    /**
     * @param prize according to input prize , apply necessary changes to score and dice chance
     *              <p>
     *              you can use method "addChance" in class "Dice"(not necessary, but recommended)
     */
    public void usePrize(Prize prize) {
        score += prize.getPoint();
        this.getDice().addChance(prize.getDiceNumber(),prize.getChance());
    }


    /**
     * check if any of player pieces can move to another cell.
     *
     * @return true if at least 1 piece has a move , else return false
     * <p>
     * you can use method "isValidMove" in class "Piece"(not necessary, but recommended)
     */
    public boolean hasMove(Board board, int diceNumber) {
        for(Piece temp : pieces){
            if(!temp.getIsAlive())continue;
            for(Cell cell : board.getCells()){
                if((int)(board.distance(cell,temp.getCurrentCell())) == diceNumber &&
                        (cell.getX() == temp.getCurrentCell().getX() || cell.getY() == temp.getCurrentCell().getY())) {
                    if(cell.canEnter(temp) && temp.isValidMove(cell,diceNumber))return true;
                    if(temp.checkAbility(cell))return true;
                }
            }
        }
        return false;
    }


    /**
     * Deselect selectedPiece and make some changes in this class fields.
     */
    // **
    public void endTurn() {
        if(selectedPiece != null)selectedPiece.setSelected(false);
        selectedPiece = null;
        moveLeft = 0;
        dicePlayedThisTurn = false;
    }


    /**
     * DO NOT CHANGE FOLLOWING METHODS.
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(name, player.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

