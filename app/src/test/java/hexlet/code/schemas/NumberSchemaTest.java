package hexlet.code.schemas;

import hexlet.code.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NumberSchemaTest {

    private Validator v;
    private NumberSchema schema;

    @BeforeEach
    void setUp() {
        v = new Validator();
        this.schema = v.number();
    }

    @Test
    void checkValidWithPositiveWithoutRequired_returnTrue() {
        assertTrue(schema.positive().isValid(null), "null should be valid when positive and not required");
    }

    @Test
    void checkValidNullWithoutRequired_returnTrue() {
        assertTrue(schema.isValid(null), "null should be valid when not required");
    }

    @Test
    void checkRequiredWithNull_returnFalse() {
        schema.required();
        assertFalse(schema.isValid(null), "null should be invalid when required");
    }

    @Test
    void checkRequiredWithValidValue_returnTrue() {
        schema.required();
        assertTrue(schema.isValid(10), "valid number should pass when required");
    }

    @Test
    void checkPositiveWithValidValue_returnTrue() {
        assertTrue(schema.positive().isValid(5), "positive number should pass positive check");
    }

    @Test
    void checkPositiveWithZeroWithoutRequired_returnFalse() {
        assertFalse(schema.positive().isValid(0), "zero should fail positive check");
    }

    @Test
    void checkNegativeNumberRequiredWithPositive_returnFalse() {
        schema.required();
        assertFalse(schema.positive().isValid(-10), "negative number should fail positive check");
    }

    @Test
    void checkZeroWithPositiveAndRequired_returnFalse() {
        schema.required();
        assertFalse(schema.positive().isValid(0), "zero should fail positive check when required");
    }

    @ParameterizedTest
    @ValueSource(ints = {6, 9})
    void checkRangePositive_returnTrue(int value) {
        schema.required();
        assertTrue(schema.range(5, 10).isValid(value), "value " + value + " should be inside range [5, 10]");
    }

    @ParameterizedTest
    @ValueSource(ints = {4, 11})
    void checkRangeOutside_returnFalse(int value) {
        schema.required();
        assertFalse(schema.range(5, 10).isValid(value), "value " + value + " should be outside range [5, 10]");
    }

    @ParameterizedTest
    @ValueSource(ints = {5, 10})
    void checkRangeBoundary_returnTrue(int value) {
        schema.required();
        assertTrue(schema.range(5, 10).isValid(value), "boundary value " + value + " should be inside range [5, 10]");
    }

    @ParameterizedTest
    @ValueSource(ints = {6, 9})
    void checkRangeNegative_returnFalse(int value) {
        schema.required();
        assertFalse(schema.range(2, 3).isValid(value), "value " + value + " should be outside range [2, 3]");
    }

    @Test
    void checkRangeNullWithRequired_returnFalse() {
        schema.required();
        assertFalse(schema.range(5, 10).isValid(null), "null should be invalid when range and required are set");
    }

}
