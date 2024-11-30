package expressivo;

import java.util.Map;

/**
 * Represents the multiplication of two expressions in the expression tree.
 * 
 * Abstraction Function:
 *   AF(leftOperand, rightOperand) = leftOperand * rightOperand
 * 
 * Representation Invariant:
 *   - Both leftOperand and rightOperand must adhere to their respective representation invariants.
 * 
 * Safety from Rep Exposure:
 *   - Fields are private, final, and immutable.
 */
public class Multiplication implements Expression {
    private final Expression leftOperand;
    private final Expression rightOperand;

    /**
     * Creates a multiplication expression from two sub-expressions.
     * 
     * @param leftOperand  the left sub-expression
     * @param rightOperand the right sub-expression
     */
    public Multiplication(Expression leftOperand, Expression rightOperand) {
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
                new Multiplication(leftOperand, rightOperand.differentiate(variable)),
                new Multiplication(rightOperand, leftOperand.differentiate(variable))
        );
    }

    @Override
    public Expression simplify(Map<String, Double> environment) {
        Expression simplifiedLeft = leftOperand.simplify(environment);
        Expression simplifiedRight = rightOperand.simplify(environment);

        if (simplifiedLeft.constant() && simplifiedRight.constant()) {
            double product = Double.parseDouble(simplifiedLeft.toString()) *
                             Double.parseDouble(simplifiedRight.toString());
            return new Number(Double.toString(product));
        }

        return new Multiplication(simplifiedLeft, simplifiedRight);
    }

    @Override
    public String toString() {
        String left = leftOperand.isPrimitive() ? leftOperand.toString() : 
                      "(" + leftOperand.toString() + ")";
        String right = rightOperand.isPrimitive() ? rightOperand.toString() : 
                       "(" + rightOperand.toString() + ")";
        return left + " * " + right;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Multiplication)) {
            return false;
        }
        Multiplication other = (Multiplication) obj;
        return leftOperand.equals(other.leftOperand) &&
               rightOperand.equals(other.rightOperand);
    }

    @Override
    public int hashCode() {
        return 37 * leftOperand.hashCode() + rightOperand.hashCode();
    }
}
