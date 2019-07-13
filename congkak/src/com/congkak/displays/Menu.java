package com.congkak.displays;

import com.congkak.objects.Options;
import com.congkak.mechanics.GameMechanics;
import com.congkak.mechanics.Highscore;


import java.util.*;

/**
 * <h1>Menu</h1>
 * A Menu Object
 *
 * @author  Lishan Abbas
 * @author  Hans Maulloo
 * @since   6/12/17
 */
public class Menu {

    private static final String OS = System.getProperty("os.name");
    private static final String CONGKAK = "   ____                  _         _    \n / ___|___  _ __   __ _| | ____ _| | __\n| |   / _ \\| '_ \\ / _` | |/ / _` | |/ /\n| |__| (_) | | | | (_| |   < (_| |   <\n \\____\\___/|_| |_|\\__, |_|\\_\\__,_|_|\\_\\\n                  |___/";

    private static int gameType;
    private static int boardSize;
    private static int holeCount;
    private static int boardBorders;
    private static int seedCount;
    private static String playerOneName;
    private static String playerTwoName;
    private boolean back = false;
    private int choice;
    private int aboutChoice;

    /**
     * Displays Main Menu for Players
     */
    public void mainMenu() {
        clearScreen(OS);
        System.out.println(CONGKAK);  
        System.out.println("      +-------------------------+      ");
        System.out.println("      |        MAIN MENU        |      ");
        System.out.println("      +-------------------------+      ");
        System.out.println("      | 1 Play                  |      ");
        System.out.println("      | 2 About                 |      ");
        System.out.println("      | 3 Highscores            |      ");
        System.out.println("      | 4 Exit                  |      ");
        System.out.println("      +-------------------------+      ");
        System.out.println();
    }

