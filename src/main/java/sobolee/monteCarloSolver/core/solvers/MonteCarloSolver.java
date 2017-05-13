package sobolee.monteCarloSolver.core.solvers;

import sobolee.monteCarloSolver.core.solvers.model.Bound;
import sobolee.monteCarloSolver.core.solvers.model.ObjectiveFunction;
import sobolee.monteCarloSolver.core.solvers.model.Point;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.function.IntToDoubleFunction;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class MonteCarloSolver implements Solver {

    public static int NUMBER_OF_PROBES = 1000;

    private final List<Bound> bounds;
    private final ObjectiveFunction objectiveFunction;
    private final int dimension;

    public MonteCarloSolver(final int dimension, final List<Bound> bounds, final ObjectiveFunction objectiveFunction) {
        if (dimension < 1) {
            throw new IllegalArgumentException("Dimension must be positive");
        }
        this.dimension = dimension;
        this.objectiveFunction = Objects.requireNonNull(objectiveFunction);
        this.bounds = Objects.requireNonNull(bounds);
    }

    public Point solve(final double[] lowerBounds, final double[] upperBounds, final double precision) {
        Point[] probes = generateRandomProbes(lowerBounds, upperBounds);
        Point bestProbe = objectiveFunction.findPointWithBestValue(probes);
        double currentRadius = 0.5 * getMaximalRange(probes);
        while (currentRadius > precision) {
            probes = generateRandomProbes(bestProbe, currentRadius, lowerBounds, upperBounds);
            bestProbe = objectiveFunction.findPointWithBestValue(probes);
            currentRadius = currentRadius / 2;
        }
        return bestProbe;
    }

    private Point[] generateRandomProbes(final double[] lowerBounds, final double[] upperBounds) {
        final Supplier<Point> supplier = () -> new Point(findPointWithinBounds(lowerBounds, upperBounds));
        return Stream.generate(supplier)
                .limit(NUMBER_OF_PROBES)
                .toArray(Point[]::new);
    }

    private Point[] generateRandomProbes(final Point centerPoint, final double radius, final double[] lowerBounds, final double[] upperBounds) {
        final Supplier<Point> supplier = () -> new Point(findPointWithinBounds(centerPoint, radius, lowerBounds, upperBounds));
        return Stream.generate(supplier)
                .limit(NUMBER_OF_PROBES)
                .toArray(Point[]::new);
    }

    private double[] findPointWithinBounds(final double[] lowerBounds, final double[] upperBounds) {
        final double[] coordinates = new double[dimension];
        IntToDoubleFunction indexToCoordinates;
        do {
            indexToCoordinates = i -> calculateCoordinates(lowerBounds[i], upperBounds[i]);
            Arrays.setAll(coordinates, indexToCoordinates);
        } while (!areCoordinatesInBounds(coordinates));
        return coordinates;
    }

    private double[] findPointWithinBounds(final Point centerPoint, final double radius, final double[] lowerBounds, final double[] upperBounds) {
        final double[] coordinates = new double[dimension];
        IntToDoubleFunction indexToCoordinates;
        do {
            indexToCoordinates = i -> calculateCoordinates(centerPoint.getCoordinates()[i], radius, lowerBounds[i], upperBounds[i]);
            Arrays.setAll(coordinates, indexToCoordinates);
        } while (!areCoordinatesInBounds(coordinates));
        return coordinates;
    }

    private double calculateCoordinates(final double centerPointCoordinates, final double radius, final double lowerBound, final double upperBound) {
        final double possibleMin = centerPointCoordinates - radius;
        final double rangeMin = calculateMinimumOfRange(lowerBound, possibleMin);
        final double possibleMax = centerPointCoordinates + radius;
        final double rangeMax = calculateMaximumOfRange(upperBound, possibleMax);
        return calculateCoordinates(rangeMin, rangeMax);
    }

    private double calculateCoordinates(final double lowerBound, final double upperBound) {
        final Random random = new Random();
        return lowerBound + (upperBound - lowerBound) * random.nextDouble();
    }

    private double calculateMaximumOfRange(final double upperBound, final double possibleMax) {
        return possibleMax < upperBound
                ? possibleMax
                : upperBound;
    }

    private double calculateMinimumOfRange(final double lowerBound, final double possibleMin) {
        return possibleMin > lowerBound
                ? possibleMin
                : lowerBound;
    }

    private boolean areCoordinatesInBounds(final double[] coordinates) {
        return bounds.stream()
                .allMatch(b -> b.isInBound(coordinates));
    }

    private double getMaximalRange(final Point[] probes) {
        return IntStream.range(0, dimension)
                .mapToDouble(i -> getMaximumOfRange(probes, i) - getMinimumOfRange(probes, i))
                .max()
                .getAsDouble();
    }

    private double getMinimumOfRange(final Point[] probes, final int index) {
        return Arrays.stream(probes)
                .map(Point::getCoordinates)
                .mapToDouble(t -> objectiveFunction.computeValueInGivenDimension(t[index], index))
                .min()
                .getAsDouble();
    }

    private double getMaximumOfRange(final Point[] probes, final int index) {
        return Arrays.stream(probes)
                .map(Point::getCoordinates)
                .mapToDouble(t -> objectiveFunction.computeValueInGivenDimension(t[index], index))
                .max()
                .getAsDouble();
    }

    public static class MonteCarloSolverBuilder implements SolverBuilder {

        private int dimension;
        private List<Bound> bounds;
        private ObjectiveFunction objectiveFunction;

        public MonteCarloSolverBuilder withDimension(int dimension) {
            this.dimension = dimension;
            return this;
        }

        public MonteCarloSolverBuilder withBounds(List<Bound> bounds) {
            this.bounds = bounds;
            return this;
        }

        public MonteCarloSolverBuilder withObjectiveFunction(ObjectiveFunction objectiveFunction) {
            this.objectiveFunction = objectiveFunction;
            return this;
        }

        @Override
        public Solver build() {
            return new MonteCarloSolver(dimension, bounds, objectiveFunction);
        }
    }
}