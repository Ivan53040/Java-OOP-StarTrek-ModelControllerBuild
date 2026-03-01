package sttrj.game;

import java.util.ArrayList;
import java.util.Random;

/**
 * Quadrant are sections of space with a randomised mixture of Stars, Klingons and Starbases.
 */
public class Quadrant extends Object {
    /**
     * The position of the Quadrant in the Galaxy.
     */
    private XyPair position;
    /**
     * The number of Starbases in the Quadrant.
     */
    private ArrayList<Starbase> starbases = new ArrayList<>();
    /**
     * The number of Klingons in the Quadrant.
     */
    private ArrayList<Klingon> klingons = new ArrayList<>();
    /**
     * The number of Stars in the Quadrant.
     */
    private ArrayList<Star> stars = new ArrayList<>();
    
    /**
     * The maximum number of rows in the Quadrant.
     */
    private int maxRows = 8;
    /**
     * The maximum number of columns in the Quadrant.
     */
    private int maxCols = 8;

    /**
     * Constructs a Quadrant at the given coordinates.
     * 
     * @param galaxyX - horizontal coordinate for this Quadrant in the Galaxy.
     * @param galaxyY - vertical coordinate for this Quadrant in the Galaxy.
     */
    public Quadrant(int galaxyX, int galaxyY) {
        //set the position
        this.position = new XyPair(galaxyX, galaxyY);
        // Randomised counts roughly similar to classic game feel
        //create a new random object
        Random random = new Random();
        //generate a random number between 0 and 1 for the starbases
        int starbases = random.nextInt(2); // 0-1
        //generate a random number between 0 and 3 for the klingons
        int klingons = random.nextInt(4); // 0-3
        //generate a random number between 0 and 4 for the stars
        int stars =  random.nextInt(5); // 0-4

        //place the entities on random empty sectors
        for (int i = 0; i < starbases; i++) {
            XyPair position = getRandomEmptySector();
            this.starbases.add(new Starbase(position.getX(), position.getY()));
        }
        //place the klingons on random empty sectors
        for (int i = 0; i < klingons; i++) {
            XyPair position = getRandomEmptySector();
            this.klingons.add(new Klingon(position.getX(), position.getY()));
        }
        //place the stars on random empty sectors
        for (int i = 0; i < stars; i++) {
            XyPair position = getRandomEmptySector();
            this.stars.add(new Star(position.getX(), position.getY()));
        }
    }

    // JB: We should provide them this constructor commented out, so we can guarantee it works
    // correctly for testing, it'd be unfortunate for a student to break this bit and lose hours
    // to this rather than actual issues with their program.

    /**
     * Used only for testing, so we can specify a given number of {@link Starbase}, {@link Klingon}
     * and {@link Star} to be generated in a given {@link Quadrant} in a very specific order for
     * predictability when testing.
     *
     * @param galaxyX horizontal coordinate
     * @param galaxyY vertical coordinate
     * @param starbases number of {@link Starbase} for the {@link Quadrant} to place on empty
     *                  sectors in this {@link Quadrant}
     * @param klingons number of {@link Klingon} for the {@link Quadrant} to place on empty sectors
     *                  in this {@link Quadrant}
     * @param stars number of {@link Star} for the {@link Quadrant} to place on empty sectors in
     *                  this {@link Quadrant} Because this constructor is used only for testing, we
     *                  want the {@link Starbase}, {@link Klingon} and {@link Star} placed in a set
     *                  pattern it is expected we will place all the {@link Starbase}, then all the
     *                  {@link Klingon} and then all the {@link Star} going from the top left to the
     *                  bottom right placing in empty x,y coords until we have placed 
     *                  the given number of each.
     *
     *                  <p>Ps. As a reminder only one {@link Starbase}, {@link Star} or
     *                  {@link Klingon} should be at a specific x,y coordinate for the
     *                  {@link Quadrant}, they should never overlap.
     *                  </p>
     */
    public Quadrant(final int galaxyX, final int galaxyY, int starbases, int klingons, int stars) {
        //set the position
        this.position = new XyPair(galaxyX, galaxyY);
        //place the starbases
        for (int i = 0; i < starbases; i++) {
            XyPair position = getRandomEmptySector();
            this.starbases.add(new Starbase(position.getX(), position.getY()));
        }
        //place the klingons
        for (int i = 0; i < klingons; i++) {
            XyPair position = getRandomEmptySector();
            this.klingons.add(new Klingon(position.getX(), position.getY()));
        }
        //place the stars
        for (int i = 0; i < stars; i++) {
            XyPair position = getRandomEmptySector();
            this.stars.add(new Star(position.getX(), position.getY()));
        }
    }
    
