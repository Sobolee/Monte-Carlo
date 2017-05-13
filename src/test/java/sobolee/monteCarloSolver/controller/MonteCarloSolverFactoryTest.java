package sobolee.monteCarloSolver.controller;

import org.junit.Test;
import sobolee.monteCarloSolver.core.MonteCarloSolverMessenger;
import sobolee.monteCarloSolver.core.SolverFactory;
import sobolee.monteCarloSolver.core.solvers.MonteCarloSolver;
import sobolee.monteCarloSolver.core.solvers.Solver;
import sobolee.monteCarloSolver.core.solvers.model.Bound;
import sobolee.monteCarloSolver.core.solvers.model.Objective;
import sobolee.monteCarloSolver.core.solvers.model.ObjectiveFunction;
import sobolee.monteCarloSolver.core.solvers.model.RelationalOperator;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static sobolee.monteCarloSolver.controller.TestUtils.getBoundsString;
import static sobolee.monteCarloSolver.controller.TestUtils.getObjectiveFunctionString;

public class MonteCarloSolverFactoryTest {

    @Test
    public void shouldProperlyCreateMonteCarloSolverWithMaxObjectiveFunction() {
        // given
        final String dimension = "2";
        final String numberOfBounds = "5";
        final String bounds = getBoundsString("1 1", "1 1", "1");
        final String objective = getObjectiveFunctionString("1 1", "1 1", "max");
        final String input = new StringJoiner("\n")
                .add(dimension)
                .add(numberOfBounds)
                .add(bounds)
                .add(objective)
                .toString();
        final InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        final MonteCarloSolverMessenger messenger = new CommandLineMessenger(inputStream, new TestUtils.NullOutputStream());
        final SolverFactory solverFactory = new MonteCarloSolverFactory(messenger);

        // when
        final Solver solver = solverFactory.create();

        // then
        final List<Bound> expectedBounds = Arrays.asList(
                new Bound(new double[]{1.0, 1.0}, new double[]{1.0, 1.0}, RelationalOperator.LESS, 1.0),
                new Bound(new double[]{1.0, 1.0}, new double[]{1.0, 1.0}, RelationalOperator.LESS_OR_EQUAL, 1.0),
                new Bound(new double[]{1.0, 1.0}, new double[]{1.0, 1.0}, RelationalOperator.EQUAL, 1.0),
                new Bound(new double[]{1.0, 1.0}, new double[]{1.0, 1.0}, RelationalOperator.GREATER_OR_EQUAL, 1.0),
                new Bound(new double[]{1.0, 1.0}, new double[]{1.0, 1.0}, RelationalOperator.GREATER, 1.0));
        final ObjectiveFunction expectedObjectiveFunction = new ObjectiveFunction(new double[]{1.0, 1.0}, new double[]{1.0, 1.0}, Objective.MAX);
        assertThat(solver).isEqualToComparingFieldByField(new MonteCarloSolver(2, expectedBounds, expectedObjectiveFunction));
    }

    @Test
    public void shouldProperlyCreateMonteCarloSolverWithMinObjectiveFunction() {
        // given
        final String dimension = "2";
        final String numberOfBounds = "0";
        final String objective = getObjectiveFunctionString("1 1", "1 1", "min");
        final String input = new StringJoiner("\n")
                .add(dimension)
                .add(numberOfBounds)
                .add(objective)
                .toString();
        final InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        final MonteCarloSolverMessenger messenger = new CommandLineMessenger(inputStream, new TestUtils.NullOutputStream());
        final SolverFactory solverFactory = new MonteCarloSolverFactory(messenger);

        // when
        final Solver solver = solverFactory.create();

        // then
        final List<Bound> expectedBounds = Collections.emptyList();
        final ObjectiveFunction expectedObjectiveFunction = new ObjectiveFunction(new double[]{1.0, 1.0}, new double[]{1.0, 1.0}, Objective.MIN);
        assertThat(solver).isEqualToComparingFieldByField(new MonteCarloSolver(2, expectedBounds, expectedObjectiveFunction));
    }

    @Test
    public void shouldThrowExceptionWhenInvalidRelationalOperator() {
        // given
        final String dimension = "2";
        final String numberOfBounds = "5";
        final String bounds = new StringJoiner("\n").add("1 1").add("1 1").add("invalid relational operator").add("1").toString();
        final String input = new StringJoiner("\n")
                .add(dimension)
                .add(numberOfBounds)
                .add(bounds)
                .toString();
        final InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        final MonteCarloSolverMessenger messenger = new CommandLineMessenger(inputStream, new TestUtils.NullOutputStream());
        final SolverFactory solverFactory = new MonteCarloSolverFactory(messenger);

        // when / then
        assertThatThrownBy(solverFactory::create).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void shouldThrowExceptionWhenInvalidObjective() {
        // given
        final String dimension = "2";
        final String numberOfBounds = "5";
        final String bounds = getBoundsString("1 1", "1 1", "1");
        final String objective = getObjectiveFunctionString("1 1", "1 1", "invalid objective");
        final String input = new StringJoiner("\n")
                .add(dimension)
                .add(numberOfBounds)
                .add(bounds)
                .add(objective)
                .toString();
        final InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        final MonteCarloSolverMessenger messenger = new CommandLineMessenger(inputStream, new TestUtils.NullOutputStream());
        final SolverFactory solverFactory = new MonteCarloSolverFactory(messenger);

        // when / then
        assertThatThrownBy(solverFactory::create).isInstanceOf(IllegalArgumentException.class);
    }
}