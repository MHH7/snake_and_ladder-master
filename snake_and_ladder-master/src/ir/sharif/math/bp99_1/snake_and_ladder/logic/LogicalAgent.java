package ir.sharif.math.bp99_1.snake_and_ladder.logic;


import ir.sharif.math.bp99_1.snake_and_ladder.graphic.GraphicalAgent;
import ir.sharif.math.bp99_1.snake_and_ladder.model.*;
import ir.sharif.math.bp99_1.snake_and_ladder.model.pieces.Bomber;
import ir.sharif.math.bp99_1.snake_and_ladder.model.pieces.Piece;
import ir.sharif.math.bp99_1.snake_and_ladder.model.prizes.Prize;
import ir.sharif.math.bp99_1.snake_and_ladder.model.transmitters.Killer;
import ir.sharif.math.bp99_1.snake_and_ladder.model.transmitters.Magic;
import ir.sharif.math.bp99_1.snake_and_ladder.model.transmitters.Transmitter;
import ir.sharif.math.bp99_1.snake_and_ladder.model.transmitters.Worm;
import ir.sharif.math.bp99_1.snake_and_ladder.util.Config;

import java.io.*;
import java.util.List;
import java.util.Scanner;

/**
 * This class is an interface between logic and graphic.
 * some methods of this class, is called from graphic.
 * DO NOT CHANGE ANY PART WHICH WE MENTION.
 */
public class LogicalAgent {
    private final ModelLoader modelLoader;
    private final GraphicalAgent graphicalAgent;
    private GameState gameState;

    /**
     * DO NOT CHANGE CONSTRUCTOR.
     */
    public LogicalAgent() {
        this.graphicalAgent = new GraphicalAgent(this);
        this.modelLoader = new ModelLoader();
        this.gameState = loadGameState();
        loadGame();

    }

    public GameState getGameState() {
        return gameState;
    }

    /**
     * NO CHANGES NEEDED.
     */
    private GameState loadGameState() {
        Board board = modelLoader.loadBord();
        Player player1 = modelLoader.loadPlayer(graphicalAgent.getPlayerNames(1), 1);
        Player player2;
        do {
            player2 = modelLoader.loadPlayer(graphicalAgent.getPlayerNames(2), 2);
        } while (player1.equals(player2));
        player1.setRival(player2);
        player2.setRival(player1);
        GameState g = new GameState(board,player1,player2);
        return g;
    }

    /**
     * NO CHANGES NEEDED.
     */
    public void initialize() {
        graphicalAgent.initialize(gameState);
    }

    public String getCellDetails(int x,int y){
        return "cell at " + x + "," + y;
    }

    /**
     * Give a number from graphic,( which is the playerNumber of a player
     * who clicks "ReadyButton".) you should somehow change that player state.
     * if both players are ready. then start the game.
     */
    public void readyPlayer(int playerNumber){
        if (playerNumber == 1) {
            gameState.getPlayer1().setReady(!gameState.getPlayer1().isReady());
            if (gameState.getPlayer1().isReady() == true) {
                List<Piece> temp = gameState.getPlayer1().getPieces();
                List<Cell> temp2 = gameState.getBoard().getPlayer1Pieces();
                temp.get(0).setCurrentCell(temp2.get(0));
                temp.get(1).setCurrentCell(temp2.get(1));
                temp.get(2).setCurrentCell(temp2.get(2));
                temp.get(3).setCurrentCell(temp2.get(3));

                temp2.get(0).setPiece(temp.get(0));
                temp2.get(1).setPiece(temp.get(1));
                temp2.get(2).setPiece(temp.get(2));
                temp2.get(3).setPiece(temp.get(3));
            } else {
                for (Piece p : gameState.getPlayer1().getPieces()) p.setCurrentCell(null);
                for (Cell c : gameState.getBoard().getPlayer1Pieces()) c.setPiece(null);
            }
        } else {
            gameState.getPlayer2().setReady(!gameState.getPlayer2().isReady());
            if (gameState.getPlayer2().isReady() == true) {
                List<Piece> temp = gameState.getPlayer2().getPieces();
                List<Cell> temp2 = gameState.getBoard().getPlayer2Pieces();
                temp.get(0).setCurrentCell(temp2.get(0));
                temp.get(1).setCurrentCell(temp2.get(1));
                temp.get(2).setCurrentCell(temp2.get(2));
                temp.get(3).setCurrentCell(temp2.get(3));

                temp2.get(0).setPiece(temp.get(0));
                temp2.get(1).setPiece(temp.get(1));
                temp2.get(2).setPiece(temp.get(2));
                temp2.get(3).setPiece(temp.get(3));
            } else {
                for (Piece p : gameState.getPlayer2().getPieces()) p.setCurrentCell(null);
                for (Cell c : gameState.getBoard().getPlayer2Pieces()) c.setPiece(null);
            }
        }
        if (gameState.getPlayer1().isReady() && gameState.getPlayer2().isReady()) {
            gameState.startGame();

        }
        // dont touch this line
        graphicalAgent.update(gameState);
    }

