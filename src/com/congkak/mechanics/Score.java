package com.congkak.mechanics;

/**
 * <h1>Score</h1>
 * A Score Object that contains a player name and a score value
 *
 * @author  Lishan Abbas
 * @since   6/25/17
 */
public class Score {

	private final String playerName;
	private final int value;

   /**
    * Default Constructor
    * 
    * <p>Note: Only used for testing</p>
    */
	public Score() {
		this.playerName = "Test Player";
		this.value = 420;
	}

   /**
    * Main Constructor
    *
    * Constructs a Score object with the given name and the value
    * 
    * @param playerName Name of the scorer
    * @param value Score of the scorer
    */
	public Score(String playerName, int value) {
		this.playerName = playerName;
		this.value = value;
	}

   /**
    * @return String Gets the player name of the score
    */
	public String getPlayerName() { return this.playerName; }

   /**
    * @return int Gets the value of the score
    */
	public int getValue() { return this.value; }
}