package com.congkak; 

import com.congkak.displays.Menu;
import com.congkak.mechanics.Game;
import com.congkak.mechanics.Highscore;
import com.congkak.objects.Options;

import java.util.Scanner;

/**
 * <h1>Main</h1>
 * Main class
 * Runs the game Congkak in the command line
 * Please run 'javac com/congkak/Main.java && java com/congkak/Main' 
 * on the command line at src to start the game
 *
 * @author  Lishan Abbas
 * @author  Yap Jia Yung
 * @author  Hans Maulloo
 * @author  Daniel Jedidiah Antonio
 * @since   6/11/17
 */
public class Main {

    private static Scanner userInput = new Scanner(System.in);

   /**
    * The main function for Congkak
    *
    * <p>Plays the game Congkak on command line</p>
    *
    * @param args Arguments for the command line
    */
    public static void main(String[] args) { 

        int choice;

        do {
            Menu menu = new Menu();
            Highscore highscore = new Highscore("com/congkak/resources/highscores.txt/");
            Options options = menu.start();

            Game game = new Game(options);

            game.startGame();

            System.out.print("Go Back to Main Menu? (1 = Yes, 2 = Exit Game): ");
            choice = userInput.nextInt();
        } while (choice == 1);

        System.out.println("Good bye!");
    }
}
