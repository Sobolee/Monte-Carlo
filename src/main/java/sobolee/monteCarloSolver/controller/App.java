package sobolee.monteCarloSolver.controller;

import sobolee.monteCarloSolver.core.SolverExecutor;

public class App {

    public static void main(String[] args) {
        CommandLineMessenger messenger = new CommandLineMessenger(System.in, System.out);
        final SolverExecutor solverExecutor = new MonteCarloSolverExecutor(messenger);
        solverExecutor.execute();
    }
}