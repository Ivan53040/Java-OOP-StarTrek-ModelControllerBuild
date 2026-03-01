package sttrj.input.validator;

/**
 * Overwrites Validator isValid to validate an input for course (direction). 
 * Validates that the user String input represents an integer 1 or more (inclusive) 
 * and less than 9.
 */
public class CourseValidator extends Validator {
    
    /**
     * Constructs an instance of CourseValidator.
     */
    public CourseValidator() {
        super();
    }

    /**
     * Validates that the String input represents an integer at least 1 and less than 9. 
     * Takes the given String input and return if the given String input can be evaluated 
     * as 1 or more (inclusive) and less than 9.
     * 
     * @Override isValid in class Validator
     * @param input - The given String we wish to evaluate.
     * @return if the given String input can be evaluated as 1 or more (inclusive) and less than 9.
     */
    public boolean isValid(String input) {
        int course = Integer.parseInt(input);
        return course >= 1 && course < 9;
    }

}
