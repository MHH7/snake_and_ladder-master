package ir.sharif.math.bp99_1.snake_and_ladder.model;

import ir.sharif.math.bp99_1.snake_and_ladder.model.pieces.Piece;
import ir.sharif.math.bp99_1.snake_and_ladder.model.prizes.Prize;
import ir.sharif.math.bp99_1.snake_and_ladder.model.transmitters.Transmitter;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Board {
    private final List<Cell> cells;
    private final List<Transmitter> transmitters;
    private final List<Wall> walls;
    private final List<Prize> prizes;
    private final Map<Cell, Integer> startingCells;
    private final List<Cell> player1Pieces;
    private final List<Cell> player2Pieces;

    public Board() {
        cells = new LinkedList<>();
        transmitters = new LinkedList<>();
        walls = new LinkedList<>();
        startingCells = new HashMap<>();
        player1Pieces = new LinkedList<>();
        player2Pieces = new LinkedList<>();
        prizes = new LinkedList<>();
    }

    public List<Cell> getCells() {
        return cells;
    }

    public List<Wall> getWalls() {
        return walls;
    }

    public List<Cell> getPlayer1Pieces(){return player1Pieces;}

    public List<Cell> getPlayer2Pieces(){return player2Pieces;}

    public List<Transmitter> getTransmitters() {
        return transmitters;
    }

    public List<Prize> getPrizes(){return prizes;}

    public void addCell(Cell c){
        cells.add(c);
    }

    public void addTransmitter(Transmitter t){
        transmitters.add(t);
    }

    public void addWall(Wall w){
        walls.add(w);
    }

    public void addPlayer1Pieces(Cell c){
        player1Pieces.add(c);
    }

    public void addPlayer2Pieces(Cell c){
        player2Pieces.add(c);
    }

    public void addPrizes(Prize p){
        prizes.add(p);
    }
    /**
     * give x,y , return a cell with that coordinates
     * return null if not exist.
     */
    public Cell getCell(int x, int y) {
        for(Cell t : cells){
            if(t.getX() == x && t.getY() == y)return t;
        }
        return null;
    }

    public double distance(Cell A,Cell B){
        if(A.getX() != B.getX() && A.getY() != B.getY())return 10000;
        double ans;
        ans = Math.sqrt((A.getX() - B.getX())*(A.getX() - B.getX()) + (A.getY() - B.getY())*(A.getY() - B.getY()));
        return ans;
    }

    public boolean checkWall(Cell a,Cell b){
        Cell temp;
        if(a.getX() != b.getX() && a.getY() != b.getY())return true;
        if(a.getX() == b.getX()){
            if(a.getY() > b.getY()){
                temp = a;
                a = b;
                b = temp;
            }
            while (a.getX() != b.getX() || a.getY() != b.getY()){
                temp = getCell(a.getX(),a.getY() + 1);
                for(Wall t : walls){
                    Cell a2 = t.getCell1(), b2 = t.getCell2();
                    if((a2.getX() == a.getX() && a2.getY() == a.getY() && b2.getX() == temp.getX() && b2.getY() == temp.getY()))return true;
                    a2 = t.getCell2();
                    b2 = t.getCell1();
                    if((a2.getX() == a.getX() && a2.getY() == a.getY() && b2.getX() == temp.getX() && b2.getY() == temp.getY()))return true;
                }
                a = temp;
            }
        }
        else {
            if(a.getX() > b.getX()){
                temp = a;
                a = b;
                b = temp;
            }
            while (a.getX() != b.getX() || a.getY() != b.getY()){
                temp = getCell(a.getX() + 1,a.getY());
                for(Wall t : walls){
                    Cell a2 = t.getCell1(), b2 = t.getCell2();
                    if((a2.getX() == a.getX() && a2.getY() == a.getY() && b2.getX() == temp.getX() && b2.getY() == temp.getY()))return true;
                    a2 = t.getCell2();
                    b2 = t.getCell1();
                    if((a2.getX() == a.getX() && a2.getY() == a.getY() && b2.getX() == temp.getX() && b2.getY() == temp.getY()))return true;
                }
                a = temp;
            }
        }
    return false;
    }
}
