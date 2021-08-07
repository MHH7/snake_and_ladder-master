package ir.sharif.math.bp99_1.snake_and_ladder.logic;

import ir.sharif.math.bp99_1.snake_and_ladder.model.Board;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Player;
import ir.sharif.math.bp99_1.snake_and_ladder.util.Config;

import java.io.*;
import java.util.Scanner;

public class ModelLoader {
    private final File boardFile, playersDirectory, archiveFile;


    /**
     * DO NOT CHANGE ANYTHING IN CONSTRUCTOR.
     */
    public ModelLoader() {
        boardFile = Config.getConfig("mainConfig").getProperty(File.class, "board");
        playersDirectory = Config.getConfig("mainConfig").getProperty(File.class, "playersDirectory");
        archiveFile = Config.getConfig("mainConfig").getProperty(File.class, "archive");
        if (!playersDirectory.exists()) playersDirectory.mkdirs();
    }


    /**
     * read file "boardFile" and craete a Board
     * <p>
     * you can use "BoardBuilder" class for this purpose.
     * <p>
     * pay attention add your codes in "try".
     */
    public Board loadBord() {
        BoardBuilder b = new BoardBuilder("");
        return b.build(boardFile);
    }

    /**
     * load player.
     * if no such a player exist, create an account(file) for him/her.
     * <p>
     * you can use "savePlayer" method of this class for that purpose.
     * <p>
     * add your codes in "try" block .
     */
    public Player loadPlayer(String name, int playerNumber) {
        try {
            savePlayer(new Player(name,0,0,playerNumber));
            int id;
            Scanner sc = new Scanner(getPlayerFile(name));
            sc.next();
            sc.next();
            sc.next();
            sc.next();
            sc.next();
            id = sc.nextInt();
            sc.close();
            return (new Player(name,0,id,playerNumber));
        } catch (IllegalArgumentException | FileNotFoundException e) {
            e.printStackTrace();
            System.err.println("could not find player file");
            System.exit(-2);
        }
        return null;
    }

    /**
     * if player does not have a file, create one.
     * <p>
     * else update his/her file.
     * <p>
     * add your codes in "try" block .
     */
    public void savePlayer(Player player) {
        try {
            // add your codes in this part
            File file = getPlayerFile(player.getName());
            String name = playersDirectory.toPath().toString() + "\\" + player.getName() + ".txt";
            int score = 0;
            int id = 0, temp = 0;
            if(file == null){
                file = new File(name);
                file.createNewFile();
                id++;
            }
            else{
                Scanner sc = new Scanner(file);
                sc.next();
                sc.next();
                sc.next();
                sc.next();
                sc.next();
                sc.next();
                sc.next();
                sc.next();
                sc.next();
                score = sc.nextInt();
                sc.close();
            }
            score += player.getScore();
            PrintStream printStream = new PrintStream(new FileOutputStream(file));
            printStream.print("Name ");
            printStream.print(": ");
            printStream.println(player.getName());
            printStream.print("ID : ");
            printStream.println(id);
            printStream.print("Total score ");
            printStream.print(": ");
            printStream.print(score);
            printStream.close();
        } catch (IllegalArgumentException | FileNotFoundException e) {
            e.printStackTrace();
            System.err.println("could not find player file");
            System.exit(-2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * give you a name (player name), search for its file.
     * return the file if exist.
     * return null if not.
     */
    private File getPlayerFile(String name) {
        name += ".txt";
        File f = new File(playersDirectory,name);
        if(f.exists())return f;
        return null;
    }

    /**
     * at the end of the game save game details
     */
    public void archive(Player player1, Player player2) {
        try {
            // add your codes in this part
            int n = 0;
            Scanner sc = new Scanner(archiveFile);
            while (sc.hasNext()){
                if(sc.next().equals("Game")){
                    sc.next();
                    n = sc.nextInt();
                }
            }
            n++;
            PrintStream printStream = new PrintStream(new FileOutputStream(archiveFile, true));
            printStream.print("Game number ");
            printStream.print(n);
            printStream.println(" {");
            printStream.print("    Player1 : ");
            printStream.println(player1.getName());
            printStream.print("    Player2 : ");
            printStream.println(player2.getName());
            printStream.print("    Player1's score: ");
            printStream.println(player1.getScore());
            printStream.print("    Player2's score: ");
            printStream.println(player2.getScore());
            if(player1.getScore() > player2.getScore()){
                printStream.println("    Player1 was the winner!");
            }
            else if(player2.getScore() > player1.getScore()){
                printStream.println("    Player2 was the winner!");
            }
            else printStream.println("    It was a draw!");
            printStream.println('}');
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
