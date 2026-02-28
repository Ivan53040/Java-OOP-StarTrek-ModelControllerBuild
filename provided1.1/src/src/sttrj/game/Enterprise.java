package sttrj.game;

/**
 * Representation of the Enterprise, handles internal state for the Enterprise in our game.
 */
public class Enterprise extends Entity {
    /**
     * The shields of the Enterprise.
     */ 
    private Stat shields;
    /**
     * The energy of the Enterprise.
     */
    private Stat energy;
    /**
     * The torpedoes Enterprise has available.
     */
    private Stat torpedoes;
    /**
     * The symbol of the Enterprise.
     */
    private String symbol = "<*>";

    /**
     * Constructs an Enterprise instance with:
     * shields at 500,
     * energy at 2500,
     * 10 torpedoes
     * X:4, Y:4 for starting position
     * The Enterprise can always detect itself. It doesn't need to scan...
     */
    public Enterprise() {
        //set the Enterprise's starting position
        super(4, 4);
        //set the Enterprise's symbol
        this.setSymbol(symbol);
        //set the Enterprise's shields
        this.shields = new Stat(500, 500);
        //set the Enterprise's energy
        this.energy = new Stat(2500, 2500);
        //set the Enterprise's torpedoes
        this.torpedoes = new Stat(10, 10);
        //the Enterprise can always detect itself
        this.scan();
    }

    /**
     * Constructs an Enterprise instance with:
     * shields at 500,
     * energy at 2500,
     * 10 torpedoes
     * X:x, Y:y for starting position
     * The Enterprise can always detect itself. It doesn't need to scan...
     * 
     * @param x - the horizontal coordinate for the Enterprise's starting position
     * @param y - the vertical coordinate for the Enterprise's starting position
     */
    public Enterprise(int x, int y) {
        //set the Enterprise's starting position
        super(x, y);
        //set the Enterprise's symbol
        this.setSymbol(symbol);
        //set the Enterprise's shields
        this.shields = new Stat(500, 500);
        //set the Enterprise's energy
        this.energy = new Stat(2500, 2500);
        //set the Enterprise's torpedoes
        this.torpedoes = new Stat(10, 10);
        //the Enterprise can always detect itself
        this.scan();
    }

    /**
     * Returns an int representation of the number of torpedoes the Enterprise has to use.
     * 
     * @return an int representation of the number of torpedoes the Enterprise has to use.
     */
    public int torpedoAmmo() {
        //return the number of torpedoes the Enterprise has
        return this.torpedoes.get();
    }

    /**
     * Creates a Torpedo using Entity and reduces the Enterprise internal ammo tracking by 1, 
     * returns null if no ammo left.
     * 
     * @return a Torpedo using Entity returns null if no ammo left.
     */
    public Entity fireTorpedo() {
        //if the Enterprise has torpedo ammo, create a new Torpedo and 
        //reduce the Enterprise's ammo by 1
        if (this.hasTorpedoAmmo()) {
            this.torpedoes.adjust(-1);
            return new Entity(this.getX(), this.getY());
        } else {
            //if the Enterprise has no torpedo ammo, return null
            return null;
        }
    }

    /**
     * Returns whether the Enterprise still has Torpedo Ammo to spend or not.
     * 
     * @return true if the Enterprise still has Torpedo Ammo, else false.
     */
    public boolean hasTorpedoAmmo() {
        //if the Enterprise has torpedo ammo, return true
        return this.torpedoes.get() > 0;
    }

    /**
     * Returns the current amount of energy in the Enterprise's shields Stat.
     * 
     * @return an int representation of the current value of the Enterprise's shields Stat.
     */
    public int shields() {
        //return the current amount of energy in the Enterprise's shields Stat
        return this.shields.get();
    }

    /**
     * Returns the current amount of energy in the Enterprise's energy Stat.
     * 
     * @return an int representation of the current value of the Enterprise's energy Stat.
     */
    public int energy() {
        //return the current amount of energy in the Enterprise's energy Stat
        return this.energy.get();
    }

    /**
     * Drains the given amount of energy from the Enterprise down to a minimum of 0.
     * 
     * @param energy - amount of energy to subtract
     * @return amount of energy that was available to drain.
     */
    public int drainEnergy(int energy) {
        //has enough energy to drain
        if (this.energy.get() > energy) {
            //if the Enterprise has enough energy to drain, 
            //drain the energy and return the amount drained
            this.energy.adjust(-energy);
            return energy;
        } else {
            //if the Enterprise does not have enough energy to drain, 
            //drain all the energy and return the amount drained
            int eng = this.energy.get();
            //set the Enterprise's energy to 0
            this.energy.set(0);
            return eng;
        }
    }

    /**
     * Adds the given amount of energy to the Enterprise up to it's maximum amount (3000).
     * 
     * @param energy - amount of energy to add
     */
    public void gainEnergy(int energy) {
        //add the given amount of energy to the Enterprise's energy
        this.energy.adjust(energy);
        //if the Enterprise's energy is greater than its maximum, set it to its maximum
        if (this.energy.get() > this.energy.getMax()) {
            this.energy.set(this.energy.getMax());
        }
    }

    /**
     * Adjust shields and energy by the given target amount of energy. 
     * If there is insufficient energy to transfer that, then transfer what is available.
     * 
     * @param energy - the amount of energy we wish to transfer to shields
     * @return how much energy was actually transferred to shields. 
     * (this may differ from the amount ordered based on energy available etc.)
     */
    public int transferEnergyToShields(int energy) {
        //get the available energy
        int availableEnergy = this.energy.get();
        //get the requested energy
        int requested = Math.max(0, energy);
        //get the transferable energy
        int transferable = Math.min(this.shields.getMax() - this.shields.get(), 
            Math.min(availableEnergy, requested));
        //if there is transferable energy, transfer it to shields and 
        //reduce the Enterprise's energy by the transferable amount
        if (transferable > 0) {
            this.shields.adjust(transferable);
            this.energy.adjust(-transferable);
        }
        return transferable;
    }

    /**
     * Return if the Enterprise is currently alive.
     * 
     * @return if the Enterprise is currently alive.
     */
    public boolean isAlive() {
        //if the Enterprise's shields are greater than 0, it is alive
        return this.shields.get() > 0;
    }
    
    /**
     * Hit the Enterprise for the given amount of damage. 
     * Reducing its shields by the given amount. 
     * After damage is inflicted, checks if the enterprise still has shields above 0, 
     * if a hit took it down to 0 shields, or it was already at 0 shields when hit, 
     * the Enterprise is destroyed. (Simplified version for A1, because a Stat should be minimum 0.)
     * 
     * @Override hit in class Entity
     * @param damage - how much damage we wish to do to the Enterprise
     */
    public void hit(int damage) {
        this.shields.adjust(-damage);
        //if shields are 0 or less, destroy the Enterprise
        if (!isAlive()) {
            this.remove();
        }
    }

    /**
     * Heal the Enterprise for the given amount of energy.
     * 
     * @param energy - how much do we wish to heal the Enterprise by.
     */
    public void heal(int energy) {
        //add the given amount of energy to the Enterprise's energy
        this.energy.adjust(energy);
        //if the Enterprise's energy is greater than its maximum, set it to its maximum
        if (this.energy.get() > this.energy.getMax()) {
            this.energy.set(this.energy.getMax());
        }
    }


}
