package com.congkak.mechanics;

import com.congkak.objects.Player;
import com.congkak.objects.Hole;
import com.congkak.objects.Options;

import java.lang.*;

/**
 * <h1>GameMechanics</h1>
 * The functions required for game management
 * 
 * @author  Yap Jia Yung
 * @author  Lishan Abbas
 * @since   6/15/17
 */
public class GameMechanics {

   /**
    * Clears the screen based on the operating system
    * 
    * @param operatingSystem Operating System as a String from System
    */
    public static void clearScreen(String operatingSystem) {

        Runtime runtime = Runtime.getRuntime();

        //Checks for the operating system and then clears the screen
        try {
            if (operatingSystem.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            System.out.println("Error : Clear command does not work");
        }
    }

   /**
    * Sleep for a second during the game
    */
    public static void sleep() {
        try {
            Thread.sleep(1000);
            // Thread.sleep(0); // Disable Animations
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

   /**
    * Sleep for a period during the game
    *
    * @param millis The time interval in terms of milliseconds
    */
    public static void sleep(int millis) {
        try {
            Thread.sleep(millis);
            // Thread.sleep(0); // Disable Animations
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

   /**
    * Display the congkak board according to the state pass in
    *
    * @param holes Holes array object
    * @param options Options Object
    * @param home1 player 1 home score
    * @param home2 player 2 home score
    */
    public static void display(Hole holes[], Options options, int home1, int home2) {

        clearScreen(System.getProperty("os.name"));

        int boardSize = options.getBoardSize();
        int boardBorders = options.getBoardBorders();

        System.out.print("+");
        for (int i = 0; i < (boardBorders * 6) + (28) - 1; i++) {
            System.out.print("-");
        }
        System.out.println("+");

        System.out.print("|              ");
        for (int i = boardBorders; i > 0; i--) {
            if (i >= 10) System.out.printf(" %3d  ", i);
            else System.out.printf("  %d   ", i);
        }

        System.out.print("             |");
        System.out.println();

        System.out.print("|   Player2   +");
        for (int i = boardSize - 1; i >= boardBorders; i--) {
            System.out.print("  -  +");
        }

        System.out.print("   Player1   |");

        System.out.print("\n|   +  -  +   ");


        System.out.print("|");
        for (int i = boardSize - 1; i >= boardBorders; i--) {
            System.out.printf(" %03d |", holes[i].getValue());
        }

        System.out.print("   +  -  +   |");

        System.out.println();
        System.out.printf("|   | %03d |   ", home2);

        System.out.print("+");
        for (int i = boardSize - 1; i >= boardBorders; i--) {
            System.out.printf("  -  +", holes[i].getValue());
        }


        System.out.printf("   | %03d |   |", home1);


        System.out.print("\n|   +  -  +   ");

        System.out.print("|");
        for (int i = 0; i < boardBorders; i++) {
            System.out.printf(" %03d |", holes[i].getValue());
        }

        System.out.print("   +  -  +   |");

        System.out.print("\n|    Score    +");
        for (int i = boardSize - 1; i >= boardBorders; i--) {
            System.out.print("  -  +");
        }

        System.out.print("    Score    |");

        System.out.println();
        System.out.print("|              ");

        for (int i = 0; i < boardBorders; i++) {
            if (i >= 10) System.out.printf(" %3d  ", i + 1);
            else System.out.printf("  %d   ", i + 1);
        }

        if (boardBorders >= 10) System.out.print("            |");
        else System.out.print("             |");
        System.out.println();

        System.out.print("+");
        for (int i = 0; i < (boardBorders * 6) + (28) - 1; i++) {
            System.out.print("-");
        }
        System.out.println("+");

        System.out.println();
    }

   /**
    * Distribute the seeds to each hole at the start according to seed count for each hole
    *
    * @param holes Holes array object
    * @param seedCount amount of seed retrieve from a selected hole
    */
    public static void distributeSeeds(Hole[] holes, int seedCount) {
        for (int i = 0; i < holes.length; i++) {
            holes[i] = new Hole(seedCount);
        }
    }

   /**
    * Check if user selected move is a valid move
    *
    * @param holes Holes array object 
    * @param options Options array object
    * @param currentIndex the index of the selected hole
    *
    * @return boolean True if valid move, false if invalid move
    */
    public static boolean validateMove(Hole[] holes, Options options, int currentIndex) {

        int boardBorders = options.getBoardSize() / 2;

        int moveIndex = currentIndex;

        if (
            Player.getCurrentPlayer() == 1 &&
            moveIndex >= 0 &&
            moveIndex < boardBorders &&
            !holes[moveIndex].isEmpty()
        ) return true;

        else if (
            Player.getCurrentPlayer() == 2 &&
            moveIndex >= boardBorders &&
            moveIndex < options.getBoardSize() &&
            !holes[moveIndex].isEmpty()
        ) return true;

        else return false;
    }

   /**
    * Locate the hole to continue from if player are able to continue move
    *
    * @param currentHole the hole player last stop at from distributing the seeds
    *
    * @return int The hole to continue from currentHole
    */
    public static int continuedMove(Options options, int currentHole) {

        int boardSize = options.getBoardSize();

        if (currentHole + 1 > boardSize - 1) return currentHole + 1 - boardSize;
        else return currentHole + 1;
    }

   /**
    * Distribute the seeds obtained from selected hole to every neighbouring hole one at a time 
    *
    * @param holes Holes array object
    * @param options Options Object
    * @param currentHole the hole player last stop at from distributing the seeds
    * @param selectedIndex the hole where player select for move
    * @param seedCountAtHand number of seeds obtain from the hole selected
    * @param playerOne Player Object
    * @param playerTwo Player Object
    *
    * @return int The current hole from the last seed being distributed.
    */
    public static int seedDistribution(Hole holes[], Options options, int currentHole, int selectedIndex, int seedCountAtHand, Player playerOne, Player playerTwo) {

        for (int i = 1; i <= seedCountAtHand; i++) {

            if (selectedIndex + i > options.getBoardSize() - 1) {
                selectedIndex -= options.getBoardSize();
                holes[selectedIndex + i].incrementValue();
            } else {
                holes[selectedIndex + i].incrementValue();
            }

            currentHole = selectedIndex + i;

            sleep();
            display(holes, options, playerOne.getScore(), playerTwo.getScore());
        }
        return currentHole;
    }

   /**
    * Fixed the position of move for each player.Eg: 1st hole will always be on left side of each player 
    *
    * @param select the hole where player selected to move
    * @param options Options object
    *
    * @return int The move Player selected according to the fixed position.
    */
    public static int offsetMoves(int select, Options options) {

        if (Player.getCurrentPlayer() == 1) {
            return select;
        } else {
            return (options.getBoardBorders()) + (select);
        }
    }

   /**
    * Check if the next neighbouring hole is empty
    *
    * @param holes Hole array object
    * @param currentHole the hole player last stop at from distributing the seeds
    * @param boardSize the size of the Board
    *
    * @return int The seed count in the next hole. 
    */
    public static int checkEmptyHoles(Hole holes[], int currentHole, int boardSize) {
        if (currentHole + 1 > boardSize - 1) {
            return holes[currentHole + 1 - boardSize].getValue();
        } else {
            return holes[currentHole + 1].getValue();
        }
    }

   /**
    * Check if a player can continue to move while distributing the seeds
    *
    * @param holes Hole array object
    * @param options Options array object
    * @param currentHole the hole player last stop at from distributing the seeds
    *
    * @return boolean Whether a player can continue to move from last distributed seed
    */
    public static boolean checkContinueMove(Hole holes[], Options options, int currentHole) {

        int check = checkEmptyHoles(holes, currentHole, options.getBoardSize());

        if (check == 0) return false;

        else return true;
    }

   /**
    * Obtain score from the hole next to the empty hole when the distribution of seeds stop
    *
    * @param currentHole the hole player last stop at from distributing the seeds
    * @param options Options object
    * @param holes Holes array object
    * @param home1 player 1 home score
    * @param home2 player 2 home score
    *
    * @return int The score obtain for currentPlayer home
    */
    public static int obtainScore(int currentHole, Options options, Hole holes[], int home1, int home2) {

        int home = 0;
        int boardSize = options.getBoardSize();

        if (currentHole + 2 > boardSize - 1) {
            if (Player.isPlayerOne()) {
                home1 += holes[currentHole + 2 - boardSize].getValue();
                holes[currentHole + 2 - boardSize].clear();
                home = home1;
            } else if (Player.isPlayerTwo()) {
                home2 += holes[currentHole + 2 - boardSize].getValue();
                holes[currentHole + 2 - boardSize].clear();
                home = home2;
            }
        } else {
            if (Player.isPlayerOne()) {
                home1 += holes[currentHole + 2].getValue();
                holes[currentHole + 2].clear();
                home = home1;
            } else if (Player.isPlayerTwo()) {
                home2 += holes[currentHole + 2].getValue();
                holes[currentHole + 2].clear();
                home = home2;
            }
        }

        return home;
    }

   /**
    * Check if player has any legal move to play
    *
    * @param holes Holes array object
    * @param options Options object
    *
    * @return boolean True if there is any legal move to play or False if no more legal move to play
    */
    public static boolean checkLegalMove(Hole holes[], Options options, int home1, int home2) {

        int i;
        int count = 0;
        int boardBorders = options.getBoardBorders();
        int boardSize = options.getBoardSize();
        boolean flag = true;

        for (i = 0; i < boardBorders; i++) {
            if (holes[i].isEmpty()) {
                count ++;
            }
            if (count == boardBorders && Player.isPlayerTwo()) {
                flag = false;

                display(holes, options, home1, home2);

                System.out.println("No more legal moves for Player 1.");
                System.out.println("Remaining seeds are deposited to Player 2 Home hole.\n");
            }
        }

        count = 0;
        for (i = boardBorders; i < boardSize; i++) {
            if (holes[i].isEmpty()) {
                count ++;
            }
            if (count == boardBorders && Player.isPlayerOne()) {
                flag = false;

                System.out.println("No more legal move for Player 2.");
                System.out.println("Remaining seeds are deposited to Player 1 Home hole.\n");
            }
        }

        return flag;
    }

   /**
    * Sum the left over seeds to opponent when either player has no more valid move to play
    * 
    * @param holes Hole array object
    * @param options Options object 
    * @param home1 the score of player1 home
    * @param home2 the score of player2 home
    * 
    * @return int The sum of leftover seed and current player home score
    */
    public static int leftOverSeed(Hole holes[], Options options, int home1, int home2) {

        int i, home = 0;
        int boardSize = options.getBoardSize();
        int boardBorders = options.getBoardBorders();

        if(Player.isPlayerTwo()) {
            for (i = boardSize - 1; i >= boardBorders; i--) {
                home2 += holes[i].getValue();
                holes[i].clear();

            }
            home = home2;
        } else if(Player.isPlayerOne()) {
            for(i = 0; i < boardBorders; i++){
                home1 += holes[i].getValue();
                holes[i].clear();
            }
            home = home1;
        }
        return home;
    }
}
