package sttrj.input.validator;

/**
 * Overwrites Validator isValid to validate an input for warp coordinates. 
 * Validates that the user String input conforms to either a NUMBER,NUMBER 
 * or NUMBER,NUMBER.NUMBER pattern.
 */
public class WarpCoordValidator extends Validator {

    /**
     * Constructs a WarpCoordValidator instance.
     */
    public WarpCoordValidator() {
        super();
    }

    /**
     * Takes the given String input and return if the given String input can be evaluated 
     * as conforming to either a DIGIT,DIGIT or DIGIT,DIGIT.DIGIT pattern. 
     * Examples of valid inputs:
     * 1,1
     * 5,0.1
     * 
     * @param input - The given String we wish to evaluate.
     * @return if the given String input can be evaluated as conforming to either a 
     * DIGIT,DIGIT or DIGIT,DIGIT.DIGIT pattern.
     */
    public boolean isValid(String input) {
        if (input == null) {
            return false;
        }
        // AI generated method and regex
        // regex: first digit 1-9, second part 0-9 optionally followed by .0-9
        return input.matches("[1-9],[0-9](\\.[0-9])?");
    }
}
