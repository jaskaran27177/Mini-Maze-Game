package ca.cmpt213.a2.model;

/**
 * Represents the Hero in the GameBoard. The Hero
 * extends the Placeable Abstract Class which allows
 * an object to be placed at some position on the GameBoard.
 * The Hero also keeps track of the number of powerups it has
 * collected and whether or not it is alive. This class also
 * provides methods for moving the Hero.
 *
 * @author Sean Dutton-Jones | 301360968 | sddutton@sfu.ca
 * @since 2020-06-20
 *
 * @see Placeable
 * @see GameBoard
 * */
public class Hero extends Placeable {
    private final GameBoard gameBoard;
    private int numberOfPowerUps;
    private boolean isAlive;

    public Hero(GameBoard gameBoard, int xPosition, int yPosition) {
        this.gameBoard = gameBoard;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.numberOfPowerUps = 0;

        this.isAlive = true;
    }

    public void moveLeft() {
        if(!(gameBoard.getMaze().isWallAt(yPosition, xPosition - 1))) {
            xPosition -= 1;
        }
    }

    public void moveRight() {
        if(!(gameBoard.getMaze().isWallAt(yPosition, xPosition + 1))) {
            xPosition += 1;
        }
    }

    public void moveUp() {
        if(!(gameBoard.getMaze().isWallAt(yPosition - 1, xPosition))) {
            yPosition -= 1;
        }
    }

    public void moveDown() {
        if(!(gameBoard.getMaze().isWallAt(yPosition + 1, xPosition))) {
            yPosition += 1;
        }
    }

    public void addOnePowerUp() {
        numberOfPowerUps += 1;
    }

    public void removeOnePowerUp() {
        numberOfPowerUps -= 1;
    }

    public void kill() {
        isAlive = false;
    }

    public int getNumberOfPowerUps() {
        return numberOfPowerUps;
    }

    public boolean isAlive() {
        return isAlive;
    }
}