    /**
     * give x,y (coordinates of a cell) :
     * you should handle if user want to select a piece
     * or already selected a piece and now want to move it to a new cell
     */
    // ***
    public void selectCell(int x, int y){
        if (!gameState.isStarted()) return;
        Player p = gameState.getCurrentPlayer();
        Cell c = gameState.getBoard().getCell(x, y);
        if(c.getPiece() == null){
            if(p.getSelectedPiece() != null && p.getSelectedPiece().getIsAlive()){
                if(p.getSelectedPiece().getType().equals("Thief")){
                    if(p.getSelectedPiece().castAbility(c))gameState.nextTurn();
                }
                else{
                    Piece temp = p.getSelectedPiece();
                    if(temp.isValidMove(c,p.getMoveLeft()) && c.canEnter(temp)){
                        temp.moveTo(c);
                        gameState.nextTurn();
                    }
                }
            }
        }
        else{
            if(p.getSelectedPiece() != null){
                if(p.getSelectedPiece() == c.getPiece()){
                    Piece temp = c.getPiece();
                    if(temp.getType().equals("Bomber") || temp.getType().equals("Thief")){
                        if(temp.castAbility(c)){
                            p.setSelectedPiece(null);
                            temp.setSelected(false);
                            gameState.nextTurn();
                        }
                        else{
                            p.setSelectedPiece(null);
                            temp.setSelected(false);
                        }
                    }
                    else {
                        p.setSelectedPiece(null);
                        temp.setSelected(false);
                    }
                }
                else if(c.getPiece().getPlayer() != p && p.getSelectedPiece().getType().equals("Sniper") && p.getSelectedPiece().getIsAlive()){
                    if(p.getSelectedPiece().castAbility(c)){
                        p.getSelectedPiece().setSelected(false);
                        p.setSelectedPiece(null);
                        gameState.nextTurn();
                    }
                }
                else if(c.getPiece().getPlayer() == p && p.getSelectedPiece().getType().equals("Healer")){
                    if(p.getSelectedPiece().castAbility(c)){
                        p.getSelectedPiece().setSelected(false);
                        p.setSelectedPiece(null);
                        gameState.nextTurn();
                    }
                    else if(c.getPiece().getIsAlive()){
                        p.getSelectedPiece().setSelected(false);
                        p.setSelectedPiece(c.getPiece());
                        c.getPiece().setSelected(true);
                    }
                }
                else if(c.getPiece().getPlayer() == p && c.getPiece().getIsAlive()){
                    p.getSelectedPiece().setSelected(false);
                    p.setSelectedPiece(c.getPiece());
                    c.getPiece().setSelected(true);
                }
            }
            else if(c.getPiece().getPlayer() == p && c.getPiece().getIsAlive()){
                p.setSelectedPiece(c.getPiece());
                c.getPiece().setSelected(true);
            }
        }
        // dont touch this line
        graphicalAgent.update(gameState);
        checkForEndGame();
    }

