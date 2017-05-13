package sobolee.monteCarloSolver.core.solvers.model;

import sobolee.monteCarloSolver.core.solvers.utils.ArrayUtils;

import java.util.Arrays;
import java.util.Objects;

public class ObjectiveFunction {
    private final double[] factors;
    private final double[] powers;
    private final Objective objective;

    public ObjectiveFunction(final double[] factors, final double[] powers, final Objective objective) {
        ArrayUtils.checkIfArraysAreTheSameSizeAndThrowException(factors, powers);
        this.factors = factors;
        this.powers = powers;
        this.objective = objective;
    }

    public double computeValue(final double[] coordinates) {
        ArrayUtils.checkIfArraysAreTheSameSizeAndThrowException(factors, coordinates);
        double result = 0;
        for (int i = 0; i < factors.length; i++) {
            result += computeValueInGivenDimension(coordinates[i], i);
        }
        return result;
    }

    public Point findPointWithBestValue(final Point[] points) {
        return objective.equals(Objective.MAX)
                ? Arrays.stream(points).max(this::compareValuesOfPoints).get()
                : Arrays.stream(points).min(this::compareValuesOfPoints).get();
    }

    public double computeValueInGivenDimension(final double coordinate, final int dimension) {
        return factors[dimension] * Math.pow(coordinate, powers[dimension]);
    }

    private int compareValuesOfPoints(final Point firstPoint, final Point secondPoint) {
        final double firstValue = computeValue(firstPoint.getCoordinates());
        final double secondValue = computeValue(secondPoint.getCoordinates());
        return Double.compare(firstValue, secondValue);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ObjectiveFunction that = (ObjectiveFunction) o;
        return Arrays.equals(factors, that.factors) &&
                Arrays.equals(powers, that.powers) &&
                objective == that.objective;
    }

    @Override
    public int hashCode() {
        return Objects.hash(factors, powers, objective);
    }
}