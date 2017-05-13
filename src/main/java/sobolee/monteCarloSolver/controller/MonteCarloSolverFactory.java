package sobolee.monteCarloSolver.controller;

import sobolee.monteCarloSolver.core.MonteCarloSolverMessenger;
import sobolee.monteCarloSolver.core.SolverFactory;
import sobolee.monteCarloSolver.core.solvers.MonteCarloSolver;
import sobolee.monteCarloSolver.core.solvers.Solver;

public class MonteCarloSolverFactory implements SolverFactory {

    private final MonteCarloSolverMessenger messenger;
    private final MonteCarloSolver.MonteCarloSolverBuilder solverBuilder = new MonteCarloSolver.MonteCarloSolverBuilder();

    public MonteCarloSolverFactory(MonteCarloSolverMessenger messenger) {
        this.messenger = messenger;
    }

    @Override
    public Solver create() {
        return solverBuilder
                .withDimension(messenger.getDimension())
                .withBounds(messenger.getBounds())
                .withObjectiveFunction(messenger.getObjectiveFunction())
                .build();
    }
}