    /**
     * Returns the number of live Klingons in this Quadrant.
     * 
     * @return the number of live Klingons in this Quadrant.
     */
    public int klingonCount() {
        //return the number of klingons
        return this.klingons.size();
    }

    /**
     * Returns the number of Starbases in this Quadrant.
     * 
     * @return the number of Starbases in this Quadrant.
     */
    public int starbaseCount() {
        //return the number of starbases
        return this.starbases.size();
    }

    /**
     * Returns the number of Stars in this Quadrant.
     * 
     * @return the number of Stars in this Quadrant.
     */
    public int starCount() {
        //return the number of stars
        return this.stars.size();
    }

    /**
     * Returns the x-coordinate for this Quadrant.
     * 
     * @return the x-coordinate for this Quadrant.
     */
    public int getX() {
        //return the x coordinate
        return this.position.getX();
    }

    /**
     * Returns the y-coordinate for this Quadrant.
     * 
     * @return the y-coordinate for this Quadrant.
     */
    public int getY() {
        //return the y coordinate
        return this.position.getY();
    }

    /**
     * Returns the 3 character String symbol at the given x,y coordinates 
     * checking the Stars, Klingons, and Starbases for any of those at the given coordinates, 
     * if none are returns "   ".
     * 
     * @param x - horizontal coordinate.
     * @param y - vertical coordinate.
     * @return 3 character String symbol.
     */
    public String getSymbolAt(int x, int y) {
        //get the entity at the given x and y coordinates
        Entity e = getEntityAt(x, y);
        //if the entity is not null, return the symbol
        if (e != null) {
            return e.symbol();
        }
        //if the entity is null, return "   "
        return "   ";
    }

    /**
     * Searches the Quadrant for any Entitys at the given location.
     * 
     * @param x - horizontal coordinate
     * @param y - vertical coordinate
     * @return if there is an Entity at the given x, y coordinate in the Quadrant 
     * return that, otherwise return null.
     */
    public Entity getEntityAt(int x, int y) {
        //iterate through the stars
        for (Star s : this.stars) {
            //if the star's x and y coordinates match the given x and y coordinates, return the star
            if (s.getX() == x && s.getY() == y) {
                return s;
            }
        }
        //iterate through the starbases
        for (Starbase s : this.starbases) {
            //if the starbase's x and y coordinates match the given x and y coordinates, 
            //return the starbase
            if (s.getX() == x && s.getY() == y) {
                return s;
            }
        }
        //iterate through the klingons
        for (Klingon k : this.klingons) {
            //if the klingon's x and y coordinates match the given x and y coordinates, 
            //return the klingon
            if (k.getX() == x && k.getY() == y) {
                return k;
            }
        }
        //if no entity is found, return null
        return null;
    }

    /**
     * Returns how many Klingons are currently marked for removal.
     * 
     * @return how many Klingons are currently marked for removal.
     */
    public int klingonsMarkedForRemovalCount() {
        //initialize the count
        int count = 0;
        //iterate through the klingons
        for (Klingon k : this.klingons) {
            //if the klingon is marked for removal, increment the count
            if (k.isMarkedForRemoval()) {
                count += 1;
            }
        }
        //return the count
        return count;
    }
    
    /**
     * Returns a descriptive String representation of this Quadrant's state.
     * the first line provides this Quadrant's position Quadrant(x,y)
     * the second line provides how many Stars there are Stars: n
     * the third line provides how many Klingons there are Enemies: n
     * the fourth line provides how many Starbases there are Starbases: n
     * The final line includes a trailing newline character.
     * 
     * @Override toString in class Object
     * @return the 4-line descriptive String representation of this Quadrant 
    */
    public String toString() {
        //return the string representation of the quadrant
        return "Quadrant(" + getX() + "," + getY() + ")\n" 
               + "Stars: " + starCount() + "\n" 
               + "Enemies: " + klingonCount() + "\n"
               + "Starbases: " + starbaseCount() + "\n";
    }

    /**
     * Scan every Klingon, Starbase and Star in this Quadrant. 
     * Slightly redundant because we always set Stars as already scanned, 
     * but scan them again in case something went wrong.
     */
    public void scan() {
        //iterate through the stars
        for (Star s : this.stars) {
            //scan the star
            s.scan();
        }
        //iterate through the klingons
        for (Klingon k : this.klingons) {
            k.scan();
        }
        for (Starbase s : this.starbases) {
            s.scan();
        }
    }

