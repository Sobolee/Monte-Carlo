package sobolee.monteCarloSolver.core.utils;

import org.junit.Test;
import sobolee.monteCarloSolver.core.solvers.utils.ArrayUtils;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class ArrayUtilsTest {

    @Test
    public void shouldThrowExceptionWhenArraysAreDifferentSizes() {
        // given
        final double[] firstArray = new double[]{};
        final double[] secondArray = new double[]{1.0};

        // when / then
        assertThatThrownBy(() -> ArrayUtils.checkIfArraysAreTheSameSizeAndThrowException(firstArray, secondArray))
                .isInstanceOf(IllegalArgumentException.class);
    }
}