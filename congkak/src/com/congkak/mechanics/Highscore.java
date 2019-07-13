package com.congkak.mechanics;

import java.util.Collections;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.File;

/**
 * <h1>Highscore</h1>
 * To manipulate the highscore.txt file
 *
 * @author  Lishan Abbas
 * @since   6/25/17
 */
public class Highscore {

	private static File highscoreFile;
	private static String highscoreFilePath;
	private static boolean hasInstance;

	private static ArrayList<Score> scores;  

   /**
    * Constructor
    * 
    * Creates a new Highscore object
    * 
    * @param path Path of the highscore file
    */	
	public Highscore(String path) {
		try {
			hasInstance = true;
			highscoreFilePath = path;
			highscoreFile = new File(path);
			scores = new ArrayList<Score>();
			sortScores();

		} catch (Exception e) {
			System.out.println("Could not construct the highscore object");
			System.out.println("IOException: " + e.getMessage());
		}
	}

   /**
    * Adds a score into the highscore file
    * 
    * @param name Player Name
    * @param score Player Score
    */
	public static void add(String name, int score) {
		try {
		    String filename = highscoreFilePath;
		    FileWriter fileWriter = new FileWriter(filename, true);
		    fileWriter.write("\n" + name + " " + score);
		    fileWriter.close();
		} catch(Exception e) {
		    System.err.println("IOException: " + e.getMessage());
		}
	}

   /**
    * Fills the scores from the highscore file inside the score ArrayList
    */
	public static void fillScoresInsideArray() {
	    scores.clear();
		try {
			File file = highscoreFile;

			Scanner scanner = new Scanner(file);

			ArrayList<String> names = new ArrayList<String>();
			ArrayList<Integer> values = new ArrayList<Integer>();

			String temp = "";
			while (scanner.hasNextLine()) {
				if (!scanner.hasNextInt()) {
					temp += scanner.next() + " ";
				} else {
					scores.add(new Score(temp, scanner.nextInt()));
					temp = "";
				}
			}

			for (int i = 0; i < names.size(); i++) {
				scores.add(new Score(names.get(i), values.get(i)));
			}

		} catch (Exception e) {
		}
	}

   /**
    * Prints the contents of highscore file
    * <p>Note: Not sorted </p>
    */
	public static void printHighscores() {
		fillScoresInsideArray();

		for (int i = 0; i < 23; i++) {
            if (i % 2 == 0) System.out.print("+ ");
            else System.out.print("- ");
        }
        System.out.println();

		System.out.println("|                Highscores!                |");

	    for (int i = 0; i < 23; i++) {
            if (i % 2 == 0) System.out.print("+ ");
            else System.out.print("- ");
        }
        System.out.println();

        int count = 1;

		for (Score score : scores) {

            System.out.format("| %3d. %-25s :    %-4d  |\n", count, score.getPlayerName(), score.getValue());

            for (int i = 0; i < 23; i++) {
                if (i % 2 == 0) System.out.print("+ ");
                else System.out.print("- ");
            }
            System.out.println();
            count++;
        }
	}

   /**
    * Prints the contents of highscore file (sorted)
    */
	public static void printHighscoresSorted() {
		sortScores();

		for (int i = 0; i < 23; i++) {
            if (i % 2 == 0) System.out.print("+ ");
            else System.out.print("- ");
        }
        System.out.println();

		System.out.println("|                Highscores!                |");

	    for (int i = 0; i < 23; i++) {
            if (i % 2 == 0) System.out.print("+ ");
            else System.out.print("- ");
        }
        System.out.println();

        int count = 1;

		for (int c = scores.size() - 1; c >= 0; c--) {

            System.out.format("| %3d. %-25s :    %-4d  |\n", count, scores.get(c).getPlayerName(), scores.get(c).getValue());

            for (int i = 0; i < 23; i++) {
                if (i % 2 == 0) System.out.print("+ ");
                else System.out.print("- ");
            }
            System.out.println();
            count++;
        }
	}

   /**
    * Resets the scores in highscores.txt
    */
	public static void resetScoresInFile() {

		try {
			PrintWriter writer = new PrintWriter(highscoreFile);
			writer.print("Testscore -999");
			writer.close();
			System.out.println("Highscore file is resetted!");
			GameMechanics.sleep(3000);
		} catch (Exception e) {
			System.out.println("Cannot reset the scores");
			System.err.println("Exception: " + e.getMessage());
		}
	}

   /**
    * Sorts the values inside the static ArrayList<Score> scores
    */
	private static void sortScores() {
		fillScoresInsideArray();
	    quicksort(scores, 0, scores.size() - 1);
	}

   /**
    * Uses quicksort to sort an ArrayList with Score objects inside it
    * 
    * @param arrayList The ArrayList containing Score objects
    * @param from Where to start the sort
    * @param to Where to finish the sort
    */
	private static void quicksort(ArrayList<Score> arrayList, int from, int to) {
	    if (from < to) {
	        int pivot = from;
	        int left = from + 1;
	        int right = to;
	        int pivotValue = arrayList.get(pivot).getValue();
	        while (left <= right) {
	            // left <= to -> limit protection
	            while (left <= to && pivotValue >= arrayList.get(left).getValue()) {
	                left++;
	            }
	            // right > from -> limit protection
	            while (right > from && pivotValue < arrayList.get(right).getValue()) {
	                right--;
	            }
	            if (left < right) {
	                Collections.swap(arrayList, left, right);
	            }
	        }
	        Collections.swap(arrayList, pivot, left - 1);
	        quicksort(arrayList, from, right - 1); // pivot
	        quicksort(arrayList, right + 1, to);   // pivot
	    }
	}
}