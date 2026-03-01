package sttrj.game;

/**
 * Represents a data object with an x and y integer, used for things like position and vectors. 
 * x represents the horizontal value. y represents the vertical value. 
 * Values can be any integer - positive, negative, or zero.
 */

public class XyPair extends Object {
    /**
     * The x value of this {@link XyPair}.
     */
    private int x;
    /**
     * The y value of this {@link XyPair}.
     */
    private int y;

    /**
     * Constructs an instance of XyPair with the given values. 
     * Values can be any integer - positive, negative, or zero.
     * 
     * @param x the x value of the {@link XyPair}.
     * @param y the y value of the {@link XyPair}.
     */

    public XyPair(int x, int y) {
        //set the x and y values
        this.x = x;
        this.y = y;
    }

    /**
     * Sets the x value of this {@link XyPair}.
     * Value can be any integer - positive, negative, or zero.
     * 
     * @param x the new x value of the {@link XyPair}.
     */
    public void setX(int x) {
        //set the x value
        this.x = x;
    }

    /**
     * Sets the y value of this {@link XyPair}.
     * Value can be any integer - positive, negative, or zero.
     * 
     * @param y the new y value of the {@link XyPair}.
     */
    public void setY(int y) {
        //set the y value
        this.y = y;
    }

    /**
     * Adjusts the x value of this {@link XyPair} by the given amount.
     *  Amount can be any integer - positive, negative, or zero.
     * 
     * @param amount the amount to adjust the x value by.
     */
    public void adjustX(int amount) {
        //adjust the x value
        this.x += amount;
    }

    /**
     * Adjusts the y value of this {@link XyPair} by the given amount.
     * Amount can be any integer - positive, negative, or zero.
     * 
     * @param amount the amount to adjust the y value by.
     */
    public void adjustY(int amount) {
        //adjust the y value
        this.y += amount;
    }

    /**
     * Returns the x value of this {@link XyPair}.
     * 
     * @return the integer value of this x {@link XyPair}.
     */
    public int getX() {
        //return the x value
        return this.x;
    }

    /**
     * Returns the y value of this {@link XyPair}.
     * 
     * @return the integer value of this y {@link XyPair}.
     */
    public int getY() {
        //return the y value
        return this.y;
    }

    /**
     * Returns a string representation of this {@link XyPair}. 
     * In the format "XYPair[x:value, y:value]".
     * 
     * @Override toString in class Object
     * @return a string for the XyPair in the format "XYPair[x:value, y:value]".
     */
    
    public String toString() {
        //return the string representation of the XyPair
        return "XYPair[x:" + this.x + ", y:" + this.y + "]";
    }

}
