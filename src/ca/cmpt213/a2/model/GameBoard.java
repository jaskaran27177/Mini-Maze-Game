package ca.cmpt213.a2.model;

import java.util.ArrayList;

/**
 * Represents a physical game board that could be used to
 * play this game. Holds all required objects for the functioning
 * of the game, such as RandomDepthFirstSearchMaze, Hero, Monsters
 * and a PowerUp. Exposes an interface to interact with the gameboard.
 *
 * @author Sean Dutton-Jones | 301360968 | sddutton@sfu.ca
 * @author Jaskaran Dhanoa | 301370242 | jsdhanoa@sfu.ca
 * @since 2020-06-20
 *
 * @see RandomDepthFirstSearchMaze
 * @see Hero
 * @see Monster
 * @see PowerUp
 * */
public class GameBoard {
    private final RandomDepthFirstSearchMaze maze;
    private final Hero hero;
    private final ArrayList<Monster> monsters;
    private PowerUp powerUp;

    public GameBoard() {
        maze = new RandomDepthFirstSearchMaze(20, 15);
        maze.generate(1, 1);

        hero = new Hero(this, 1, 1);

        monsters = new ArrayList<>();
        monsters.add(new Monster(this, 1, 13));
        monsters.add(new Monster(this, 18, 13));
        monsters.add(new Monster(this, 18, 1));

        powerUp = new PowerUp(this);
    }

    /**
     * Calls functions to check what has happend after the player
     * has moved the Hero.
     * */
    public void update() {
        checkPlayerAndMonsterCollision();
        checkPlayerAndPowerUpCollision();
    }

    /**
     * Checks if the Hero has picked up a powerup. If the Hero
     * has, then add a powerup and create a new powerup.
     * */
    private void checkPlayerAndPowerUpCollision() {
        if(hero.getXPosition() == powerUp.getXPosition() && hero.getYPosition() == powerUp.getYPosition()) {
            hero.addOnePowerUp();
            powerUp = new PowerUp(this);
        }
    }

    /**
     * Checks if the Hero has run into a Monster. If the Hero has,
     * and the Hero has a powerup, then kill the monster. Else if,
     * the Hero has no powerup then kill the player.
     * */
    private void checkPlayerAndMonsterCollision() {
        for(int i = 0; i < monsters.size(); i++) {
            Monster m = monsters.get(i);
            /*
            * IMPORTANT: this forloop enforces a pattern of check-move-check. This means
            * that we check to see if the player and monster are in the same cell. If not,
            * then we move the monster(this is it's first and only move). We then check once
            * more if the monster has now moved to the same cell as the player.
            * */
            for(int j = 0; j < 2; j++) {
                if(hero.getXPosition() == m.getXPosition() && hero.getYPosition() == m.getYPosition()) {
                    if(hero.getNumberOfPowerUps() > 0) {
                        monsters.remove(m);
                        hero.removeOnePowerUp();
                    } else {
                        hero.kill();
                    }

                    break;
                } else {
                    if(j == 0) {
                        m.move();
                    }
                }
            }
        }
    }

    public int[][] getGameBoard() {
        int[][] gameBoard = maze.getMaze();

        /*
        * The ordering of the below assignments are important because
        * they will render in this order. This ordering ensures that the
        * most important object will be rendered on top so that player can see.
        * ex. in the requirements, it says that a monster may hide a powerup
        * momentarily. This means that the monster should be rendered after the
        * powerup. Hence assigning the powerup first, and then the monsters below.
        * */
        gameBoard[powerUp.getYPosition()][powerUp.getXPosition()] = 4;

        for(Monster m : monsters) {
            gameBoard[m.getYPosition()][m.getXPosition()] = 3;
        }

        if(hero.isAlive()) {
            gameBoard[hero.getYPosition()][hero.getXPosition()] = 2;
        } else { // hero has died
            gameBoard[hero.getYPosition()][hero.getXPosition()] = 5;
        }

        return gameBoard;
    }

    public Hero getHero() {
        return hero;
    }

    public ArrayList<Monster> getMonsters() {
        return monsters;
    }

    public RandomDepthFirstSearchMaze getMaze() {
        return maze;
    }
}
