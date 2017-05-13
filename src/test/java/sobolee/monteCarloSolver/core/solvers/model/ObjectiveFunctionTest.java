package sobolee.monteCarloSolver.core.solvers.model;

import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class ObjectiveFunctionTest {

    private ObjectiveFunction maxObjectiveFunction = new ObjectiveFunction(
            new double[]{1.0, 2.0, 3.0},
            new double[]{1.0, 2.0, 3.0},
            Objective.MAX
    );

    @Test
    public void shouldThrowExceptionWhenWrongArraysSizes() {
        //given /when /then
        assertThatThrownBy(() -> new ObjectiveFunction(
                new double[]{1.0},
                new double[]{},
                Objective.MIN
        ));
    }

    @Test
    public void shouldThrowExceptionWhenWrongNumberOfCoordinates() {
        // given
        final double[] coordinates = new double[]{};
        // when /then
        assertThatThrownBy(() -> maxObjectiveFunction.computeValue(coordinates))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void shouldProperlyCalculateValue() {
        // given
        final double[] coordinates = {1.0, 2.0, 3.0};

        // when
        final double result = maxObjectiveFunction.computeValue(coordinates);

        // then
        assertThat(result).isEqualTo(90.0);
    }
}