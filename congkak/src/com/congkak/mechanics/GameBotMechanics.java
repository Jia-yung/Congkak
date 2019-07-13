package com.congkak.mechanics;
 
import com.congkak.objects.Hole;
import com.congkak.objects.Player;
import com.congkak.objects.Options;
import com.congkak.objects.PlayerBot;
 
import java.util.ArrayList;
import java.io.*;

/**
 * <h1>GameBotMechanics</h1>
 * The functions required for game management when using the bot
 * Main method is the getBestMove, that uses the rest of the methods
 * the output on the screen.
 *
 * @author  Lishan Abbas
 * @since   6/15/17
 */
public class GameBotMechanics extends GameMechanics { 

    private static Hole[] holesObject;
    private static int boardBorders;

   /**
    * Creates a deep copy of the Hole[] array Object
    * 
    * @param holesOriginal Holes Array Object
    * @return Hole[] Deep copy of the passed in Hole Array Object
    */
    private static Hole[] holesDeepCopy(Hole[] holesOriginal) {

        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bos);
            out.writeObject(holesOriginal);
            out.flush();
            out.close();

            // Create an input stream from the byte array and read a copy of the object back in.
            ObjectInputStream in = new ObjectInputStream(
            new ByteArrayInputStream(bos.toByteArray()));

