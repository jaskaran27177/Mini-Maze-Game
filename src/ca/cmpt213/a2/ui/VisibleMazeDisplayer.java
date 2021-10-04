package ca.cmpt213.a2.ui;

import ca.cmpt213.a2.model.GameBoard;
import ca.cmpt213.a2.model.Hero;

/**
 * Displays the RandomDepthFirstSearchMaze. This
 * class offers one method, which is inherited from IMazeDisplayer
 * to print the maze at full visibility.
 *
 * @author Jaskaran Dhanoa | 301370242 | jsdhanoa@sfu.ca
 * @since 2020-06-21
 *
 * @see IMazeDisplayer
 * @see Hero
 * @see GameBoard
 * */
public class VisibleMazeDisplayer implements IMazeDisplayer {
    private final GameBoard gameBoard;

    VisibleMazeDisplayer(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    /**
     * Prints the GameBoards numbers as humans readable
     * symbols.
     * */
    public void print() {
        int[][] maze = gameBoard.getGameBoard();

        for(int i = 0; i < maze.length; i++) {
            for(int j = 0; j < maze[0].length; j++) {
                int cell = maze[i][j];
                switch (cell) {
                    case 0:
                        System.out.print(" ");
                        break;

                    case 1:
                        System.out.print("#");
                        break;

                    case 2:
                        System.out.print("@");
                        break;

                    case 3:
                        System.out.print("!");
                        break;

                    case 4:
                        System.out.print("$");
                        break;
                    case 5:
                        System.out.print("X");
                        break;
                }
            }

            System.out.println();
        }
    }
}
