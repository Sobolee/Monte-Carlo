package sobolee.monteCarloSolver.controller;


import sobolee.monteCarloSolver.core.MonteCarloSolverMessenger;
import sobolee.monteCarloSolver.core.SolverExecutor;
import sobolee.monteCarloSolver.core.SolverFactory;
import sobolee.monteCarloSolver.core.solvers.MonteCarloSolver;
import sobolee.monteCarloSolver.core.solvers.Solver;
import sobolee.monteCarloSolver.core.solvers.model.Point;

public class MonteCarloSolverExecutor implements SolverExecutor {

    private final SolverFactory solverFactory;
    private final MonteCarloSolverMessenger messenger;

    public MonteCarloSolverExecutor(MonteCarloSolverMessenger messenger) {
        this.messenger = messenger;
        this.solverFactory = new MonteCarloSolverFactory(messenger);
    }

    @Override
    public Point execute() {
        final Solver solver = solverFactory.create();
        final double[] lowerSearchingBounds = messenger.getLowerSearchingBounds();
        final double[] upperSearchingBounds = messenger.getUpperSearchingBounds();
        final double precision = messenger.getPrecision();
        return ((MonteCarloSolver) solver).solve(lowerSearchingBounds, upperSearchingBounds, precision);
    }
}