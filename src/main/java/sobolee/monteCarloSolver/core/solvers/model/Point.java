package sobolee.monteCarloSolver.core.solvers.model;

public class Point {

    private final double[] coordinates;

    public Point(final double[] coordinates) {
        this.coordinates = coordinates;
    }

    public double[] getCoordinates() {
        return coordinates;
    }
}