package sttrj.game;

/**
 * A Starbase is an Entity that can protect and heal the Enterprise. 
 * This only occurs when the Enterprise is adjacent to a Starbase in the same Quadrant. 
 * A Starbase has powerful shields that can withstand any phaser attack 
 * and will also protect any docked starships. 
 * However, photon torpedoes will destroy a Starbase. 
 * A Starbase only has a finite ration of power to help heal a docked starship. 
 * Once that power has been transferred to a starship, 
 * there is no more available from that Starbase while the Enterprise remains in the Quadrant. 
 * However, when the Enterprise leaves the Quadrant, 
 * the Starbase will have replenished that power ration by 
 * the time the Enterprise next visits that Quadrant. 
 * The game is currently configured such that a Starbase has a ration of 300 units of energy 
 * to help heal a docked starship.
 */
public class Starbase extends Entity {
    /**
     * The symbol of the Starbase.
     */
    private String symbol = ">!<";
    /**
     * The energy of the Starbase.
     */
    private Stat energy = new Stat(300, 300);
    
    /**
     * Constructs a Starbase instance at the given coordinates.
     * 
     * @param x - horizontal coordinate
     * @param y - vertical coordinate
     */
    public Starbase(int x, int y) {
        super(x, y);
        //set the symbol
        this.setSymbol(symbol);
    }
    
    /**
     * Handles checking if the given Enterprise is adjacent to the Starbase, 
     * and if it is, healing the Enterprise with 300 energy.
     * 
     * @param enterprise - The Enterprise to check against the current position of our Starbase 
     * to work out if it is adjacent, and call the Enterprises heal method if so.
     */
    public void attemptHeal(Enterprise enterprise) {
        //calculate the distance between the enterprise and the starbase
        int dx = Math.abs(enterprise.getX() - this.getX());
        int dy = Math.abs(enterprise.getY() - this.getY());
        //if the enterprise is adjacent to the starbase, heal the enterprise
        if ((dx <= 1 && dy <= 1)) {
            enterprise.heal(this.energy.get());
            this.energy.adjust(-this.energy.get());
        }
    }

}
