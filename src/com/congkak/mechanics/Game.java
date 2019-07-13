package com.congkak.mechanics;

import com.congkak.objects.Hole;
import com.congkak.objects.Player;
import com.congkak.objects.Options;
import com.congkak.objects.PlayerBot;

import java.util.Scanner;
import java.util.Random;

/**
 * <h1>Game</h1>
 * A Game Object that can play a game
 * Custom Options are available through Options Object
 * Main method is startGame(), available publicly
 *
 * @author  Lishan Abbas
 * @author  Yap Jia Yung
 * @since   6/15/17
 */
public class Game {

    private final String OS;
    private Scanner userInput;
    private Options options;
    private boolean gameHasEnded;

   /**
    * Constructor A
    * 
    * <p>
    * A new game object with custom options
    * </p>
    * 
    * @param newOptions Options object containing custom options selected by the user
    */
    public Game(Options newOptions) {

        // CUSTOM GAME WITH OPTIONS
        this.options = newOptions;
        this.OS = System.getProperty("os.name");
        this.userInput = new Scanner(System.in);
        this.gameHasEnded = false;
    }

   /**
    * Constructor B
    * 
    * <p>
    * A new game object with default options
    * Gameboard Size: 7
    * Seeds per Hole: 4
    * Game Type: Basic (1)
    * Player 1 Name: "P1"
    * Player 2 Name: "P2"
    * </p>
    * 
    */
    public Game() {

        // DEFAULT GAME
        this.options = new Options(1, 7, 4, "P1", "P2");
        this.OS = System.getProperty("os.name");
        this.userInput = new Scanner(System.in);
        this.gameHasEnded = false;
    }

   /**
    * Checks whether the game is ended or not manually
    * 
    * @return boolean gameHasEnded
    */
    public boolean isEnded() { return this.gameHasEnded; }

   /**
    * Main : Starts a new game and returns a 0 when it is finished
    * 
    * @return int A flag for indicating game events
    */
    public int startGame() {

        int suggestedMoveFlag = -1;

        if (options.getGameType() == 3) {
            GameMechanics.clearScreen(OS);
            int choice;
            Scanner tempScannner = new Scanner(System.in);
            System.out.println("Extra options for Game Type: Advanced");
            System.out.println("Do you want a bot to suggest moves for you? (Enter the following values!)");
            System.out.println("-1 : Do not display suggested moves");
            System.out.println("0 : For both players");
            System.out.println("1 : For Player 1");
            System.out.println("2 : For Player 2");
            System.out.println();

            System.out.print("Your choice: ");
            choice = tempScannner.nextInt();

            if (choice >= -1 && choice <= 2) {
                suggestedMoveFlag = choice;
            } else {
                suggestedMoveFlag = -1;
            }
        }

        Hole holes[] = new Hole[options.getBoardSize()];

        this.gameHasEnded = false;
        boolean gameIsRunning = false;
        boolean continueToMove = false;

        int currentHole = 0;
        int selectedIndex = 0;
        int seedCountAtHand = 0;

        Random rand = new Random();

        GameMechanics.distributeSeeds(holes, options.getSeedCount());

        Player playerOne = new Player(options.getPlayerOneName());
        Player playerTwo = new Player(options.getPlayerTwoName());

        Player.setCurrentPlayer(1);

        do {

            if (!continueToMove) {

                if (!gameIsRunning) {

                    coinflip(rand, playerOne, playerTwo);
                    gameIsRunning = true;

                } else Player.togglePlayer();

                GameMechanics.clearScreen(OS);
                GameMechanics.display(holes, options, playerOne.getScore(), playerTwo.getScore());

                do {


                    if (Player.isPlayerOne()) {
                        System.out.println(playerOne.getName() + " to move");
                    } else {
                        System.out.println(playerTwo.getName() + " to move");
                    }

                    if (suggestedMoveFlag != -1) {
                        PlayerBot playerBOT;
                        if (Player.getCurrentPlayer() == 1) {
                            playerBOT = new PlayerBot(playerOne.getScore());
                        } else {
                            playerBOT = new PlayerBot(playerTwo.getScore());

                        }
                        int bestMove = GameBotMechanics.getBestMove(holes, options, playerBOT);
                        bestMove = getBestMoveForSuggestion(bestMove, options.getHoleCount());
                        suggestedMoveDisplay(suggestedMoveFlag, bestMove);
                    }

                    System.out.print("Enter Move: ");
                    selectedIndex = userInput.nextInt() - 1;

                    System.out.println();

                    selectedIndex = GameMechanics.offsetMoves(selectedIndex, options);

                    if (!GameMechanics.validateMove(holes, options, selectedIndex)) {
                        System.out.println("Move is not valid"); 
                    } 

                } while (!GameMechanics.validateMove(holes, options, selectedIndex));

                seedCountAtHand = holes[selectedIndex].getValue();

            } else {
                selectedIndex = GameMechanics.continuedMove(options, currentHole);
                seedCountAtHand = holes[selectedIndex].getValue();
            }

            // Clear the selected hole's value
            holes[selectedIndex].clear();

            //seeds from selected hole will be distributed to neighbouring holes and current hole will be increment for each distribution
            currentHole = GameMechanics.seedDistribution(holes, options,currentHole, selectedIndex, seedCountAtHand, playerOne, playerTwo);


            // check whether the player gets to continue or not
            continueToMove = GameMechanics.checkContinueMove(holes, options, currentHole);

            //if player is not allowed to continue, score are obtain from the hole next to empty hole. Player lose his turn.
            if (!continueToMove) {
                GameMechanics.sleep();
                if (Player.isPlayerOne()) {
                    int score = GameMechanics.obtainScore(currentHole, options, holes, playerOne.getScore(), playerTwo.getScore());
                    playerOne.setScore(score);
                } else if (Player.isPlayerTwo()) {
                    int score = GameMechanics.obtainScore(currentHole, options, holes, playerOne.getScore(), playerTwo.getScore());
                    playerTwo.setScore(score);
                }
            }

            // check if there is any legal move for current player
            // if no legal move, leftover seeds are deposit to opponent's home hole.
            if (!GameMechanics.checkLegalMove(holes, options, playerOne.getScore(), playerTwo.getScore())) {
                if (Player.isPlayerOne()) {

                    int score = GameMechanics.leftOverSeed(holes, options, playerOne.getScore(), playerTwo.getScore());
                    playerOne.setScore(score);

                } else if (Player.isPlayerTwo()) {

                    int score = GameMechanics.leftOverSeed(holes, options, playerOne.getScore(), playerTwo.getScore());
                    playerTwo.setScore(score);
                }

                gameHasEnded = true;
            }

        } while (!gameHasEnded);

        gameIsRunning = false;

        GameMechanics.clearScreen(OS);
        GameMechanics.display(holes, options, playerOne.getScore(), playerTwo.getScore());

        System.out.println("Game Ended!");

        displayScores(playerOne, playerTwo);

        return 0;
    }

