# Mancala

Mancala is a game of Malay origin played primarily in South-east Asia. This is a slightly different version of the game since its based on an assignment from my University. The game is developed completely using the [Java Programming Language](https://en.wikipedia.org/wiki/Java_(programming_language)). 

## Getting Started

To run the program, locate to Source code/congkak/src/ using Command Prompt or Terminal. Run the command 'javac com/congkak/Main.java && java com/congkak/Main' to start the game.

### Prerequisites

Make sure to have the java compiler added to your path.

## Game Rules

Before the game start, player can choose the size of the board in multiple of two and decided how many seed in each hole to begin with. 

The moves that the user can select are the numbers below the holes(1-10). If its player 1 turn, the player has to choose a move on the bottom whereas player 2 chooses a move on the top. 

Each player can select the seed from hole 1 to 10. If a hole is empty, that hole cannot be selected. Upon selecting a hole, all the seed from the selected hole will be added to the subsequent hole each at a time until it reaches another empty hole and the seed next to the emtpy hole will be added to the player's home as their score. If it does not reach an empty hole, the seed from the next hole will be extracted and added to the subsequent hole again. The player with the most score wins !

### Options

If the user wants to disable animations(sleep times), they can change it within the source code.

To disable animations:
  1. Go to file com/congkak/mechanics/GameMechanics.java
  2. Replace line 44 and 57 with "Thread.sleep(0);".
  3. Animations are now disabled.
  4. Compile and run the program
