package ca.cmpt213.a2.model;

/**
 * Represents an object that can be placed at
 * some x,y position on the GameBoard. This class
 * essentially represents an objects position in
 * 2D space.
 *
 * @author Sean Dutton-Jones | 301360968 | sddutton@sfu.ca
 * @since 2020-06-20
 * */
public abstract class Placeable {
    int xPosition;
    int yPosition;

    public int getXPosition() {
        return xPosition;
    }

    public int getYPosition() {
        return yPosition;
    }
}
