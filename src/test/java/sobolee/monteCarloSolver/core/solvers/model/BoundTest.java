package sobolee.monteCarloSolver.core.solvers.model;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class BoundTest {

    @Test
    public void shouldThrowExceptionWhenWrongArraysSizes() {
        //given /when /then
        assertThatThrownBy(() -> new Bound(
                new double[]{1.0},
                new double[]{},
                RelationalOperator.EQUAL,
                5.0
        )).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void shouldThrowExceptionWhenWrongNumberOfValues() {
        // given
        final Bound bound = new Bound(
                new double[]{1.0, 2.0, 3.0},
                new double[]{1.0, 2.0, 3.0},
                RelationalOperator.GREATER,
                10.0
        );
        final double[] values = new double[]{};

        // when /then
        assertThatThrownBy(() -> bound.isInBound(values)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void shouldProperlyCheckIfIsInBoundForGreater() {
        // given
        final Bound bound = getBound(RelationalOperator.GREATER);
        final double[] values = {1.0, 1.0, 1.0};

        // when
        final boolean isInBounds = bound.isInBound(values);

        // then
        assertThat(isInBounds).isFalse();
    }

    @Test
    public void shouldProperlyCheckIfIsInBoundForLess() {
        // given
        final Bound bound = getBound(RelationalOperator.LESS);
        final double[] values = {0.5, 0.5, 0.5};

        // when
        final boolean isInBounds = bound.isInBound(values);

        // then
        assertThat(isInBounds).isTrue();
    }

    @Test
    public void shouldProperlyCheckIfIsInBoundForGreaterOrEqual() {
        // given
        final Bound bound = getBound(RelationalOperator.GREATER_OR_EQUAL);
        final double[] values = {1.0, 1.0, 1.0};

        // when
        final boolean isInBounds = bound.isInBound(values);

        // then
        assertThat(isInBounds).isTrue();
    }

    @Test
    public void shouldProperlyCheckIfIsInBoundForLessOrEqual() {
        // given
        final Bound bound = getBound(RelationalOperator.LESS_OR_EQUAL);
        final double[] values = {1.0, 1.0, 1.0};

        // when
        final boolean isInBounds = bound.isInBound(values);

        // then
        assertThat(isInBounds).isTrue();
    }

    @Test
    public void shouldProperlyCheckIfIsInBoundForEqual() {
        // given
        final Bound bound = getBound(RelationalOperator.EQUAL);
        final double[] values = {1.0, 1.0, 1.0};

        // when
        final boolean isInBounds = bound.isInBound(values);

        // then
        assertThat(isInBounds).isTrue();
    }

    private Bound getBound(RelationalOperator relationalOperator) {
        final double[] factors = {1.0, 2.0, 3.0};
        final double[] powers = {1.0, 2.0, 3.0};
        final double comparingValue = 6.0;
        return new Bound(
                factors,
                powers,
                relationalOperator,
                comparingValue
        );
    }
}