package sobolee.monteCarloSolver.core;


import sobolee.monteCarloSolver.core.solvers.Solver;

public interface SolverFactory {

    Solver create();
}