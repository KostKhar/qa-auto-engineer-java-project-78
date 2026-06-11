package hexlet.code.schemas;

import hexlet.code.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StringSchemaTest {

    private Validator v;
    private StringSchema schema;

    @BeforeEach
    void setUp() {
        v = new Validator();
        this.schema = v.string();
    }

    @Test
    void checkValidEmptyWithoutRequired_returnTrue() {
        assertTrue(schema.isValid(""), "empty string should be valid when not required");
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
    void checkRequiredEmpty_returnFalse() {
        schema.required();
        assertFalse(schema.isValid(""), "empty string should be invalid when required");
    }

    @Test
    void checkRequiredWithSpaces_returnTrue() {
        schema.required();
        assertTrue(schema.isValid(" "), "whitespace-only string should be valid when required");
    }

    @Test
    void checkRequiredWithString_returnTrue() {
        schema.required();
        assertTrue(schema.isValid("what does the fox say"), "non-empty string should be valid when required");
    }

    @Test
    void checkRequiredWithOneWord_returnTrue() {
        schema.required();
        assertTrue(schema.isValid("hexlet"), "non-empty string should be valid when required");
    }

    @ParameterizedTest
    @ValueSource(strings = {"wh", "what"})
    void checkContainsSubstringInString_returnTrue(String substring) {
        schema.required();
        assertTrue(schema.contains(substring).isValid("what does the fox say"),
                "string should contain substring '" + substring + "'");
    }

    @Test
    void checkNotContainsSubstring_returnFalse() {
        schema.required();
        assertFalse(schema.contains("whatthe").isValid("what does the fox say"),
                "string should not contain missing substring");
    }

    @Test
    void checkContainsNullInString_returnTrue() {
        schema.required();
        assertTrue(schema.contains(null).isValid("what does the fox say"),
                "contains(null) should skip substring check");
    }

    @Test
    void checkContainsEmptySubstring_returnTrue() {
        schema.required();
        assertTrue(schema.contains("").isValid("what does the fox say"),
                "empty substring should match any string");
    }

    @Test
    void checkMinLengthBoundary_returnTrue() {
        schema.required();
        assertTrue(schema.minLength(4).isValid("what"), "string with exact min length should pass");
    }

    @Test
    void checkMinLengthNotInString_returnFalse() {
        schema.required();
        assertFalse(schema.minLength(6).isValid("what"), "string shorter than minLength should fail");
    }

    @Test
    void checkMinLengthNull_returnTrue() {
        schema.required();
        assertTrue(schema.minLength(null).isValid("what"), "minLength(null) should skip length check");
    }

    @Test
    void checkRequiredContainsChain_returnFalse() {
        assertFalse(schema.required().contains("whatthe").isValid("what does the fox say"),
                "string without required substring should fail");
    }

    @Test
    void checkRequiredMinLengthContainsChain_returnTrue() {
        assertTrue(schema.required().minLength(3).contains("fox").isValid("what does the fox say"),
                "string matching all chained constraints should pass");
    }

}