    /**
     * check for endgame and specify winner
     * if player one in winner set winner variable to 1
     * if player two in winner set winner variable to 2
     * If the game is a draw set winner variable to 3
     */
    private void checkForEndGame() {
        if (!gameState.isStarted()) return;
        if (gameState.getTurn() >= 41) {
            // game ends
            int winner = 1;
            if (gameState.getPlayer2().getScore() > gameState.getPlayer1().getScore()) winner = 2;
            else if (gameState.getPlayer2().getScore() == gameState.getPlayer1().getScore()) winner = 3;
            File playersDirectory = Config.getConfig("mainConfig").getProperty(File.class, "playersDirectory");
            String name = gameState.getPlayer1().getName() + "_" + gameState.getPlayer2().getName() + "_Game.txt";
            File f = new File(playersDirectory, name);
            f.delete();
            // dont touch it
            graphicalAgent.playerWin(winner);
            /* save players*/
            modelLoader.savePlayer(gameState.getPlayer1());
            modelLoader.savePlayer(gameState.getPlayer2());
            modelLoader.archive(gameState.getPlayer1(), gameState.getPlayer2());
            LogicalAgent logicalAgent = new LogicalAgent();
            logicalAgent.initialize();
        }
    }


    /**
     * Give a number from graphic,( which is the playerNumber of a player
     * who left clicks "dice button".) you should roll his/her dice
     * and update *****************
     */
    public void rollDice(int playerNumber) {
        if (!gameState.isStarted()) return;
        if (gameState.getCurrentPlayer() == gameState.getPlayer1() && playerNumber == 2) return;
        if (gameState.getCurrentPlayer() == gameState.getPlayer2() && playerNumber == 1) return;
        Player p;
        if (playerNumber == 1) p = gameState.getPlayer1();
        else p = gameState.getPlayer2();
        if (!p.isDicePlayedThisTurn()) {
            int d = p.getDice().roll();
            int fl = 0;
            if(d == p.getLastMoveLeft()){
                p.getDice().addChance(d,1);
                fl = 1;
            }
            p.setLastMoveLeft(d);
            if(fl == 1)p.setLastMoveLeft(-1);
            if(d == 1)p.getPieces().get(3).setAbility(true);
            if(d == 3)p.getPieces().get(1).setAbility(true);
            if(d == 5)p.getPieces().get(0).setAbility(true);
            if (d == 6) p.applyOnScore(4);
            p.setMoveLeft(d);
            p.setDicePlayedThisTurn(true);
            if (!p.hasMove(gameState.getBoard(), d)) {
                p.applyOnScore(-3);
                gameState.nextTurn();
            }
        }
        // dont touch this line
        graphicalAgent.update(gameState);
    }


    /**
     * Give a number from graphic,( which is the playerNumber of a player
     * who right clicks "dice button".) you should return the dice detail of that player.
     * you can use method "getDetails" in class "Dice"(not necessary, but recommended )
     */
    public String getDiceDetail(int playerNumber) {
        String ans;
        if (playerNumber == 1) ans = gameState.getPlayer1().getDice().getDetails();
        else ans = gameState.getPlayer2().getDice().getDetails();
        return ans;
    }

