package ir.sharif.math.bp99_1.snake_and_ladder.logic;

import ir.sharif.math.bp99_1.snake_and_ladder.model.Board;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Cell;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Color;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Wall;
import ir.sharif.math.bp99_1.snake_and_ladder.model.pieces.Piece;
import ir.sharif.math.bp99_1.snake_and_ladder.model.prizes.Prize;
import ir.sharif.math.bp99_1.snake_and_ladder.model.transmitters.Killer;
import ir.sharif.math.bp99_1.snake_and_ladder.model.transmitters.Magic;
import ir.sharif.math.bp99_1.snake_and_ladder.model.transmitters.Transmitter;
import ir.sharif.math.bp99_1.snake_and_ladder.model.transmitters.Worm;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class BoardBuilder {

    private Board board;

    public BoardBuilder(String src) {
        board = new Board();
    }

    /**
     * give you a string in constructor.
     * <p>
     * you should read the string and create a board according to it.
     */
    public Board build(File boardFile) {
        try {
            Scanner scanner = new Scanner(boardFile);

            scanner.next();
            scanner.next();
            scanner.next();
            scanner.next();

            String temp = "";
            Cell c;
            for(int i = 1;i <= 7;i++){
                for(int j = 1;j <= 16;j++){
                    temp = scanner.next();
                    c = new Cell(Color.valueOf(temp),i,j);
                    c.setBoard(board);
                    board.addCell(c);
                }
            }

            scanner.next();

            int cnt = Integer.parseInt(scanner.next());
            scanner.next();
            int n,x,y;
            for(int i = 0;i < cnt;i++){
                x = scanner.nextInt();
                y = scanner.nextInt();
                n = scanner.nextInt();
                if(n == 1){
                    board.addPlayer1Pieces(board.getCell(x,y));
                }
                else{
                    board.addPlayer2Pieces(board.getCell(x,y));
                }
            }

            scanner.next();

            cnt = Integer.parseInt(scanner.next());
            scanner.next();
            int x1,x2,y1,y2;
            for(int i = 0;i < cnt;i++){
                x1 = scanner.nextInt();
                y1 = scanner.nextInt();
                x2 = scanner.nextInt();
                y2 = scanner.nextInt();
                board.addWall(new Wall(board.getCell(x1,y1),board.getCell(x2,y2)));
            }

            scanner.next();

            cnt = Integer.parseInt(scanner.next());
            scanner.next();
            for(int i = 0;i < cnt;i++){
                x1 = scanner.nextInt();
                y1 = scanner.nextInt();
                x2 = scanner.nextInt();
                y2 = scanner.nextInt();
                String type = scanner.next();
                if(type.equals("U")){
                    Worm W = new Worm(board.getCell(x1,y1),board.getCell(x2,y2));
                    board.addTransmitter(W);
                    board.getCell(x1,y1).setTransmitter(W);
                }
                else if(type.equals("P")){
                    Killer K = new Killer(board.getCell(x1,y1),board.getCell(x2,y2));
                    board.addTransmitter(K);
                    board.getCell(x1,y1).setTransmitter(K);
                }
                else if(type.equals("M")){
                    Magic M = new Magic(board.getCell(x1,y1),board.getCell(x2,y2));
                    board.addTransmitter(M);
                    board.getCell(x1,y1).setTransmitter(M);
                }
                else{
                    Transmitter T = new Transmitter(board.getCell(x1,y1),board.getCell(x2,y2));
                    board.addTransmitter(T);
                    board.getCell(x1,y1).setTransmitter(T);
                }
            }

            scanner.next();

            cnt = Integer.parseInt(scanner.next());
            scanner.next();
            int scr, t;
            for(int i = 0;i < cnt;i++){
                x = scanner.nextInt();
                y = scanner.nextInt();
                scr = scanner.nextInt();
                t = scanner.nextInt();
                n = scanner.nextInt();
                Prize P = new Prize(board.getCell(x,y),scr,t,n);
                P.setIndex(i);
                board.addPrizes(P);
                if(board.getCell(x,y) != null)
                board.getCell(x,y).setPrize(P);
            }

            scanner.close();

            for(Cell cell: board.getCells()){
                Cell movaghat;
                int X = cell.getX();
                int Y = cell.getY();
                int flag;
                if(board.getCell(X + 1,Y) != null){
                    movaghat = board.getCell(X + 1,Y);
                    cell.getAdjacentCells().add(movaghat);
                    flag = 0;
                    for(Wall w : board.getWalls()){
                        if((w.getCell1() == cell && w.getCell2() == movaghat) || (w.getCell2() == cell && w.getCell1() == movaghat)){
                            flag = 1;
                        }
                    }
                    if(flag == 0)cell.getAdjacentOpenCells().add(movaghat);
                }
                if(board.getCell(X - 1,Y) != null){
                    movaghat = board.getCell(X - 1,Y);
                    cell.getAdjacentCells().add(movaghat);
                    flag = 0;
                    for(Wall w : board.getWalls()){
                        if((w.getCell1() == cell && w.getCell2() == movaghat) || (w.getCell2() == cell && w.getCell1() == movaghat)){
                            flag = 1;
                        }
                    }
                    if(flag == 0)cell.getAdjacentOpenCells().add(movaghat);
                }
                if(board.getCell(X,Y - 1) != null){
                    movaghat = board.getCell(X,Y - 1);
                    cell.getAdjacentCells().add(movaghat);
                    flag = 0;
                    for(Wall w : board.getWalls()){
                        if((w.getCell1() == cell && w.getCell2() == movaghat) || (w.getCell2() == cell && w.getCell1() == movaghat)){
                            flag = 1;
                        }
                    }
                    if(flag == 0)cell.getAdjacentOpenCells().add(movaghat);
                }
                if(board.getCell(X,Y + 1) != null){
                    movaghat = board.getCell(X,Y + 1);
                    cell.getAdjacentCells().add(movaghat);
                    flag = 0;
                    for(Wall w : board.getWalls()){
                        if((w.getCell1() == cell && w.getCell2() == movaghat) || (w.getCell2() == cell && w.getCell1() == movaghat)){
                            flag = 1;
                        }
                    }
                    if(flag == 0)cell.getAdjacentOpenCells().add(movaghat);
                }
            }
            return board;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.err.println("could not find board file");
            System.exit(-2);
        }
        return null;
    }
}
