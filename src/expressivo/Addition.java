package expressivo;

import java.util.Map;

/**
 * Represents the addition of two expressions in the expression tree.
 * 
 * Abstraction Function:
 *   AF(leftOperand, rightOperand) = leftOperand + rightOperand
 * 
 * Representation Invariant:
 *   - Both leftOperand and rightOperand maintain their respective representation invariants.
 * 
 * Safety from Rep Exposure:
 *   - All fields are private, final, and immutable.
 */
public class Addition implements Expression {
    private final Expression leftOperand;
    private final Expression rightOperand;

    /**
     * Creates an addition expression from two sub-expressions.
     * 
     * @param leftOperand  the left sub-expression
     * @param rightOperand the right sub-expression
     */
    public Addition(Expression leftOperand, Expression rightOperand) {
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
    }

    @Override
    public boolean isPrimitive() {
        return false;
    }

    @Override
    public Expression differentiate(String variable) {
        return new Addition(
                leftOperand.differentiate(variable),
                rightOperand.differentiate(variable)
        );
    }

    @Override
    public Expression simplify(Map<String, Double> environment) {
        Expression simplifiedLeft = leftOperand.simplify(environment);
        Expression simplifiedRight = rightOperand.simplify(environment);

        if (simplifiedLeft.constant() && simplifiedRight.constant()) {
            double sum = Double.parseDouble(simplifiedLeft.toString()) +
                         Double.parseDouble(simplifiedRight.toString());
            return new Number(Double.toString(sum));
        }

        return new Addition(simplifiedLeft, simplifiedRight);
    }

    @Override
    public String toString() {
        String left = leftOperand.isPrimitive() ? leftOperand.toString() :
                      "(" + leftOperand.toString() + ")";
        String right = rightOperand.isPrimitive() ? rightOperand.toString() :
                       "(" + rightOperand.toString() + ")";
        return left + " + " + right;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Addition)) {
            return false;
        }
        Addition other = (Addition) obj;
        return leftOperand.equals(other.leftOperand) &&
               rightOperand.equals(other.rightOperand);
    }

    @Override
    public int hashCode() {
        return 31 * leftOperand.hashCode() + rightOperand.hashCode();
    }
}
