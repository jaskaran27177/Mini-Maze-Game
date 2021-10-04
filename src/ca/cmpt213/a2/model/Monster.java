package ca.cmpt213.a2.model;

import java.util.ArrayList;
import java.util.Random;

/**
 * Represents the Monster(s) in the GameBoard. The Monster
 * extends the Placeable Abstract Class which allows
 * an object to be placed at some position on the GameBoard.
 * The Monster keeps track of its last move. It also has a method
 * to determine its own psuedo random movements, since Monsters
 * are not controlled by the Player.
 *
 * @author Sean Dutton-Jones | 301360968 | sddutton@sfu.ca
 * @since 2020-06-20
 *
 * @see Placeable
 * @see GameBoard
 *
 * */
public class Monster extends Placeable {
    private final GameBoard gameBoard;
    private int backTrackMove;

    public Monster(GameBoard gameBoard, int xPosition, int yPosition) {
        this.gameBoard = gameBoard;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.backTrackMove = -1;
    }

    public void move() {
        ArrayList<Integer> moves = findValidMoves();
        int nextMove;

        moves.remove((Integer) backTrackMove);

        if(!(moves.isEmpty())) {
            Random random = new Random();
            int index = random.nextInt(moves.size());
            nextMove = moves.get(index);
        } else { // moves is empty
            nextMove = backTrackMove;
        }

        switch (nextMove) {
            case 0: // left
                moveLeft();
                backTrackMove = 1;
                break;
            case 1: // right
                moveRight();
                backTrackMove = 0;
                break;
            case 2: // up
                moveUp();
                backTrackMove = 3;
                break;
            case 3: // down
                moveDown();
                backTrackMove = 2;
                break;
        }
    }

    private ArrayList<Integer> findValidMoves() {
        ArrayList<Integer> moves = new ArrayList<>();
        // left
        if(!(gameBoard.getMaze().isWallAt(yPosition, xPosition - 1))) {
            moves.add(0);
        }

        // right
        if(!(gameBoard.getMaze().isWallAt(yPosition, xPosition + 1))) {
            moves.add(1);
        }

        // up
        if(!(gameBoard.getMaze().isWallAt(yPosition - 1, xPosition))) {
            moves.add(2);
        }

        // down
        if(!(gameBoard.getMaze().isWallAt(yPosition + 1, xPosition))) {
            moves.add(3);
        }

        return moves;
    }

    private void moveLeft() {
        xPosition -= 1;
    }

    private void moveRight() {
        xPosition += 1;
    }

    private void moveUp() {
        yPosition -= 1;
    }

    private void moveDown() {
        yPosition += 1;
    }
}
