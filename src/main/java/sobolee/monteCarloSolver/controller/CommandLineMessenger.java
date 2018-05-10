package sobolee.monteCarloSolver.controller;

import sobolee.monteCarloSolver.core.MonteCarloSolverMessenger;
import sobolee.monteCarloSolver.core.solvers.model.*;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class CommandLineMessenger implements MonteCarloSolverMessenger {
    private final Scanner scanner;
    private final PrintWriter printWriter;
    private int dimension;

    public CommandLineMessenger(InputStream inputStream, OutputStream outputStream) {
        scanner = new Scanner(inputStream);
        printWriter = new PrintWriter(outputStream, true);
    }

    @Override
    public int getDimension() {
        printWriter.println("Enter dimension: ");
        dimension = scanner.nextInt();
        flush();
        return dimension;
    }

    @Override
    public List<Bound> getBounds() {
        final int numberOfBounds = getNumberOfBounds();
        final List<Bound> bounds = new LinkedList<>();
        for (int i = 0; i < numberOfBounds; i++) {
            bounds.add(getSingleBound());
        }
        return bounds;
    }

    @Override
    public ObjectiveFunction getObjectiveFunction() {
        printWriter.println("Enter objective function");
        final double[] factors = getFactors();
        final double[] powers = getPowers();
        final Objective objective = getObjective();
        return new ObjectiveFunction(factors, powers, objective);
    }

    @Override
    public double[] getLowerSearchingBounds() {
        return getBoundsForEachDimension("lower");
    }

    @Override
    public double[] getUpperSearchingBounds() {
        return getBoundsForEachDimension("upper");
    }

    @Override
    public double getPrecision() {
        printWriter.println("Enter precision: ");
        final double precision = scanner.nextDouble();
        flush();
        return precision;
    }

    @Override
    public void sendResults(final Point resultPoint) {
        String coordinates = "Result point: ";
        final double[] coordinatesValues = resultPoint.getCoordinates();
        for (int i = 0; i < coordinatesValues.length; i++) {
            coordinates += "x_" + (i + 1) + "=" + coordinatesValues[i] + " ";
        }
        printWriter.println(coordinates);
        printWriter.flush();
        scanner.close();
    }

    private int getNumberOfBounds() {
        printWriter.println("Enter number of bounds: ");
        final int numberOfBounds = scanner.nextInt();
        flush();
        return numberOfBounds;
    }

    private Bound getSingleBound() {
        printWriter.println("Enter bound");
        final double[] factors = getFactors();
        final double[] powers = getPowers();
        final RelationalOperator relationalOperator = getRelationalOperator();
        final double comparingValue = getComparingValue();
        return new Bound(factors, powers, relationalOperator, comparingValue);
    }

    private double[] getFactors() {
        return getValues("factors");
    }

    private double[] getPowers() {
        return getValues("powers");
    }

    private double[] getValues(String value) {
        printWriter.println("Enter " + dimension + " " + value + " separated with space: ");
        return parseValuesSeparatedWithSpace(scanner.nextLine());
    }

    private RelationalOperator getRelationalOperator() {
        printWriter.println("Enter relational operator (<,<=,=,>,>=): ");
        final String relationalOperator = scanner.nextLine();
        switch (relationalOperator) {
            case "<":
                return RelationalOperator.LESS;
            case "<=":
                return RelationalOperator.LESS_OR_EQUAL;
            case "=":
                return RelationalOperator.EQUAL;
            case ">":
                return RelationalOperator.GREATER;
            case ">=":
                return RelationalOperator.GREATER_OR_EQUAL;
            default:
                throw new IllegalArgumentException("Unknown relational operator");
        }
    }

    private double getComparingValue() {
        printWriter.println("Enter left side of bound: ");
        final double comparingValue = scanner.nextDouble();
        flush();
        return comparingValue;
    }

    private Objective getObjective() {
        printWriter.println("Enter type of objective (max/min): ");
        final String objective = scanner.nextLine();
        if (objective.equalsIgnoreCase("max")) {
            return Objective.MAX;
        } else if (objective.equalsIgnoreCase("min")) {
            return Objective.MIN;
        } else {
            throw new IllegalArgumentException("Unknown objective");
        }
    }

    private double[] getBoundsForEachDimension(String whichType) {
        printWriter.println("Enter " + dimension + " " + whichType + " bounds for searching separated with space: ");
        return parseValuesSeparatedWithSpace(scanner.nextLine());
    }

    private double[] parseValuesSeparatedWithSpace(String valuesString) {
        return Arrays.stream(valuesString.split(" ")).mapToDouble(Double::parseDouble).toArray();
    }

    private void flush() {
        scanner.nextLine();
    }
}
