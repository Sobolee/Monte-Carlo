package sobolee.monteCarloSolver.controller;

import org.junit.Test;
import sobolee.monteCarloSolver.core.solvers.model.Point;

import java.io.*;

import static org.assertj.core.api.Assertions.assertThat;

public class CommandLineMessengerTest {

    @Test
    public void shouldProperlyCreateLowerSearchingBounds() {
        // given
        final String lowerBoundsString = "1 1 1\n";
        final InputStream inputStream = new ByteArrayInputStream(lowerBoundsString.getBytes());
        final CommandLineMessenger commandLineMessenger = new CommandLineMessenger(inputStream, new NullOutputStream());

        // when
        final double[] lowerBounds = commandLineMessenger.getLowerSearchingBounds();

        // then
        assertThat(lowerBounds).containsExactly(1.0, 1.0, 1.0);
    }

    @Test
    public void shouldProperlyCreateUpperSearchingBounds() {
        // given
        final String upperBoundsString = "1 1 1\n";
        final InputStream inputStream = new ByteArrayInputStream(upperBoundsString.getBytes());
        final CommandLineMessenger commandLineMessenger = new CommandLineMessenger(inputStream, new NullOutputStream());

        // when
        final double[] upperBounds = commandLineMessenger.getUpperSearchingBounds();

        // then
        assertThat(upperBounds).containsExactly(1.0, 1.0, 1.0);
    }

    @Test
    public void shouldProperlyCreatePrecision() {
        // given
        final String precisionString = "1.0\n";
        final InputStream inputStream = new ByteArrayInputStream(precisionString.getBytes());
        final CommandLineMessenger commandLineMessenger = new CommandLineMessenger(inputStream, new NullOutputStream());

        // when
        final double precision = commandLineMessenger.getPrecision();

        // then
        assertThat(precision).isEqualTo(1.0);
    }

    @Test
    public void shouldProperlyPrintResult() {
        // given
        final OutputStream outputStream = new ByteArrayOutputStream();
        final CommandLineMessenger commandLineMessenger = new CommandLineMessenger(System.in, outputStream);
        final Point point = new Point(new double[]{1.0, 2.0, 3.0});
        // when
        commandLineMessenger.sendResults(point);

        // then
        assertThat(outputStream.toString()).isEqualToIgnoringWhitespace("Result point: x_1=1.0 x_2=2.0 x_3=3.0");
    }

    private class NullOutputStream extends OutputStream {
        @Override
        public void write(int b) throws IOException {
        }
    }
}
