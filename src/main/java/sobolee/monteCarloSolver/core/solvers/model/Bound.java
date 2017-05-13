package sobolee.monteCarloSolver.core.solvers.model;

import sobolee.monteCarloSolver.core.solvers.utils.ArrayUtils;

import java.util.Arrays;
import java.util.Objects;

public class Bound {

    private final double[] factors;
    private final double[] powers;
    private final RelationalOperator relationalOperator;
    private final double comparingValue;

    public Bound(final double[] factors, final double[] powers, final RelationalOperator relationalOperator, final double comparingValue) {
        ArrayUtils.checkIfArraysAreTheSameSizeAndThrowException(factors, powers);
        this.factors = factors;
        this.powers = powers;
        this.relationalOperator = relationalOperator;
        this.comparingValue = comparingValue;
    }

    public boolean isInBound(final double[] values) {
        ArrayUtils.checkIfArraysAreTheSameSizeAndThrowException(factors, values);
        final double rightSide = computeLeftSideOfBound(values);
        return computeIfIsInBound(rightSide);
    }

    private double computeLeftSideOfBound(final double[] values) {
        double result = 0;
        for (int i = 0; i < factors.length; i++) {
            result += factors[i] * Math.pow(values[i], powers[i]);
        }
        return result;
    }

    private boolean computeIfIsInBound(final double rightSide) {
        return relationalOperator.mapToBooleanExpression(rightSide, comparingValue);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bound bound = (Bound) o;
        return Double.compare(bound.comparingValue, comparingValue) == 0 &&
                Arrays.equals(factors, bound.factors) &&
                Arrays.equals(powers, bound.powers) &&
                relationalOperator == bound.relationalOperator;
    }

    @Override
    public int hashCode() {
        return Objects.hash(factors, powers, relationalOperator, comparingValue);
    }
}