            return (Hole[]) in.readObject();
        } catch (Exception e) {
            System.out.println("Deep Copy unsuccessful. ");
            System.err.println("Exception: " + e.getMessage());

            return holesOriginal;
        }
    }

   /**
    * Main :: Gets the best move for the player passed in
    * 
    * @param holes Holes Array Object
    * @param options Options Object
    * @param player PlayerBot Object inherited from Player
    * @return int The best next move for the passed in player
    */
    public static int getBestMove(Hole[] holes, Options options, PlayerBot player) {

        holesObject = holesDeepCopy(holes);
        boardBorders = options.getBoardBorders();

        int[] moves = getPlayableMovesArray(holes, options, player);

        int highestScore = Integer.MIN_VALUE;
        int playedMoveScore;
        int bestMove = 1;

        for (int i = 0; i < moves.length; i++) {

            holesObject = holesDeepCopy(holes);

            // Create a new BOT player inside the function: playMove, with the passed in player's score
            playedMoveScore = playMove(holesObject, options, new PlayerBot(player.getScore()), moves[i]);

            if (playedMoveScore > highestScore) {
                highestScore = playedMoveScore;
                bestMove = moves[i];
            }
        }

        return bestMove;
    }
  
   /**
    * Plays one move using the passed in
    * playedMove at the game state
    * based on the holes[] array 
    * 
    * @param holes Holes Array Object 
    * @param options Options Object
    * @param playerBOT PlayerBot Object inherited from Player
    * @param playedMove The move as an integer
    * @return int The score for the move played
    */
    private static int playMove(Hole[] holes, Options options, Player playerBOT, int playedMove) {
        boolean continueToMove = false;
        int currentHole = 0;
        int selectedIndex = 0;
        int seedCountAtHand = 0;

        playerBOT.setId(Player.getCurrentPlayer());

        do {

            if (!continueToMove) {

                // Bot Move - !shouldn't be in an offset state
                selectedIndex = playedMove;

                // Get the selected holes index's value
                seedCountAtHand = holes[selectedIndex].getValue();

            } else {
                // Continued Move
                selectedIndex = GameMechanics.continuedMove(options, currentHole);
                seedCountAtHand = holes[selectedIndex].getValue();
            }

            // Clear the selected hole's value
            holes[selectedIndex].clear();

            //seeds from selected hole will be distributed to neighbouring holes and current hole will be increment for each distribution
            int passedInCurrentHole = 0;
            for (int i = 0; i < seedCountAtHand; i++) {
                if (selectedIndex + i > options.getBoardSize() - 1) {
                    selectedIndex -= options.getBoardSize();
                    holes[selectedIndex + i].incrementValue();
                } else {
                    holes[selectedIndex + i].incrementValue();
                }
                passedInCurrentHole = selectedIndex + i;
            }
            currentHole = passedInCurrentHole;

            int check;

            if (currentHole + 1 > options.getBoardSize() - 1) {
                check = holes[currentHole + 1 - options.getBoardSize()].getValue();
            } else {
                check = holes[currentHole + 1].getValue();
            }

            if (check == 0) {
                continueToMove = false;
            } else {
                continueToMove = true;
            }

            if (!continueToMove) {

                // Obtain the score
                int score;
                int boardSize = options.getBoardSize();

                if (currentHole + 2 > boardSize - 1) {
                    if (playerBOT.getId() == 1) {
                        playerBOT.setScoreIncrement(holes[currentHole + 2 - boardSize].getValue());
                        holes[currentHole + 2 - boardSize].clear();
                    }
                } else {
                    if (playerBOT.getId() == 2) {
                        playerBOT.setScoreIncrement(holes[currentHole + 2].getValue());
                        holes[currentHole + 2].clear();
                    }
                }

                score = playerBOT.getScore();
                playerBOT.setScore(score);
            }

            int count = 0;
            int boardBorders = options.getBoardBorders();
            int boardSize = options.getBoardSize();
            boolean flag = true;

            for (int i = 0; i < boardBorders; i++) {
                if (holes[i].isEmpty()) {
                    count ++;
                }
                if (count == boardBorders && (playerBOT.getId() == 1)) {
                    flag = false;
                }
            }

            count = 0;
            for (int i = boardSize - 1; i >= boardBorders; i--) {
                if (holes[i].isEmpty()) {
                    count ++;
                }
                if (count == boardBorders && (playerBOT.getId() == 2)) {
                    flag = false;
                }
            }

            boolean checkLegalMove = flag;

            if (checkLegalMove) {

                int leftOverSeeds = 0;
                boardSize = options.getBoardSize();
                boardBorders = options.getBoardBorders();

                if(playerBOT.getId() == 1) {
                    for (int i = boardSize - 1; i >= boardBorders; i--) {
                        playerBOT.setScoreIncrement(holes[i].getValue());
                        leftOverSeeds += holes[i].getValue();
                        holes[i].clear();

                    }
                } else if(playerBOT.getId() == 2) {
                    for(int i = 0; i < boardBorders; i++){
                        playerBOT.setScoreIncrement(holes[i].getValue());
                        leftOverSeeds += holes[i].getValue();
                        holes[i].clear();
                    }
                }
                leftOverSeeds = playerBOT.getScore();
                playerBOT.setScore(leftOverSeeds);
            }

        } while (continueToMove);

        return playerBOT.getScore();
    }
  
   /**
    * Gets the playable moves for the player passed in
    * 
    * @param holes Holes Array Object
    * @param options Options Object
    * @param player PlayerBot Object inherited from Player
    * @return int[] The playable moves as an int array
    */
    private static int[] getPlayableMovesArray(Hole holes[], Options options, PlayerBot player) {

        if (!player.isBot()) {
            player.setId(Player.getCurrentPlayer());
        }
        int move = 1;
        ArrayList<Integer> playableMoves = new ArrayList<Integer>();

        for (int i = 0; i < options.getBoardBorders(); i++) {
            if (player.getId() == 2) {
                move = GameMechanics.offsetMoves(move, options);
            }

            if (validateBotMove(holes, options, move, player)) {
                playableMoves.add(i + 1);
            }
            move = i+1;

            move++;

        }

        return arrayListToArray(playableMoves);
    }

   /**
    * Validate the move made by the bot
    * 
    * @param holes Holes Object
    * @param options Options Object
    * @param currentIndex Current move index
    * @param playerBOT PlayerBot Object
    * @return boolean Whether the move is valid or not
    */
    public static boolean validateBotMove(Hole[] holes, Options options, int currentIndex, PlayerBot playerBOT) {

        int boardBorders = options.getBoardSize() / 2;

        int moveIndex = currentIndex;

        if (
            playerBOT.getId() == 1 &&
            moveIndex >= 0 &&    
            moveIndex < boardBorders &&
            !holes[moveIndex].isEmpty()
        ) return true;

        else if (
            playerBOT.getId() == 2 &&
            moveIndex >= boardBorders &&
            moveIndex < options.getBoardSize() &&
            !holes[moveIndex].isEmpty()
        ) return true;

        else return false;
    }
  
   /**
    * Converts an ArrayList<Integer> to a normal int[] array
    * 
    * @param arrayList ArrayList Object
    * @return int[] int[] array
    */
    private static int[] arrayListToArray(ArrayList<Integer> arrayList) {
        int[] array = new int[arrayList.size()];

        for (int i = 0; i < arrayList.size(); i++) {
            array[i] = arrayList.get(i);
        }
        return array;
    }
}
