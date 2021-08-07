package ir.sharif.math.bp99_1.snake_and_ladder.model;

import ir.sharif.math.bp99_1.snake_and_ladder.model.pieces.Piece;
import ir.sharif.math.bp99_1.snake_and_ladder.model.prizes.Prize;
import ir.sharif.math.bp99_1.snake_and_ladder.model.transmitters.Transmitter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Cell {
    private Color color;
    private final int x, y;
    private Transmitter transmitter;
    private Prize prize;
    private Piece piece;
    private final List<Cell> adjacentOpenCells;
    private final List<Cell> adjacentCells;
    private Board board;

    public Cell(Color color, int x, int y) {
        this.color = color;
        this.x = x;
        this.y = y;
        this.transmitter = null;
        this.prize = null;
        this.piece = null;
        this.adjacentOpenCells = new ArrayList<>();
        this.adjacentCells = new ArrayList<>();
        this.board = null;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color c) {color = c;}

    public List<Cell> getAdjacentCells() {
        return adjacentCells;
    }

    public List<Cell> getAdjacentOpenCells() {
        return adjacentOpenCells;
    }

    public Piece getPiece() {
        return piece;
    }

    public Prize getPrize() {
        return prize;
    }

    public Transmitter getTransmitter() {
        return transmitter;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public void setPrize(Prize prize) {
        this.prize = prize;
    }

    public void setTransmitter(Transmitter transmitter) {
        this.transmitter = transmitter;
    }

    public Board getBoard(){return board;}

    public void setBoard(Board board){this.board = board;}
    /**
     * @return true if piece can enter this cell, else return false
     */
    public boolean canEnter(Piece piece) {
        if(color == Color.BLACK)return false;
        boolean ans = true;
        if(piece.getColor() != color && color != Color.WHITE)ans = false;
        if(this.getPiece() != null)ans = false;
        return ans;
    }


    /**
     * DO NOT CHANGE FOLLOWING METHODS.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return x == cell.x && y == cell.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