    /**
     * Prompts users for Game properties.
     *
     * @return options Options for the game
     */
    public Options start() {
        Scanner userChoice = new Scanner(System.in);
        Scanner gameT = new Scanner(System.in);
        while (choice!=1){
            if(!back){
                mainMenu();
                System.out.print("Enter choice: ");
                choice = userChoice.nextInt();
                clearScreen(OS);
            }
            gameType = 10;
            back = false;
            
            switch (choice) {
                case 1:
                    while ((gameType < 1) || (gameType > 4)){
                        System.out.println(CONGKAK);  
                        System.out.println("      +-------------------------+      ");
                        System.out.println("      |     Select Game Type    |      ");
                        System.out.println("      +-------------------------+      ");
                        System.out.println("      | 1 Basic                 |      ");
                        System.out.println("      | 2 Intermediate          |      ");
                        System.out.println("      | 3 Advanced              |      ");
                        System.out.println("      | 4 Back                  |      ");
                        System.out.println("      +-------------------------+      ");
                        System.out.println();
                        System.out.print("Enter Game Type: ");
                        gameType = gameT.nextInt();
                        switch(gameType){
                            case 1:
                            clearScreen(OS);
                            System.out.println("Game Type: Basic");
                            System.out.println();
                            playerNameInput();
                            return new Options(1, 7, 4, playerOneName, playerTwoName); 

                            case 2:
                            clearScreen(OS);
                            System.out.println("Game Type: Intermediate");
                            System.out.println();
                            boardSetupInput();
                            System.out.println();
                            playerNameInput();
                            return new Options(2, boardSize, seedCount, playerOneName, playerTwoName); 

                            case 3:
                            clearScreen(OS);
                            System.out.println("Game Type: Advanced");
                            System.out.println();
                            boardSetupInput();
                            System.out.println();
                            playerNameInput();
                            return new Options(3, boardSize, seedCount, playerOneName, playerTwoName); 

                            case 4: 
                            choice = 10;
                            gameType = 4;
                            back = false;
                            break;

                            default:
                            System.err.println("Error: Invalid Input");
                        }
                        clearScreen(OS);
                    }
                    break;

                case 2:
                    int aboutChoice = 0;
                    while(aboutChoice != 1){
                            System.out.println("+------------------------------------+");
                            System.out.println("|             ABOUT GAME             |");
                            System.out.println("+------------------------------------+");
                            System.out.println("| 1 Lishan Abbas                     |");
                            System.out.println("| 2 Yap Jia Yung                     |");
                            System.out.println("| 3 Daniel Jedidiah Antonio          |");
                            System.out.println("| 4 Hans Maulloo                     |");
                            System.out.println("|                                    |");
                            System.out.println("| For :                              |");
                            System.out.println("|                                    |");
                            System.out.println("|    Object-Oriented Programming F   |");
                            System.out.println("|             (PRG 1203)             |");
                            System.out.println("|                  &&                |");
                            System.out.println("|    Data Structures & Algorithms    |");
                            System.out.println("|             (CSC 2103)             |");
                            System.out.println("|                                    |");
                            System.out.println("|   PRESS 1 to RETURN to MAIN MENU   |");
                            System.out.println("|                                    |");
                            System.out.println("+------------------------------------+");
                            System.out.println();
                            System.out.print("Return to Main Menu? (1 = Yes, 2 = No): ");
                            Scanner aboutChoicesc = new Scanner(System.in);
                            aboutChoice = aboutChoicesc.nextInt();

                            switch(aboutChoice){
                                case 1:
                                    clearScreen(OS);
                                    choice = 10;
                                    gameType = 4;
                                    back = false;
                                    break;

                                default:
                                    clearScreen(OS);
                            }
                    }
                    break;

                case 3: 
                    int highscorechoice = 1;
                    while ((highscorechoice > 0) && (highscorechoice < 3)){
                        System.out.println(CONGKAK);  
                        System.out.println("      +-------------------------+      ");
                        System.out.println("      |       HIGH SCORES       |      ");
                        System.out.println("      +-------------------------+      ");
                        System.out.println("      | 1 View                  |      ");
                        System.out.println("      | 2 Reset                 |      ");
                        System.out.println("      | 3 Back                  |      ");
                        System.out.println("      +-------------------------+      ");
                        System.out.println();
                        System.out.print("Enter choice: ");
                        Scanner highscoresc = new Scanner(System.in);
                        highscorechoice = highscoresc.nextInt();
                        switch(highscorechoice){
                            case 1: 
                                clearScreen(OS);
                                Highscore.printHighscoresSorted();
                                GameMechanics.sleep(3000);
                                System.out.println();
                                System.out.println();
                                System.out.println();
                                break;

                            case 2:
                                System.out.print("Are you sure? (1 = Yes, 2 = No): ");
                                highscorechoice = highscoresc.nextInt();

                                clearScreen(OS);

                                if (highscorechoice == 1) {
                                    Highscore.resetScoresInFile();
                                    System.out.println();
                                    System.out.println();
                                    System.out.println();
                                }
                                break;

                            case 3:
                                clearScreen(OS);
                                choice = 10;
                                gameType = 4;
                                back = false;
                                break;

                        }
                    }
                    break;

                case 4:
                    System.out.println("Good bye!");
                    System.exit(0);
                    break;
            }
            if(back){
                choice = 99;
            }
        }

        return new Options();
    }

    /**
     * Clears Screen after each menu
     */
    private static void clearScreen(String operatingSystem) {

        Runtime runtime = Runtime.getRuntime();

        //Checks for the operating system and then clear the scree
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
     * Inputs the name for player 1 and player 2
     */
    private static void playerNameInput() {
        Scanner userInput = new Scanner(System.in);

        System.out.print("Player 1's Name: ");
        playerOneName = userInput.nextLine();
        System.out.print("Player 2's Name: ");
        playerTwoName = userInput.nextLine();

    }

    /**
     * Inputs the board size and seed count for the game
     */
    private static void boardSetupInput() {
        Scanner userInput = new Scanner(System.in);

        System.out.print("Enter Board Size: ");
        boardSize = userInput.nextInt();
        System.out.print("Enter Seed Count: ");
        seedCount = userInput.nextInt();
    }
}
