package ca.cmpt213.a2.ui;

import ca.cmpt213.a2.model.GameBoard;
import ca.cmpt213.a2.model.Hero;
import ca.cmpt213.a2.model.Monster;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Represents the UI Menu for the maze game. The prime
 * purpose of this UI is to display information about the
 * game to the Player. Uses a few different objects, such
 * as GameBoard and IMazeDisplayer inherited objects to
 * gather relevant information for the Player.
 *
 * @author Jaskaran Dhanoa | 301370242 | jsdhanoa@sfu.ca
 * @since 2020-06-21
 *
 * @see GameBoard
 * @see IMazeDisplayer
 * @see VisibleMazeDisplayer
 * @see HiddenMazeDisplayer
 * */
public class Menu {
    private final GameBoard gameBoard;
    private final IMazeDisplayer visibleMazeDisplayer;
    private final IMazeDisplayer hiddenMazeDisplayer;
    private boolean cheatEnabled;

    public Menu(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
        this.visibleMazeDisplayer = new VisibleMazeDisplayer(gameBoard);
        this.hiddenMazeDisplayer = new HiddenMazeDisplayer(gameBoard);
        this.cheatEnabled = false;
    }

    /**
     * Shows the Menu UI for user interaction.
     * */
    public void show() {
        printInstructions();
        hiddenMazeDisplayer.print();
        printBasic();

        Scanner in = new Scanner(System.in);
        String inputAnswer;
        // this check variable is gonna be used to validating inputs
        boolean check;
        Hero seeIfAlive = gameBoard.getHero();
        ArrayList<Monster> seeMonsters = gameBoard.getMonsters();

        do {
            // gets user input
            inputAnswer = in.nextLine();

            // place answer of user input
            check = check(inputAnswer);
            if(!seeIfAlive.isAlive()) {
                visibleMazeDisplayer.print();
                System.out.println("You've been eaten. GAME OVER!!!");
                break;
            } else {
                if(seeMonsters.size() == 0 || (cheatEnabled && seeMonsters.size() == 2)) {
                    visibleMazeDisplayer.print();
                    System.out.println("GAME OVER!!!");
                    break;
                }
            }

            // if false keep trying till valid command
            while(!check) {
                System.out.print("Enter your move [WASD?]: ");
                inputAnswer = in.nextLine();
                check = check(inputAnswer);
            }

            // this will be changed to hidden when finishing of assignment
            // printMaze(gameBoard.getGameBoard());
            hiddenMazeDisplayer.print();

            // print menu again
            printBasic();
        } while((!inputAnswer.equals("end")));
    }

    /**
     * Validates the user input. This is always being called in the main loop.
     * */
    private boolean check(String input) {
        boolean val;
        // check to see if s direction input is sent
        if(input.equals("w") || input.equals("a") || input.equals("s") || input.equals("d")) {
            // check if the direction entered is valid
            val = checkDirection(input);
            // if valid move hero
            if(val) {
                moveHero(input);
                gameBoard.update();
            }

            return val;
        }
        // check to see if user wants map to be shown
        else if (input.equals("m")) {
            visibleMazeDisplayer.print();
        }
        // to print instructions again
        else if(input.equals("?")) {
            printInstructions();
        }
        // to use cheat code and kill of 2 monsters
        else if (input.equals("c")) {
            cheatEnabled = true;
            return true;
        }

        return false;
    }

    /**
     * Checks if the Hero can move in the directions
     * specified by the Player.
     *
     * @return  true if Hero can move in that direction,
     *          false otherwise
     * */
    private boolean checkDirection(String input) {
        int x = gameBoard.getHero().getXPosition();
        int y = gameBoard.getHero().getYPosition();
        switch (input) {
            case "w":
                if ((gameBoard.getMaze().isWallAt(y - 1, x))) {
                    System.out.println("Cannot go through wall");
                    return false;
                }

                break;
            case "a":
                if ((gameBoard.getMaze().isWallAt(y, x - 1))) {
                    System.out.println("Cannot go through wall");
                    return false;
                }

                break;
            case "s":
                if ((gameBoard.getMaze().isWallAt(y + 1, x))) {
                    System.out.println("Cannot go through wall");
                    return false;
                }

                break;
            case "d":
                if ((gameBoard.getMaze().isWallAt(y, x + 1))) {
                    System.out.println("Cannot go through wall");
                    return false;
                }

                break;
        }

        return true;
    }

    /**
     * Sends move commands to the Hero
     * */
    private void moveHero(String input){
        int x = gameBoard.getHero().getXPosition();
        int y = gameBoard.getHero().getYPosition();
        switch (input) {
            case "w":
                gameBoard.getMaze().changecell(y, x);
                gameBoard.getHero().moveUp();
                break;

            case "a":
                gameBoard.getMaze().changecell(y, x);
                gameBoard.getHero().moveLeft();
                break;

            case "d":
                gameBoard.getMaze().changecell(y, x);
                gameBoard.getHero().moveRight();
                break;

            case "s":
                gameBoard.getMaze().changecell(y, x);
                gameBoard.getHero().moveDown();
                break;
        }
    }

    /**
     * Prints game instructions to the Player.
     * */
    private void printInstructions() {
        System.out.println("DIRECTIONS:");
        System.out.println("    Kill 3 Monsters!");
        System.out.println("LEGEND:");
        System.out.println("    #: Wall");
        System.out.println("    @: You (a hero)");
        System.out.println("    !: Monster");
        System.out.println("    $: Power");
        System.out.println("    .: Unexplored space");
        System.out.println("    MOVES:");
        System.out.println("Use W (up), A (left), S (down) and D (right) to move.");
        System.out.println("    (You must press enter after each move).");
    }

    /**
     * Prints the basic instructions after every Hero move.
     * */
    private void printBasic() {
        int monstersAlive = getMonstersAlive();

        if(cheatEnabled) {
            System.out.println("Total number of monsters to be killed: 1");
        } else {
            System.out.println("Total number of monsters to be killed: 3");
        }

        System.out.println("Number of powers currently in possession:" + gameBoard.getHero().getNumberOfPowerUps());
        System.out.println("Number of monsters alive:" + monstersAlive);
        System.out.print("Enter your move [WASD?]: ");
    }

    /**
     * Gets the number of monsters that are currently alive.
     * */
    private int getMonstersAlive() {
        return gameBoard.getMonsters().size();
    }
}
