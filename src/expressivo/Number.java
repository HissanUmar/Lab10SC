package expressivo;

import java.math.BigDecimal;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a numerical constant in the expression tree.
 *
 * Abstraction Function:
 *   AF(decimal) = A number represented as a BigDecimal, accurate to at least four decimal places.
 *
 * Representation Invariant:
 *   - The decimal field represents a valid number with no unnecessary leading or trailing zeros.
 *
 * Safety from Rep Exposure:
 *   - All fields are private, final, and immutable.
 */
public class Number implements Expression {
    private final BigDecimal value;

    /**
     * Constructs a number expression from a string representation.
     * 
     * @param input the string representation of the number
     * @throws IllegalArgumentException if the input does not represent a valid number
     */
    public Number(String input) {
        Pattern pattern = Pattern.compile("^0*(?<number>\\d+(\\.\\d{1,4})?)\\d*$");
        Matcher matcher = pattern.matcher(input);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid number format: " + input);
        }

        value = new BigDecimal(matcher.group("number")).stripTrailingZeros();
        checkRep();
    }

    private void checkRep() {
        // Ensures representation invariant holds (currently, no specific checks required)
    }

    @Override
    public boolean isPrimitive() {
        return true;
    }

    @Override
    public Expression differentiate(String variable) {
        return new Number("0");
    }

    @Override
    public Expression simplify(Map<String, Double> environment) {
        return this;
    }

    /**
     * Indicates if the number is a constant.
     * 
     * @return always true for a number expression
     */
    public boolean constant() {
        return true;
    }

    @Override
    public String toString() {
        return value.toPlainString();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Number)) {
            return false;
        }
        Number other = (Number) obj;
        return value.equals(other.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