   /**
    * Does a coinflip!
    * 
    * @param rand Random Object
    * @param playerOne Player 1 Object
    * @param playerTwo Player 2 Object
    */
    private void coinflip(Random rand, Player playerOne, Player playerTwo) {

        GameMechanics.clearScreen(OS);

        System.out.print("Please wait, the coin is flipping! ");
        GameMechanics.sleep(1000);
        System.out.print("3 ");
        GameMechanics.sleep(1000);
        System.out.print("2 ");
        GameMechanics.sleep(1000);
        System.out.print("1\n");
        GameMechanics.sleep(1000);

        int coin = rand.nextInt();

        if (coin > 250) {
            System.out.printf("%s starts!\n", playerOne.getName());
        } else {
            Player.togglePlayer();
            System.out.printf("%s starts!\n", playerTwo.getName());
        }

        GameMechanics.sleep(3000);

        GameMechanics.clearScreen(OS);
    }

   /**
    * Display the scores ingame
    * 
    * @param playerOne Player 1 Object
    * @param playerTwo Player 2 Object
    */
    private void displayScores(Player playerOne, Player playerTwo) {

        System.out.printf("%s's score: %d\n", playerOne.getName(), playerOne.getScore());
        System.out.printf("%s's score: %d\n", playerTwo.getName(), playerTwo.getScore());

        if (playerOne.getScore() > playerTwo.getScore()) {
            System.out.printf("%s Wins!", playerOne.getName());
            Highscore.add(playerOne.getName(), playerOne.getScore());
        } else if (playerTwo.getScore() > playerOne.getScore()) {
            System.out.printf("%s Wins!", playerTwo.getName());
            Highscore.add(playerTwo.getName(), playerTwo.getScore());
        } else {
            System.out.println("Draw!");
        }
        System.out.println();
    }

   /**
    * Converts the bestmove for suggestion
    * 
    * @param bestMove Best move before conversion
    * @return int Best move after conversion
    */
    private int getBestMoveForSuggestion(int bestMove, int holeCount) {
        bestMove++;

        if (!(bestMove >= 1 && bestMove <= holeCount)) {
            bestMove = 1;
        }

        return bestMove;
    }
   /**
    * Decides on when to display the suggested move depending
    * depending on the flag passed into the method
    * 
    * @param suggestedMoveFlag Flag
    * @param bestMove Best move as an integer
    */
    private void suggestedMoveDisplay(int suggestedMoveFlag, int bestMove) {

        switch(suggestedMoveFlag) {
            // -1 : Do not display
            case -1:
                break;

            // 0 : For both players
            case 0:
                System.out.println("Suggested Move: " + bestMove);
                break;

            // 1 : For Player 1
            case 1:
                if (Player.getCurrentPlayer() == 1) {
                    System.out.println("Suggested Move: " + bestMove);
                }
                break;

            // 2 : For Player 2 
            case 2:
                if (Player.getCurrentPlayer() == 2) {
                    System.out.println("Suggested Move: " + bestMove);
                }
                break;

            // 3 :
            case 3:
                break;
        }
    }
}