    public void saveGame(){
        try {
            File playersDirectory = Config.getConfig("mainConfig").getProperty(File.class, "playersDirectory");
            Player p1 = gameState.getPlayer1();
            Player p2 = gameState.getPlayer2();
            String name = p1.getName() + "_" + p2.getName() + "_Game.txt";
            File f = new File(playersDirectory, name);
            f.createNewFile();
            PrintStream p = new PrintStream(new FileOutputStream(f));
            p.println("CELLS[ 7 16 ]:");
            for (int i = 0; i < 7; i++) {
                for (int j = 0; j < 16; j++) {
                    p.print(gameState.getBoard().getCell(i + 1, j + 1).getColor().toString() + " ");
                }
                p.println();
            }
            p.println();
            p.println("STARTING_CELLS[ 8 ]:");
            p.println("1 1 1");
            p.println("3 1 1");
            p.println("5 1 1");
            p.println("7 1 1");
            p.println("1 16 2");
            p.println("3 16 2");
            p.println("5 16 2");
            p.println("7 16 2");
            p.println();
            p.print("WALLS[ ");
            p.print(gameState.getBoard().getWalls().size());
            p.println(" ]:");
            List<Wall> W = gameState.getBoard().getWalls();
            for (Wall w : W) {
                p.print(w.getCell1().getX() + " ");
                p.print(w.getCell1().getY() + " ");
                p.print(w.getCell2().getX() + " ");
                p.println(w.getCell2().getY());
            }
            p.println();
            List<Transmitter> T = gameState.getBoard().getTransmitters();
            p.print("TRANSMITTERS[ ");
            p.print(T.size());
            p.println(" ]:");
            for (Transmitter t : T) {
                p.print(t.getFirstCell().getX() + " ");
                p.print(t.getFirstCell().getY() + " ");
                p.print(t.getLastCell().getX() + " ");
                p.print(t.getLastCell().getY() + " ");
                if (t instanceof Worm) p.println("U");
                else if (t instanceof Magic) p.println("M");
                else if (t instanceof Killer) p.println("P");
                else p.println("O");
            }
            p.println();
            p.print("PRIZES[ ");
            List<Prize> P = gameState.getBoard().getPrizes();
            p.println(P.size() + " ]:");
            for (Prize pz : P) {
                if(pz.getCell() == null)p.print(-1 + " ");
                else p.print(pz.getCell().getX() + " ");
                if(pz.getCell() == null)p.print(-1 + " ");
                else p.print(pz.getCell().getY() + " ");
                p.print(pz.getPoint() + " ");
                p.print(pz.getChance() + " ");
                p.println(pz.getDiceNumber() + " ");
            }
            p.println();
            p.print(p1.getName() + " ");
            p.print(p1.getScore() + " ");
            p.print(p1.isDicePlayedThisTurn() + " ");
            p.print(p1.getMoveLeft() + " ");
            p.print(p1.getLastMoveLeft() + " ");
            if (p1.getSelectedPiece() == null) p.println("null");
            else p.println(p1.getSelectedPiece().getType());
            p.println();
            p.print(p2.getName() + " ");
            p.print(p2.getScore() + " ");
            p.print(p2.isDicePlayedThisTurn() + " ");
            p.print(p2.getMoveLeft() + " ");
            p.print(p2.getLastMoveLeft() + " ");
            if (p2.getSelectedPiece() == null) p.println("null");
            else p.println(p2.getSelectedPiece().getType());
            p.println();
            List<Piece> Pc = p1.getPieces();
            for (Piece pp : Pc) {
                p.print(pp.getCurrentCell().getX() + " " + pp.getCurrentCell().getY() + " ");
                p.print(pp.isSelected() + " ");
                p.print(pp.getAbility() + " ");
                p.println(pp.getIsAlive());
            }
            if(Pc.get(2).getPrize() == null)p.println(-1);
            else p.println(Pc.get(2).getPrize().getIndex());
            p.println();
            Pc = p2.getPieces();
            for (Piece pp : Pc) {
                p.print(pp.getCurrentCell().getX() + " " + pp.getCurrentCell().getY() + " ");
                p.print(pp.isSelected() + " ");
                p.print(pp.getAbility() + " ");
                p.println(pp.getIsAlive());
            }
            if(Pc.get(2).getPrize() == null)p.println(-1);
            else p.println(Pc.get(2).getPrize().getIndex());
            p.println();
            Dice d = p1.getDice();
            for (int i = 0; i < 7; i++) p.print(d.getChence(i) + " ");
            p.println();
            d = p2.getDice();
            for (int i = 0; i < 7; i++) p.print(d.getChence(i) + " ");
            p.println();
            p.println(gameState.getTurn());
            p.close();
        }catch (IOException I){

        }
    }

