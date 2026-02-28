package sttrj.game;

/**
 * Star is a relatively inert Entity in a Quadrant. 
 * Stars do not move while you are in a Quadrant, 
 * but they do move if you leave the Quadrant and then come back later. 
 * They cannot be destroyed and nothing can fly through a star. 
 * Stars start already scanned as it is pretty hard to not detect a Star in a Quadrant.
 */
public class Star extends Entity {
    /**
     * The symbol of the Star.
     */
    private String symbol = " * ";

    /**
     * Constructs a Star instance at the given coordinates.
     * 
     * @param x - horizontal coordinate
     * @param y - vertical coordinate
     */
    public Star(int x, int y) {
        super(x, y);
        //set the symbol
        this.setSymbol(symbol);
        //the star is already scanned
        this.scan();
    }
}
