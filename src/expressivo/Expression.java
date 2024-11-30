package expressions;

/**
 * Expression is an immutable abstract data type representing mathematical expressions.
 * It provides methods to build expressions, as well as standard operations like toString(),
 * equals(), and hashCode().
 */
public interface Expression {

    /**
     * Returns a string representation of this expression. The string must be a valid expression
     * with consistent formatting and must reflect the structure of the AST.
     *
     * @return a string representation of this expression
     */
    @Override
    String toString();

    /**
     * Compares this expression to another object for structural equality.
     * Two expressions are equal if they contain the same variables, numbers, and operators
     * in the same structure.
     *
     * @param obj the object to compare to
     * @return true if the objects are structurally equal, false otherwise
     */
    @Override
    boolean equals(Object obj);

    /**
     * Returns a hash code consistent with structural equality.
     *
     * @return the hash code of this expression
     */
    @Override
    int hashCode();
}
