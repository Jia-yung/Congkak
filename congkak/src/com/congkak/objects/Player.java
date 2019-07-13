package com.congkak.objects;

/**
 * <h1>Player</h1>
 * A Player Object for manipulating players (core-object)
 *
 * @author  Lishan Abbas
 * @author  Hans Maulloo
 * @author  Daniel Jedidiah Antonio
 * @since   6/13/17
 */
public class Player {

   /**
    * Static variable that points at the current player
    */
    private static int currentPlayer = 1;
    
    private String name;
    protected int score;
    protected int id;

   /**
    * Constructor A
    * 
    * <p>Note: Constructs a Player with the default name</p>
    */
    public Player() {
        if (Player.getCurrentPlayer() == 1) {
            this.name = "P1";
        } else {
            this.name = "P2";
        }
        this.score = 0;
    }

   /**
    * Constructor B
    * 
    * <p>Note: Constructs a Player with the passed in name</p>
    * 
    * @param name Player Name
    */
    public Player(String name) {
        this.name = name;
        this.score = 0;
    }

   /**
    * Constructor C
    * 
    * <p>Note: Constructs a Player with the given name and score</p>
    * 
    * @param name Player Name
    * @param score Player Score
    */
    public Player(String name, int score) {
        this.name = name;
        this.score = score;
    }

   /**
    * Checks whether the current player is Player 1
    * 
    * @return boolean whether the current player is Player 1
    */
    public static boolean isPlayerOne() {
        if (currentPlayer == 1) return true;
        else return false;
    }

   /**
    * Checks whether the current player is Player 2
    * 
    * @return boolean whether the current player is Player 2
    */
    public static boolean isPlayerTwo() {
        if (currentPlayer == 2) return true;
        else return false;
    }

   /**
    * Increments the given score to the object's score 
    * 
    * @param score Score
    */
    public void setScoreIncrement(int score) {
        if (this.score <= 0) {
            this.score = 0;
        } else {
            this.score += score;
        }
    }

   /**
    * Toggles the static variable currentPlayer value between 1 and 2
    */
    public static void togglePlayer() {

        if (getCurrentPlayer() == 1) {
            setCurrentPlayer(2);
        } else {
            setCurrentPlayer(1);
        }
    }

   /**
    * @return int Gets the current player
    */
    public static int getCurrentPlayer() { return currentPlayer; }
    
   /**
    * @return String Gets the name of this player
    */
    public String getName() { return this.name; }
    
   /**
    * @return int Gets the score of this player
    */
    public int getScore() { return this.score; }
    
   /**
    * @return int Gets the id of this player
    */
    public int getId() { return this.id; }

   /**
    * @param newCurrentPlayer Sets the current player
    */
    public static void setCurrentPlayer(int newCurrentPlayer) { currentPlayer = newCurrentPlayer; }
    
   /**
    * @param name Sets the name of this player
    */
    public void setName(String name) { this.name = name; }
    
   /**
    * @param value Sets the score of this player
    */
    public void setScore(int score) { this.score = score; }
    
   /**
    * @param id Sets the id of this player
    */
    public void setId(int id) { this.id = id; }
}