    /**
     * Returns a String representation of the Quadrant formatted as DIGITDIGITDIGIT i.e. 123
     * the first number is how many Stars there are in this Quadrant.
     * the second number is how many Starbases there are in this Quadrant.
     * the third number is how many Klingons there are in this Quadrant.
     * So a Quadrant with 2 Stars, 0 Starbases and 3 Klingons 
     * would have a String representation of 203.
     * 
     * @return a String representation of the Quadrant
     */
    public String symbol() {
        //return the string representation of the quadrant
        return Integer.toString(this.starCount()) 
            + Integer.toString(this.starbaseCount()) 
            + Integer.toString(this.klingonCount());
    }

    /**
    * Handles a 'turn' of the game for the {@link Starbase}s and {@link Klingon}s in this
    * {@link Quadrant}. Intended to be called only if the {@link Quadrant} is currently active.
    * Attempts to replenish {@link Enterprise} if docked at a {@link Starbase}.
    * Deals with attacks from any {@link Klingon}s if not docked at a {@link Starbase}.
    *
    * @param game the current {@link Game} state for us to manipulate.
    */
    public void tick(final Game game) {
        //get the enterprise
        Enterprise enterprise = game.getEnterprise();

        //handle the starbase healing energy to the enterprise
        for (Starbase starbase : this.starbases) {
            int dx = Math.abs(enterprise.getX() - starbase.getX());
            int dy = Math.abs(enterprise.getY() - starbase.getY());
            if ((dx <= 1 && dy <= 1)) {
                starbase.attemptHeal(enterprise);
            }
        }
        
        //handle the klingon attacks
        for (Klingon klingon : this.klingons) {
            if (!klingon.isMarkedForRemoval()) {
                int dmg = klingon.attack(enterprise);
                if (dmg > 0) {
                    //if the enterprise is docked, negate the damage
                    boolean isDocked = false;
                    for (Starbase starbase : this.starbases) {
                        int dx = Math.abs(enterprise.getX() - starbase.getX());
                        int dy = Math.abs(enterprise.getY() - starbase.getY());
                        if ((dx <= 1 && dy <= 1)) {
                            isDocked = true;
                            break;
                        }
                    }
                    if (isDocked) {
                        enterprise.hit(0);
                    } else {
                        enterprise.hit(dmg);
                    }
                }
            }
        }
        
        this.cleanup();
    }

    /**
     * Hit every Klingon in this Quadrant.
     * 
     * @param damage - how much damage to do to EACH Klingon.
     */
    public void hit(int damage) {
        //iterate through the klingons
        for (Klingon k : this.klingons) {
            //if the klingon is not marked for removal, hit it
            if (!k.isMarkedForRemoval()) {
                k.hit(damage);
            }
        }
    }
    
    /**
     * Returns a random empty sector position as an XyPair.
     * AI helps for the do while loop.
     * @return the position of an empty sector position represented as a XyPair.
     */
    public XyPair getRandomEmptySector() {
        //create a new random object
        Random random = new Random();
        //initialize the x and y coordinates
        int x;
        int y;
        //generate a random x and y coordinate until an empty sector is found
        do {
            x = random.nextInt(maxCols);
            y = random.nextInt(maxRows);
        } while (getEntityAt(x, y) != null);
        return new XyPair(x, y);
    }
    
    /**
     * Removes any Klingon and Starbase that 
     * have been marked for removal from their respective List. 
     * Only public for testing purposes.
     */
    public void cleanup() {
        //remove the marked klingons
        ArrayList<Klingon> remainingKlingons = new ArrayList<>();
        //iterate through the klingons
        for (Klingon k : this.klingons) {
            //if the klingon is not marked for removal, add it to the remaining klingons
            if (!k.isMarkedForRemoval()) {
                remainingKlingons.add(k);
            }
        }
        this.klingons = remainingKlingons;
        
        //remove the marked starbases
        ArrayList<Starbase> remainingStarbases = new ArrayList<>();
        //iterate through the starbases
        for (Starbase s : this.starbases) {
            //if the starbase is not marked for removal, add it to the remaining starbases
            if (!s.isMarkedForRemoval()) {
                remainingStarbases.add(s);
            }
        }
        //set the starbases to the remaining starbases
        this.starbases = remainingStarbases;
    }
}
