package sobolee.monteCarloSolver.core;

import sobolee.monteCarloSolver.core.solvers.model.Point;

interface Messenger {

    void sendResults(Point resultPoint);
}