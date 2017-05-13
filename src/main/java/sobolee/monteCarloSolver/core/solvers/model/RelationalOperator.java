package sobolee.monteCarloSolver.core.solvers.model;

public enum RelationalOperator {
    LESS,
    LESS_OR_EQUAL,
    EQUAL,
    GREATER_OR_EQUAL,
    GREATER;

    public boolean mapToBooleanExpression(final double leftSide, final double rightSide) {
        switch (this) {
            case LESS:
                return leftSide < rightSide;
            case LESS_OR_EQUAL:
                return leftSide <= rightSide;
            case EQUAL:
                return leftSide == rightSide;
            case GREATER_OR_EQUAL:
                return leftSide >= rightSide;
            default:
                return leftSide > rightSide;
        }
    }
}