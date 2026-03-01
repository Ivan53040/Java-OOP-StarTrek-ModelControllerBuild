package sttrj.game;

/**
 * A Statistic object - a managed integer with an enforced minimum and maximum value. 
 * Stat wraps a numerical int value, with a minimum and maximum value enforced inclusively. 
 * i.e. if you pass in a current value of 5 and a maximum value of 2, current will become 2. 
 * This implementation of Stat assumes the minimum value is always 0 (fixed) and 
 * enforces the minimum value as always 0 (no negatives permitted). 
 * Programmers please note: You should implement this minimum value in such a way 
 * that it is easy to refactor your code to allow future implementations 
 * to set varying values for the minimum. 
 * (HINT: That is, do not hard code direct comparisons to a literal 0!)
 * 
 */

public class Stat extends Object {
    /**
     * The current value of this {@link Stat}.
     */
    private int current;
    /**
     * The maximum value of this {@link Stat}.
     */
    private int max;
    /**
     * The minimum value of this {@link Stat}.
     */
    private int min;

    /**
     * Constructs a {@link Stat} instance. 
     * The maximum value must not be less than the minimum value (zero). 
     * If the given amount is less than the minimum value, 
     * then the maximum value is set equal to the minimum value.
     * 
     * @param current - amount to set the current value of the Stat to, 
     * minimum and maximum bounds will be enforced on construction.
     * @param max - amount to set the maximum value of the Stat to, used in boundary enforcement.
     */

    public Stat(int current, int max) {
        //Construct min
        this.min = 0;
        //Construct max
        if (max < this.min) {
            this.max = this.min;
        } else {
            this.max = max;
        }
        //Construct current
        if (current < this.min) {
            this.current = this.min;
        } else if (current > this.max) {
            this.current = this.max;
        } else {
            this.current = current;
        }
    }

    /**
     * Sets the current value of the Stat to the given amount. 
     * Minimum and maximum bounds are enforced. 
     * The maximum value should not be less than the minimum value (zero). 
     * However if, for any reason, the maximum value is less than the minimum value, 
     * then the current value is set equal to the minimum value.
     * 
     * @param amount - amount we wish to the set the current value to.
     */
    public void set(int amount) {
        //set the current value
        //if the amount is greater than the maximum value, 
        //set the current value to the maximum value
        if (amount > this.max) {
            this.current = this.max;
        //if the amount is less than the minimum value, 
        //set the current value to the minimum value
        } else if (amount < this.min) {
            this.current = this.min;
        //if the maximum value is less than the minimum value, 
        //set the current value to the minimum value
        } else if (this.max < this.min) {
            this.current = this.min;
        //if the amount is within the bounds, set the current value to the amount
        } else {
            this.current = amount;
        }
    }

    /**
     * Adjusts the current value of the Stat by the given amount. 
     * Minimum and maximum bounds are enforced. 
     * The maximum value should not be less than the minimum value (zero). 
     * However if, for any reason, the maximum value is less than the minimum value, 
     * then the current value is set equal to the minimum value.
     * 
     * @param amount - the amount we wish to adjust the current value by
     */
    public void adjust(int amount) {
        //adjust the current value
        this.current += amount;
        //if the current value is greater than the maximum value, 
        //set the current value to the maximum value
        if (this.current > this.max) {
            this.current = this.max;
        //if the current value is less than the minimum value, 
        //set the current value to the minimum value
        } else if (this.current < this.min) {
            this.current = this.min;
        //if the maximum value is less than the minimum value, 
        //set the current value to the minimum value
        } else if (this.max < this.min) {
            this.current = this.min;
        }
    }

    /**
     * Sets the maximum value to the given amount. 
     * The maximum value must not be less than the minimum value (zero). 
     * If the given amount is less than the minimum value, 
     * then the maximum value is set equal to the minimum value.
     * 
     * @param amount - the amount we want to set the maximum value to.
     */
    public void setMax(int amount) {
        //set the maximum value
        if (amount < this.min) {
            this.max = this.min;
        } else {
            this.max = amount;
        }
    }

    /**
     * Return the current value of this Stat.
     * 
     * @return the current value of this Stat.
     */
    public int get() {
        //return the current value
        return this.current;
    }

    /**
     * Return the maximum value of this Stat.
     * 
     * @return the maximum value of this Stat.
     */
    public int getMax() {
        //return the maximum value
        return this.max;
    }

    /**
     * Returns a String representation of the Stat. 
     * This is the current value only of the Stat returned as a String. 
     * The Stat's bounds are not included in the toString representation.
     * 
     * @Override toString in class Object
     * @return the current value of the Stat as a String.
     */
    public String toString() {
        //return the current value as a string
        return String.valueOf(this.current);
    }
}
