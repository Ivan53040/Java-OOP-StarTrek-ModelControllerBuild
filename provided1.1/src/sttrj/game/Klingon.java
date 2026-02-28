package sttrj.game;

/**
 * Klingon handles Entity behaviour plus some extra behaviour. 
 * Including: processing an attack, handling the details of being hit, 
 * managing its own energy reserve.
 */
public class Klingon extends Entity {
    /**
     * The energy of the Klingon.
     */
    private Stat energy;


    /**
     * The symbol of the Klingon.
     */
    private String symbol = "+++";

    /**
     * Constructs a Klingon instance at the given X and Y position. 
     * In Individual Assignment 1, you, the programmer, should design and decide 
     * the inner-workings of how you construct the default settings in the Klingon class.
     * 
     * @param x - horizontal coordinate
     * @param y - vertical coordinate
     */
    public Klingon(int x, int y) {
        super(x, y);
        //set the symbol
        this.setSymbol(symbol);
        //set the energy
        this.energy = new Stat(300, 300);
    }

    /**
     * Handles this Klingon attacking the given Entity. 
     * The Klingon deals damage equal to half it's current energy reserves (rounded down). 
     * (This does not deplete its energy reserves.)
     * Klingons won't attack on the first turn or 
     * when entering a new quadrant to give the player a chance to react.
     * 
     * @param target - something we can hit
     * @return how much damage was dealt by this Klingon in this attack.
     */
    public int attack(Entity target) {
        //return the damage
        return this.energy.get() / 2;
    }

    /**
     * Hits this Klingon with an amount of damage. 
     * Reduces their energy by the damage amount and removes them if they run out of energy.
     * 
     * @Override hit in class Entity
     * @param damage - the amount of damage to deal to the Klingon
     */
    public void hit(int damage) {
        //adjust the energy
        this.energy.adjust(-damage);
        //if the energy is less than or equal to 0, remove the Klingon
        if (this.energy.get() <= 0) {
            this.remove();
        }
    }
}
