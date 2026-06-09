package hexlet.code.schemas;

import hexlet.code.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorTest {
    private Validator v;
    private StringSchema schema;


    @BeforeEach
    void setUp() {
        v = new Validator();
        this.schema = v.string();
    }

    @Test
    void checkReqiuiredEmptyWithoutRequired_returnTrue(){
        assertTrue(schema.isValid(""), "isValid value = string.isEmpty() false");
    }

    @Test
    void checkValidNullWithoutRequired_returnTrue(){
        assertTrue(schema.isValid(null), "isValid value=null false");
    }


    @Test
    void checkRequiredWithIsValid_returnFalse(){
        schema.required();
        assertFalse(schema.isValid(null), "isValid value=null true after required");
    }

    @Test
    void checkReqiuiredEmptyWithoutRequired_returnFalse(){
        schema.required();
        assertFalse(schema.isValid(""), "isValid value = string.isEmpty() false");
    }

    @Test
    void checkRequiredWithString_returnTrue(){
        schema.required();
        assertTrue(schema.isValid("what does the fox say"));
    }

    @Test
    void checkReqiuiredWithOneWord_returnTrue(){
        schema.required();
        assertTrue(schema.isValid("hexlet"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"wh", "what"})
    void checkContainsSubstringInString_returnTrue(String value){
        schema.required();
        assertTrue(schema.contains(value).isValid("what does the fox say"));
    }


    @Test
    void checkNotContainsSubstring_returnFalse(){
        schema.required();
        assertFalse(schema.contains("whatthe").isValid("what does the fox say"));
    }

    @Test
    void checkContainsNullInString_returnTrue(){
        schema.required();
        assertTrue(schema.contains(null).isValid("what does the fox say"));
    }

    @Test
    void checkMinLengthinString_returnTrue(){
        schema.required();
        assertTrue(schema.minLength(4).isValid("what"));
    }

    @Test
    void checkMinLengthNotInString_returnTrue(){
        schema.required();
        assertFalse(schema.minLength(6).isValid("what"));
    }

    @Test
    void checkMinLengthNull_returnTrue(){
        schema.required();
        assertTrue(schema.minLength(null).isValid("what"));
    }

    @Test
    void checkReqiuiredWithOneWord_returnFalse(){
        schema.required();
        schema.contains("whatthe");
        assertFalse(schema.isValid("what does the fox say"));
    }


}