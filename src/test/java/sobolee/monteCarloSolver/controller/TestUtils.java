package sobolee.monteCarloSolver.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.StringJoiner;

public class TestUtils {

    static String getBoundsString(String factors, String powers, String comparingValue) {
        final StringJoiner joiner = new StringJoiner("\n");
        final String[] relationalOperators = {"<", "<=", "=", ">=", ">"};
        for (int i = 0; i < relationalOperators.length; i++) {
            joiner.add(factors).add(powers).add(relationalOperators[i]).add(comparingValue);
        }
        return joiner.toString();
    }

    static String getObjectiveFunctionString(String factors, String powers, String objective) {
        return new StringJoiner("\n").add(factors).add(powers).add(objective).toString();
    }

    static class NullOutputStream extends OutputStream {
        @Override
        public void write(int b) throws IOException {
        }
    }
}