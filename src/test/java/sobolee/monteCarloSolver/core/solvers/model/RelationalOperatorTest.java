package sobolee.monteCarloSolver.core.solvers.model;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RelationalOperatorTest {

    @Test
    public void shouldProperlyMapLessOperatorToTrue() {
        // given
        final double leftSide = 1.0;
        final double rightSide = 2.0;
        final RelationalOperator relationalOperator = RelationalOperator.LESS;

        // when
        boolean result = relationalOperator.mapToBooleanExpression(leftSide, rightSide);

        // then
        assertThat(result).isTrue();
    }

    @Test
    public void shouldProperlyMapLessOrEqualOperatorToTrue() {
        // given
        final double leftSide = 2.0;
        final double rightSide = 2.0;
        final RelationalOperator relationalOperator = RelationalOperator.LESS_OR_EQUAL;

        // when
        boolean result = relationalOperator.mapToBooleanExpression(leftSide, rightSide);

        // then
        assertThat(result).isTrue();
    }

    @Test
    public void shouldProperlyMapEqualOperatorToTrue() {
        // given
        final double leftSide = 2.0;
        final double rightSide = 2.0;
        final RelationalOperator relationalOperator = RelationalOperator.EQUAL;

        // when
        boolean result = relationalOperator.mapToBooleanExpression(leftSide, rightSide);

        // then
        assertThat(result).isTrue();
    }

    @Test
    public void shouldProperlyMapGreaterOrEqualOperatorToTrue() {
        // given
        final double leftSide = 2.0;
        final double rightSide = 2.0;
        final RelationalOperator relationalOperator = RelationalOperator.GREATER_OR_EQUAL;

        // when
        boolean result = relationalOperator.mapToBooleanExpression(leftSide, rightSide);

        // then
        assertThat(result).isTrue();
    }

    @Test
    public void shouldProperlyMapGreaterOperatorToTrue() {
        // given
        final double leftSide = 3.0;
        final double rightSide = 2.0;
        final RelationalOperator relationalOperator = RelationalOperator.GREATER;

        // when
        boolean result = relationalOperator.mapToBooleanExpression(leftSide, rightSide);

        // then
        assertThat(result).isTrue();
    }

    @Test
    public void shouldProperlyMapLessOperatorToFalse() {
        // given
        final double leftSide = 2.0;
        final double rightSide = 2.0;
        final RelationalOperator relationalOperator = RelationalOperator.LESS;

        // when
        boolean result = relationalOperator.mapToBooleanExpression(leftSide, rightSide);

        // then
        assertThat(result).isFalse();
    }

    @Test
    public void shouldProperlyMapLessOrEqualOperatorToFalse() {
        // given
        final double leftSide = 3.0;
        final double rightSide = 2.0;
        final RelationalOperator relationalOperator = RelationalOperator.LESS_OR_EQUAL;

        // when
        boolean result = relationalOperator.mapToBooleanExpression(leftSide, rightSide);

        // then
        assertThat(result).isFalse();
    }

    @Test
    public void shouldProperlyMapEqualOperatorToFalse() {
        // given
        final double leftSide = 2.0;
        final double rightSide = 3.0;
        final RelationalOperator relationalOperator = RelationalOperator.EQUAL;

        // when
        boolean result = relationalOperator.mapToBooleanExpression(leftSide, rightSide);

        // then
        assertThat(result).isFalse();
    }

    @Test
    public void shouldProperlyMapGreaterOrEqualOperatorToFalse() {
        // given
        final double leftSide = 2.0;
        final double rightSide = 3.0;
        final RelationalOperator relationalOperator = RelationalOperator.GREATER_OR_EQUAL;

        // when
        boolean result = relationalOperator.mapToBooleanExpression(leftSide, rightSide);

        // then
        assertThat(result).isFalse();
    }

    @Test
    public void shouldProperlyMapGreaterOperatorToFalse() {
        // given
        final double leftSide = 2.0;
        final double rightSide = 2.0;
        final RelationalOperator relationalOperator = RelationalOperator.GREATER;

        // when
        boolean result = relationalOperator.mapToBooleanExpression(leftSide, rightSide);

        // then
        assertThat(result).isFalse();
    }
}