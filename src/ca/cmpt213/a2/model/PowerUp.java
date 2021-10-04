package ca.cmpt213.a2.model;

import java.util.Random;

/**
 * Represents the powerup in the GameBoard. The PowerUp
 * extends the Placeable Abstract Class which allows
 * an object to be placed at some position on the GameBoard.
 * On creation, the PowerUp determines its own placement using
 * information from the maze and player position.
 *
 * @author Sean Dutton-Jones | 301360968 | sddutton@sfu.ca
 * @since 2020-06-20
 *
 * @see Placeable
 * @see GameBoard
 * */
public class PowerUp extends Placeable {
    public PowerUp(GameBoard gameBoard) {
        // place itself in a valid position
        boolean validPosition = false;
        Random random = new Random();

        while(!validPosition) {
            int x = random.nextInt(gameBoard.getMaze().getMaze()[0].length - 2) + 1;
            int y = random.nextInt(gameBoard.getMaze().getMaze().length - 2) + 1;
            // if there is no wall at that position
            if(!(gameBoard.getMaze().isWallAt(y, x))) {
                // and if the Hero is not in the same position
                if(gameBoard.getHero().getXPosition() != x && gameBoard.getHero().getYPosition() != y) {
                    // then create the powerup at this position
                    xPosition = x;
                    yPosition = y;
                    validPosition = true;
                }
            }
        }
    }
}