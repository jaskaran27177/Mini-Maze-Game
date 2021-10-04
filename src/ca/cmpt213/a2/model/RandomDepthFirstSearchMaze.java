package ca.cmpt213.a2.model;

import java.util.Random;

/**
 * Represents a 2D maze. The prime purpose of this
 * class is to construct a cyclical maze using the Random
 * Depth-first Search algorithm. To add cycles, an
 * additional algorithm removes five walls from carefully
 * selected areas. This class offers a small interface
 * for interacting with the maze.
 *
 * @author Sean Dutton-Jones | 301360968 | sddutton@sfu.ca
 * @author Jaskaran Dhanoa | 301370242 | jsdhanoa@sfu.ca
 * @since 2020-06-20
 * */
public class RandomDepthFirstSearchMaze {
    private static final int NUMBER_OF_REMOVED_WALLS = 5;
    private final int WIDTH;
    private final int HEIGHT;
    private final int[][] maze;

    public RandomDepthFirstSearchMaze(int width, int height) {
        WIDTH = width;
        HEIGHT = height;

        // initialize maze to all walls
        maze = new int[HEIGHT][WIDTH];
        for(int i = 0; i < HEIGHT; i++) {
            for(int j = 0; j < WIDTH; j++) {
                maze[i][j] = 1;
            }
        }
    }

    /**
     * Starts the generation process. The first step is
     * to create a random depth-first search maze with no
     * cycles, then add the cycles to the maze.
     * */
    public void generate(int startX, int startY) {
        // initialize starting point
        maze[startY][startX] = 0;

        // generate basic random Depth-first Search maze
        generateBasicMaze(startY, startX);

        // add cycles to the maze
        addCyclesToMaze();
    }

    /**
     * Generates a basic random depth-first search maze.
     * */
    private void generateBasicMaze(int y, int x) {
        int[] shuffledDirections = createShuffledDirections();

        for (int shuffledDirection : shuffledDirections) {
            if ((shuffledDirection == 1) && (y - 2 > 0)) { // left
                // if the cell 2 spaces left has not been visited
                if (maze[y - 2][x] != 0) {
                    // mark the cells up to 2 spaces left as visited
                    maze[y - 2][x] = 0;
                    maze[y - 1][x] = 0;
                    // recursive call
                    generateBasicMaze(y - 2, x);
                }
            } else if ((shuffledDirection == 2) && (y + 2 < HEIGHT - 1)) { // right
                // if the cell 2 spaces right has not been visited
                if (maze[y + 2][x] != 0) {
                    // mark the cells up to 2 spaces right as visited
                    maze[y + 2][x] = 0;
                    maze[y + 1][x] = 0;
                    // recursive call
                    generateBasicMaze(y + 2, x);
                }
            } else if ((shuffledDirection == 3) && (x - 2 > 0)) { // up
                // if the cell 2 spaces up has not been visited
                if (maze[y][x - 2] != 0) {
                    // mark the cells up to 2 spaces up as visited
                    maze[y][x - 2] = 0;
                    maze[y][x - 1] = 0;
                    // recursive call
                    generateBasicMaze(y, x - 2);
                }
            } else if (shuffledDirection == 4) { // down
                if (x + 2 >= WIDTH - 1) {
                    // if the position is only one space away from an outer wall
                    if (x + 2 == WIDTH - 1) {
                        // then visit that space
                        if (maze[y][x + 1] != 0) {
                            maze[y][x + 1] = 0;
                        }
                    }

                    continue;
                }

                // if the cell 2 spaces down has not been visited
                if (maze[y][x + 2] != 0) {
                    // mark the cells up to 2 spaces down as visited
                    maze[y][x + 2] = 0;
                    maze[y][x + 1] = 0;
                    // recursive call
                    generateBasicMaze(y, x + 2);
                }
            }
        }
    }

    /**
     * Adds the cycles to the maze by checking a random
     * wall to see if it can be removed. If it can be removed
     * without creating a 2x2 area of no walls, then it is removed.
     * Otherwise a new wall is selected and checked. It will remove
     * exactly NUMBER_OF_REMOVED_WALLS before finishing.
     * */
    private void addCyclesToMaze() {
        Random random = new Random();
        int removedCounter = 0;
        int randomColumn;
        int randomRow;

        while(removedCounter < NUMBER_OF_REMOVED_WALLS) {
            // grab a random cell
            randomColumn = random.nextInt(18) + 1;
            randomRow = random.nextInt(13) + 1;

            // if the random cell is a wall
            if(maze[randomRow][randomColumn] == 1) {
                // then check if the wall can be removed without causing a 2x2 open space
                boolean isValidForRemoval = validTopLeftRegion(randomRow, randomColumn)
                        && validTopRightRegion(randomRow, randomColumn)
                        && validBottomLeftRegion(randomRow, randomColumn)
                        && validBottomRightRegion(randomRow, randomColumn);

                // if the wall can be removed
                if(isValidForRemoval) {
                    // removed the wall
                    maze[randomRow][randomColumn] = 0;
                    removedCounter++;
                }
            }
        }
    }

    /**
     * @return  true if the top left cell is a wall,
     *          false otherwise.
     * */
    private boolean validTopLeftRegion(int r, int c) {
        return maze[r - 1][c] == 1 || maze[r][c - 1] == 1 || maze[r - 1][c - 1] == 1;
    }

    /**
     * @return  true if the bottom left cell is a wall,
     *          false otherwise.
     * */
    private boolean validBottomLeftRegion(int r, int c) {
        return maze[r + 1][c] == 1 || maze[r][c - 1] == 1 || maze[r + 1][c - 1] == 1;
    }

    /**
     * @return  true if the top right cell is a wall,
     *          false otherwise.
     * */
    private boolean validTopRightRegion(int r, int c) {
        return maze[r - 1][c] == 1 || maze[r][c + 1] == 1 || maze[r - 1][c + 1] == 1;
    }

    /**
     * @return  true if the bottom right cell is a wall,
     *          false otherwise.
     * */
    private boolean validBottomRightRegion(int r, int c) {
        return maze[r + 1][c] == 1 || maze[r][c + 1] == 1 || maze[r + 1][c + 1] == 1;
    }

    /**
     * Creates a list of 4 different directions in a random
     * order.
     * */
    private int[] createShuffledDirections() {
        int[] directions = {1, 2, 3, 4};
        Random random = new Random();

        for(int i = 0; i < 2; i++) {
            int firstSelection = random.nextInt(4);
            int secondSelection = random.nextInt(4);

            // swap values
            int temp = directions[firstSelection];
            directions[firstSelection] = directions[secondSelection];
            directions[secondSelection] = temp;
        }

        return directions;
    }

    /**
     * @return the 2d integer array representing the maze.
     * */
    public int[][] getMaze() {
        int[][] mazeCopy = new int[HEIGHT][WIDTH];

        for(int i = 0; i < maze.length; i++) {
            System.arraycopy(maze[i], 0, mazeCopy[i], 0, maze[0].length);
        }

        return mazeCopy;
    }

    /**
     * @return  true is there is a wall at the c,r coordinate,
     *          false otherwise
     * */
    public boolean isWallAt(int r, int c) {
        return maze[r][c] == 1;
    }

    /**
     * Changes the cell determined by the x,y to zero.
     * */
    public void changecell(int x, int y){
        maze[x][y] = 0;
    }
}
