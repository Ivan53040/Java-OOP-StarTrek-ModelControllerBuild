package sttrj.game;

/**
 * Entity is used to represent a physical object or 'thing' in the Game. 
 * It has x and y coordinates (an XyPair) that indicate where it is in a Quadrant. 
 * Every Entity has a String symbol consisting of exactly 3 characters. 
 * Some entities can be destroyed and are then removed from the Game. 
 * Entities are not detected until they have been scanned.
 */

public class Entity extends Object {
    /**
     * The position of this {@link Entity}.
     */
    private XyPair position;
    /**
     * Whether this {@link Entity} has been scanned.
     */
    private boolean isScanned;
    /**
     * The symbol of this {@link Entity}.
     */
    private String symbol;
    /**
     * Whether this {@link Entity} has been removed from the Game.
     */
    private boolean removed;
    
    /**
     * Construct a new Entity at the given coordinates. 
     * In Individual Assignment 1, you, the programmer, 
     * should design and decide the inner-workings of how you construct the default settings 
     * in the Entity superclass.
     * 
     * @param x - horizontal coordinate
     * @param y - vertical coordinate
     */
    public Entity(int x, int y) {
        //set the Entity's position
        this.position = new XyPair(x, y);
        //set the Entity's scanned status
        this.isScanned = false;
        //set the Entity's removed status
        this.removed = false;
    }
    
    /**
     * Sets the Entity's x coordinate to the new value.
     * 
     * @param x - the new value for our x coordinate.
     */
    public void setX(int x) {
        //set the Entity's x coordinate
        this.position.setX(x);
    }

    /**
     * Sets the Entity's y coordinate to the new value.
     * 
     * @param y - the new value for our y coordinate.
     */
    public void setY(int y) {
        //set the Entity's y coordinate
        this.position.setY(y);
    }

    /**
     * Returns the x coordinate value.
     * 
     * @return the x coordinate value.
     */
    public int getX() {
        //return the Entity's x coordinate
        return this.position.getX();
    }

    /**
     * Returns the y coordinate value.
     * 
     * @return the y coordinate value.
     */
    public int getY() {
        //return the Entity's y coordinate
        return this.position.getY();
    }

    /**
     * Adjust the Entity's position (XyPair) by given amounts for x and y. 
     * e.g. sample.adjustPosition(-1,0) - move left by 1; 
     * sample.adjustPosition(1,0) - move right by 1; 
     * sample.adjustPosition(0,-1) - move up by 1; 
     * sample.adjustPosition(0,1) - move down by 1; 
     * sample.adjustPosition(0,0) - don't move; 
     * sample.adjustPosition(-1,-1) - move left 1 and up 1; 
     * sample.adjustPosition(1,1) - move right 1 and down 1; 
     * sample.adjustPosition(-1,1) - move left 1 and down 1; 
     * sample.adjustPosition(1,-1) - move right 1 and up 1.
     * 
     * @param x - amount we wish to adjust the horizontal position of this Entity by.
     * @param y - amount we wish to adjust the vertical position of this Entity by.
     */
    public void adjustPosition(int x, int y) {
        //adjust the Entity's position
        this.position.setX(this.position.getX() + x);
        this.position.setY(this.position.getY() + y);
    }

    /**
     * Marks this Entity as scanned, so it doesn't have to be scanned twice.
     */
    public void scan() {
        //set the Entity's scanned status to true
        this.isScanned = true;
    }

    /**
     * Returns a boolean indicating if the Entity has been scanned.
     * 
     * @return a boolean indicating if the Entity has been scanned.
     */
    public boolean isScanned() {
        //return the Entity's scanned status
        return this.isScanned;
    }

    /**
     * Takes a given String as the new symbol we want to represent the Entity. 
     * v1.1: Note! An INVARIANT of the symbol is that it is a 3-character String. 
     * You should only set the first 3 characters if it is longer and add spaces if shorter.
     * 
     * @param symbol - the new String we want to represent the Entity.
     */
    public void setSymbol(String symbol) {
        //if the symbol is less than 3 characters, add 2 spaces to the end of the symbol
        if (symbol.length() < 3) {
            this.symbol = symbol + "  ";
        } else if (symbol.length() == 3) {
            //if the symbol is 3 characters, set the symbol to the given symbol
            this.symbol = symbol;
        } else if (symbol.length() > 3) {
            //if the symbol is greater than 3 characters, set the symbol to the first 3 characters
            this.symbol = symbol.substring(0, 3);
        }
    }

    /**
     * Returns a 3-character String symbol for this Entity. 
     * Will return " ? " if it has not been scanned.
     * 
     * @return the 3-character String symbol, or " ? " if not been scanned
     */
    public String symbol() {
        //if the Entity has not been scanned, return " ? "
        if (this.isScanned() == false) {
            return " ? ";
        } 
        //if the Entity has been scanned, return its symbol
        return this.symbol;
    }

    /**
     * Method for inflicting damage on the Entity. Defaults to do nothing (e.g. a Star). 
     * Entities that can be damaged will override this with their own methods.
     * 
     * @param damage - the amount of damage the Entity is being hit by.
     */
    public void hit(int damage) {
        //deafaults to do nothing and be overridden by subclasses
    }

    /**
     * Marks an Entity as ready to be removed. 
     * Intended to be used by Lists, arrays, etc., holding that entity as part of a cleanup step.
     */
    public void remove() {
        //set the Entity's removed status to true
        this.removed = true;
    }

    /**
     * Returns whether an Entity has been marked as ready for removal or not.
     * 
     * @return true if an Entity has been marked as ready for removal, otherwise false.
     */
    public boolean isMarkedForRemoval() {
        //return the Entity's removed status
        return this.removed;
    }

    /**
     * Returns a String representation of the current Entity. 
     * It shows its position, if it has been scanned, and if it is marked for removal 
     * e.g. "Entity[x:1, y:2, scanned:true, markedForRemoval:true]".
     * 
     * @Override toString in class Object
     * @return a String representation of the current Entity showing its current position, 
     * if it has been scanned, and if it is marked for removal
     */
    public String toString() {
        //return a String representation of the Entity's position,
        //scanned status, and removed status
        return "Entity[x:" + this.position.getX() + ", y: " + this.position.getY() 
            + ", scanned:" + this.isScanned + ", markedForRemoval:" + this.removed + "]";
    }
}
