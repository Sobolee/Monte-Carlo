package sobolee.monteCarloSolver.core.solvers.utils;

public final class ArrayUtils {

    private ArrayUtils() {
    }

    public static void checkIfArraysAreTheSameSizeAndThrowException(double[] firstArray, double[] secondArray) {
        if (!areArraysTheSameSize(firstArray, secondArray)) {
            throw new IllegalArgumentException("Arrays should be the same size");
        }
    }

    private static boolean areArraysTheSameSize(double[] firstArray, double[] secondArray) {
        return firstArray.length == secondArray.length;
    }
}