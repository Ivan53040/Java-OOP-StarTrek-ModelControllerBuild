package sttrj.input.validator;

/**
 * Overwrites Validator isValid to validate an input for an energy value. 
 * Validates that the user String input represents an integer equal to 
 * or less than the specified max energy.
 */
public class EnergyValidator extends Validator {
    /**
     * The maximum energy value.
     */
    private int maxEnergy;

    /**
     * Constructs a EnergyValidator with a given maximum acceptable energy value.
     * 
     * @param maxEnergy - maximum energy value this EnergyValidator should accept.
     */
    public EnergyValidator(int maxEnergy) {
        this.maxEnergy = maxEnergy;
    }

    /**
     * Takes the given String input and return if the given String input can be evaluated 
     * as an int equal to or less than the specified max energy.
     * 
     * @Override isValid in class Validator
     * @param input - The given String we wish to evaluate.
     * @return if the given String input can be evaluated as an int equal to or 
     * less than the specified max energy.
     */
    public boolean isValid(String input) {
        int energy = Integer.parseInt(input);
        return energy >= 0 && energy <= maxEnergy;
    }
}