    public void loadGame(){
        File playersDirectory = Config.getConfig("mainConfig").getProperty(File.class, "playersDirectory");
        Player p1 = gameState.getPlayer1();
        Player p2 = gameState.getPlayer2();
        String name = p1.getName() + "_" + p2.getName() + "_Game.txt";
        File f = new File(playersDirectory,name);
        BoardBuilder b = new BoardBuilder("");
        if(f.exists()){
            gameState = new GameState(b.build(f),p1,p2);
            try {
                Scanner sc = new Scanner(f);
                String s = "";
                while (!s.equals(p1.getName())){
                    s = sc.next();
                }
                p1.setScore(sc.nextInt());
                s = sc.next();
                if(s.equals("true"))p1.setDicePlayedThisTurn(true);
                else p1.setDicePlayedThisTurn(false);
                p1.setMoveLeft(sc.nextInt());
                p1.setLastMoveLeft(sc.nextInt());
                s = sc.next();
                if(s.equals(null))p1.setSelectedPiece(null);
                else if(s.equals("Healer"))p1.setSelectedPiece(p1.getPieces().get(3));
                else if(s.equals("Bomber"))p1.setSelectedPiece(p1.getPieces().get(1));
                else if(s.equals("Thief"))p1.setSelectedPiece(p1.getPieces().get(2));
                else p1.setSelectedPiece(p1.getPieces().get(0));

                sc.next();
                p2.setScore(sc.nextInt());
                s = sc.next();
                if(s.equals("true"))p2.setDicePlayedThisTurn(true);
                else p2.setDicePlayedThisTurn(false);
                p2.setMoveLeft(sc.nextInt());
                p2.setLastMoveLeft(sc.nextInt());
                s = sc.next();
                if(s.equals(null))p2.setSelectedPiece(null);
                else if(s.equals("Healer"))p2.setSelectedPiece(p2.getPieces().get(3));
                else if(s.equals("Bomber"))p2.setSelectedPiece(p2.getPieces().get(1));
                else if(s.equals("Thief"))p2.setSelectedPiece(p2.getPieces().get(2));
                else p2.setSelectedPiece(p2.getPieces().get(0));
                List<Piece> P = p1.getPieces();
                for(Piece p : P){
                    int x = sc.nextInt();
                    int y = sc.nextInt();
                    Cell c = gameState.getBoard().getCell(x,y);
                    p.setCurrentCell(c);
                    c.setPiece(p);
                    s = sc.next();
                    if(s.equals("true"))p.setSelected(true);
                    else p.setSelected(false);
                    s = sc.next();
                    if(s.equals("true"))p.setAbility(true);
                    else p.setAbility(false);
                    s = sc.next();
                    if(s.equals("true"))p.setIsAlive(true);
                    else p.setIsAlive(false);
                }
                int xx = sc.nextInt();
                if(xx == -1)p1.getPieces().get(2).setPrize(null);
                else p1.getPieces().get(2).setPrize(gameState.getBoard().getPrizes().get(xx));
                P = p2.getPieces();
                for(Piece p : P){
                    int x = sc.nextInt();
                    int y = sc.nextInt();
                    Cell c = gameState.getBoard().getCell(x,y);
                    p.setCurrentCell(c);
                    c.setPiece(p);
                    s = sc.next();
                    if(s.equals("true"))p.setSelected(true);
                    else p.setSelected(false);
                    s = sc.next();
                    if(s.equals("true"))p.setAbility(true);
                    else p.setAbility(false);
                    s = sc.next();
                    if(s.equals("true"))p.setIsAlive(true);
                    else p.setIsAlive(false);
                }
                xx = sc.nextInt();
                if(xx == -1)p2.getPieces().get(2).setPrize(null);
                else p2.getPieces().get(2).setPrize(gameState.getBoard().getPrizes().get(xx));

                for(int i = 0;i < 7;i++){
                    int x = sc.nextInt();
                    p1.getDice().setChance(i,x);
                }

                for(int i = 0;i < 7;i++){
                    int x = sc.nextInt();
                    p2.getDice().setChance(i,x);
                }
                xx = sc.nextInt();
                gameState.setTurn(xx);
            }catch (IOException I){

            }
        }
    }
}
