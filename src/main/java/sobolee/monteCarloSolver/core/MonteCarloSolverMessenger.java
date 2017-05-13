package sobolee.monteCarloSolver.core;

import sobolee.monteCarloSolver.core.solvers.model.Bound;
import sobolee.monteCarloSolver.core.solvers.model.ObjectiveFunction;

import java.util.List;

public interface MonteCarloSolverMessenger extends Messenger {

    int getDimension();

    List<Bound> getBounds();

    ObjectiveFunction getObjectiveFunction();

    double[] getLowerSearchingBounds();

    double[] getUpperSearchingBounds();

    double getPrecision();
}