package com.congkak.objects;

/**
 * <h1>PlayerBot</h1>
 * A Player Object for manipulating bot players
 *
 * @author  Lishan Abbas
 * @since   6/23/17
 */
public class PlayerBot extends Player {

   /**
    * Name is fixed to "Bot"
    */
    private final String name;

   /**
    * Constructor
    *
    * <p>Note: Constructs the Bot Player Object for the given score</p>
    * 
    * @param score Score
    */
    public PlayerBot(int score) {
        super("Bot");

        this.name = "Bot";
        this.score = score;
        this.id = Player.getCurrentPlayer();
    }

   /**
    * Checks whether this Player is a Bot or not
    * 
    * @return boolean Whether this Player is a Bot or not
    */
    public boolean isBot() {
        if (this.name.equals("Bot")) {
            return true;
        } else {
            return false;
        }
    }

}
