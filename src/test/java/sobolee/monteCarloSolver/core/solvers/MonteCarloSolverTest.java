package sobolee.monteCarloSolver.core.solvers;

import org.junit.Test;
import sobolee.monteCarloSolver.core.solvers.model.*;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class MonteCarloSolverTest {

    private final double solverPrecision = 0.1;

    @Test
    public void shouldThrowExceptionWhenDimensionNegative() {
        // given / when / then
        assertThatThrownBy(() -> new MonteCarloSolver(-1, null, null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void shouldProperlySolveFirstMaxExample() {
        // given
        final double[] powers = {1.0, 1.0, 1.0, 1.0};
        final ObjectiveFunction objectiveFunction = new ObjectiveFunction(
                new double[]{6.0, 8.0, 5.0, 9.0},
                powers,
                Objective.MAX
        );
        final List<Bound> bounds = Arrays.asList(
                new Bound(new double[]{2.0, 1.0, 1.0, 3.0}, powers, RelationalOperator.LESS_OR_EQUAL, 5.0),
                new Bound(new double[]{1.0, 3.0, 1.0, 2.0}, powers, RelationalOperator.LESS_OR_EQUAL, 3.0)
        );
        final int dimension = 4;
        final MonteCarloSolver monteCarloSolver = new MonteCarloSolver(dimension, bounds, objectiveFunction);
        final double[] lowerBoundsForSearching = new double[]{0.0, 0.0, 0.0, 0.0};
        final double[] upperBoundsForSearching = new double[]{3.0, 3.0, 3.0, 3.0};
        final double precision = 0.2;

        // when
        final Point resultPoint = monteCarloSolver.solve(lowerBoundsForSearching, upperBoundsForSearching, precision);

        // then
        final double resultValue = objectiveFunction.computeValue(resultPoint.getCoordinates());
        final double correctAnswer = 17.0;
        assertThat(resultValue).isBetween(correctAnswer - precision - solverPrecision, correctAnswer + precision + solverPrecision);
    }

    @Test
    public void shouldProperlySolveSecondMaxExample() {
        // given
        final double[] powers = {1.0, 1.0};
        final ObjectiveFunction objectiveFunction = new ObjectiveFunction(
                new double[]{3.0, 2.0},
                powers,
                Objective.MAX
        );
        final List<Bound> bounds = Arrays.asList(
                new Bound(new double[]{1.0, -2.0}, powers, RelationalOperator.LESS_OR_EQUAL, 1.0),
                new Bound(new double[]{1.0, -1.0}, powers, RelationalOperator.LESS_OR_EQUAL, 2.0),
                new Bound(new double[]{2.0, -1.0}, powers, RelationalOperator.LESS_OR_EQUAL, 6.0),
                new Bound(new double[]{1.0, 0.0}, powers, RelationalOperator.LESS_OR_EQUAL, 5.0),
                new Bound(new double[]{2.0, 1.0}, powers, RelationalOperator.LESS_OR_EQUAL, 16.0),
                new Bound(new double[]{1.0, 1.0}, powers, RelationalOperator.LESS_OR_EQUAL, 12.0),
                new Bound(new double[]{1.0, 2.0}, powers, RelationalOperator.LESS_OR_EQUAL, 21.0),
                new Bound(new double[]{0.0, 1.0}, powers, RelationalOperator.LESS_OR_EQUAL, 10.0)
        );
        final int dimension = 2;
        final MonteCarloSolver monteCarloSolver = new MonteCarloSolver(dimension, bounds, objectiveFunction);
        final double[] lowerBoundsForSearching = new double[]{0.0, 0.0, 0.0, 0.0};
        final double[] upperBoundsForSearching = new double[]{10.0, 10.0, 10.0, 10.0};
        final double precision = 0.2;

        // when
        final Point resultPoint = monteCarloSolver.solve(lowerBoundsForSearching, upperBoundsForSearching, precision);

        // then
        final double resultValue = objectiveFunction.computeValue(resultPoint.getCoordinates());
        final double correctAnswer = 28.0;
        assertThat(resultValue).isBetween(correctAnswer - precision - solverPrecision, correctAnswer + precision + solverPrecision);
    }

    @Test
    public void shouldProperlySolveMinExample() {
        // given
        final double[] powers = {1.0, 1.0};
        final ObjectiveFunction objectiveFunction = new ObjectiveFunction(
                new double[]{6.0, 9.0},
                powers,
                Objective.MIN
        );
        final List<Bound> bounds = Arrays.asList(
                new Bound(new double[]{3.0, 9.0}, powers, RelationalOperator.GREATER_OR_EQUAL, 27.0),
                new Bound(new double[]{8.0, 4.0}, powers, RelationalOperator.GREATER_OR_EQUAL, 32.0),
                new Bound(new double[]{12.0, 3.0}, powers, RelationalOperator.GREATER_OR_EQUAL, 36.0)
        );
        final int dimension = 2;
        final MonteCarloSolver monteCarloSolver = new MonteCarloSolver(dimension, bounds, objectiveFunction);
        final double[] lowerBoundsForSearching = new double[]{0.0, 0.0};
        final double[] upperBoundsForSearching = new double[]{4.0, 4.0};
        final double precision = 0.2;

        // when
        final Point resultPoint = monteCarloSolver.solve(lowerBoundsForSearching, upperBoundsForSearching, precision);

        // then
        final double resultValue = objectiveFunction.computeValue(resultPoint.getCoordinates());
        final double correctAnswer = 36.0;
        assertThat(resultValue).isBetween(correctAnswer - precision - solverPrecision, correctAnswer + precision + solverPrecision);
    }
}