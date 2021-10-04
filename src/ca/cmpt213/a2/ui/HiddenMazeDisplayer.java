package ca.cmpt213.a2.ui;

import ca.cmpt213.a2.model.GameBoard;
import ca.cmpt213.a2.model.Hero;

/**
 * Displays the RandomDepthFirstSearchMaze in increments
 * according to the Hero's position. For each Hero move,
 * a square of 8 cells around the Hero are revealed. This
 * class offers one method, which is inherited from IMazeDisplayer
 * to print the maze.
 *
 * @author Jaskaran Dhanoa | 301370242 | jsdhanoa@sfu.ca
 * @since 2020-06-21
 *
 * @see IMazeDisplayer
 * @see Hero
 * @see GameBoard
 * */
public class HiddenMazeDisplayer implements IMazeDisplayer {
    private final GameBoard gameBoard;
    private final int[][] onesmaze;

    public HiddenMazeDisplayer(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
        this.onesmaze = new int[15][20];
        copyMaze(onesmaze, gameBoard.getGameBoard());
    }

    /**
     * Copies maze to ones maze.
     * */
    private void copyMaze(int[][] onesmaze, int[][] maze) {
        for(int i = 0; i < maze.length; i++) {
            for(int j = 0; j < maze[i].length; j++) {
                if(i == 0 || i == 14) { // copies left and right walls
                    onesmaze[i][j] = maze[i][j];

                } else if(j == 0 || j == 19) { // copies top and bottom walls
                    onesmaze[i][j] = maze[i][j];

                } else {
                    onesmaze[i][j] = 100;
                }
            }
        }
    }

    /**
     * Prints the maze as hidden, excluding the outer walls.
     * Only the parts that the Hero has walked through become
     * visible. All monsters and powerups remain visible even
     * though the maze is still hidden.
     * */
    public void print() {
        Hero find = gameBoard.getHero();

        int findx = find.getYPosition();
        int findy = find.getXPosition();

        int[][] maze = gameBoard.getGameBoard();

        // finding all of the cells around the player to reveal
        onesmaze[findx][findy] = 1;
        onesmaze[findx - 1][findy] = 1;
        onesmaze[findx - 1][findy - 1] = 1;
        onesmaze[findx - 1][findy + 1] = 1;
        onesmaze[findx + 1][findy] = 1;
        onesmaze[findx + 1][findy + 1] = 1;
        onesmaze[findx + 1][findy - 1] = 1;
        onesmaze[findx][findy - 1] = 1;
        onesmaze[findx][findy + 1] = 1;

        // printing the maze with human readable symbols
        for(int i = 0; i < maze.length; i++) {
            for(int j = 0; j < maze[i].length; j++) {
                if(onesmaze[i][j] == 1) {
                    if(maze[i][j] == 0) {
                        System.out.print(" ");
                    }

                    if(maze[i][j] == 1) {
                        System.out.print("#");
                    }

                    if(maze[i][j] == 2) {
                        System.out.print("@");
                    }

                    if(maze[i][j] == 3) {
                        System.out.print("!");
                    }

                    if(maze[i][j] == 4) {
                        System.out.print("$");
                    }

                    if(maze[i][j] == 5) {
                        System.out.print("X");
                    }

                } else if(onesmaze[i][j] == 100) {
                    if(maze[i][j] == 3) {
                        System.out.print("!");

                    } else if(maze[i][j] == 4) {
                        System.out.print("$");

                    } else {
                        System.out.print(".");
                    }
                }
            }

            System.out.println();
        }
    }
}