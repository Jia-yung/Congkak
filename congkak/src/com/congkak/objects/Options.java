package com.congkak.objects;

/**
 * <h1>Options</h1>
 * Options Object that cointains available options for the game
 *
 * @author  Lishan Abbas
 * @since   6/12/17
 */
public class Options {

    private int gameType;
    private int boardSize;
    private int holeCount;
    private int boardBorders;
    private int seedCount;
    private String playerOneName;
    private String playerTwoName;

   /**
    * Constructor A
    * 
    * Constructs a default Options Object with the following values
    * <p>
    * Game Type: (1)
    * Board Size: 14
    * Hole Count: 7
    * Board Borders: 14
    * Seed Count: 4
    * Player 1 Name: "P1"
    * Player 2 Name: "P2"
    * </p>
    */
    public Options() {
        this.gameType = 1;
        this.boardSize = 14;
        this.holeCount = 7;
        this.boardBorders = boardSize;
        this.seedCount = 4;
        this.playerOneName = "P1";
        this.playerTwoName = "P2";
    }

   /**
    * Constructor B
    *
    * Constructs an Option Object with custom options
    * 
    * @param gameType Type of the game
    * @param boardSize Size of the board
    * @param seedCount Seed count on each hole
    * @param gameMode PvP or PvB (Bot)
    * @param playerOneName Player 1 Name
    * @param playerTwoName Player 2 Name
    */
    public Options(int gameType, int boardSize, int seedCount, String playerOneName, String playerTwoName) {
        this.gameType = gameType;
        this.boardSize = boardSize * 2;
        this.holeCount = boardSize;
        this.boardBorders = boardSize;
        this.seedCount = seedCount;
        this.playerOneName = playerOneName;
        this.playerTwoName = playerTwoName;
    }

   /**
    * @return gameType Game Type as an Integer
    */
    public int getGameType() { return this.gameType; }

   /**
    * @return boardSize Size of the Board
    */    
    public int getBoardSize() { return this.boardSize; }

   /**
    * @return holeCount Hole Count of the Board
    */     
    public int getHoleCount() { return this.holeCount; }

   /**
    * @return boardBorders Board Borders
    */    
    public int getBoardBorders() { return this.boardBorders; }

   /**
    * @return seedCount Seed Count 
    */ 
    public int getSeedCount() { return this.seedCount; }

   /**
    * @return playerOneName Player 1 Name
    */ 
    public String getPlayerOneName() { return this.playerOneName; }

   /**
    * @return playerTwoName Player 2 Name
    */    
    public String getPlayerTwoName() { return this.playerTwoName; }


   /**
    * @param gameType returns Game Type
    */
    public void setGameType(int gameType) { this.gameType = gameType; }

   /**
    * @param boardSize returns Board Size
    */
    public void setBoardSize(int boardSize) { this.boardSize = boardSize; }

   /**
    * @param holeCount returns Hole Count
    */
    public void setHoleCount(int holeCount) { this.holeCount = holeCount; }

   /**
    * @param boardBorders return Board Borders
    */
    public void setboardBorders(int boardBorders) { this.boardBorders = boardBorders; }

   /**
    * @param seedCount return Seed Count
    */
    public void setSeedCount(int seedCount) { this.seedCount = seedCount; }

   /**
    * @param playerOneName return Player 1 Name
    */
    public void setPlayerOneName(String playerOneName) { this.playerOneName = playerOneName; }

   /**
    * @param playerTwoName return Player 2 Name
    */
    public void setPlayerTwoName(String playerTwoName) { this.playerTwoName = playerTwoName; }
}
