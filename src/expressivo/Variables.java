package expressivo;

import java.util.Map;

/**
 * Represents a variable in the expression tree.
 *
 * Abstraction Function:
 *   AF(name) = A variable with the name `name`.
 *
 * Representation Invariant:
 *   - The name must be a nonempty, case-sensitive sequence of letters.
 *
 * Safety from Rep Exposure:
 *   - All fields are private, final, and immutable.
 */
public class Variables implements Expression {
    private final String variableName;

    /**
     * Constructs a variable expression.
     * 
     * @param variable the name of the variable
     * @throws IllegalArgumentException if the name is not a valid variable identifier
     */
    public Variables(String variable) {
        if (!variable.matches("[a-zA-Z]+")) {
            throw new IllegalArgumentException("Invalid variable name: " + variable);
        }
        this.variableName = variable;
        validateRep();
    }

    /**
     * Ensures the representation invariant is maintained.
     */
    private void validateRep() {
        assert variableName.matches("[a-zA-Z]+");
    }

    @Override
    public boolean isPrimitive() {
        return true;
    }

    @Override
    public Expression differentiate(String variable) {
        return variable.equals(variableName) ? new Number("1") : new Number("0");
    }

    @Override
    public Expression simplify(Map<String, Double> environment) {
        if (environment.containsKey(variableName)) {
            return new Number(environment.get(variableName).toString());
        }
        return this;
    }

    @Override
    public String toString() {
        return variableName;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Variables)) {
            return false;
        }
        Variables other = (Variables) obj;
        return variableName.equals(other.variableName);
    }

    @Override
    public int hashCode() {
        return variableName.hashCode();
    }
}
