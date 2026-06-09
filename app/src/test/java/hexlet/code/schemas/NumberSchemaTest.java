package hexlet.code.schemas;

import hexlet.code.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class NumberSchemaTest {

    private Validator v;
    private NumberSchema schema;


    @BeforeEach
    void setUp() {
        v = new Validator();
        this.schema = v.number();
    }

    @Test
    void checkValidWithPositiveWithoutRequired_returnTrue(){
        assertTrue(schema.positive().isValid(null), "isValid value = string.isEmpty() false");
    }

    @Test
    void checkValidNullWithoutRequired_returnTrue(){
        assertTrue(schema.isValid(null), "isValid value=null false");
    }

    @Test
    void checkRequiredWithValid_returnFalse(){
        schema.required();
        assertFalse(schema.isValid(null), "isValid value=null true after required");
    }

    @Test
    void checkReqiuiredEmptyWithoutRequired_returnFalse(){
        schema.required();
        assertTrue(schema.isValid(10), "Valid value  false");
    }

    @Test
    void checkNegativeNumberRequiredWithPositive_returnFalse(){
        schema.required();
        assertFalse(schema.positive().isValid(-10), "Valid value  false");
    }

    @Test
    void checkNoneDigitWithOneWord_returnTrue(){
        schema.required();
        assertFalse(schema.positive().isValid(0), "0 return true");
    }

    @ParameterizedTest
    @ValueSource(ints = {6, 9})
    void checkRangePositive_returnTrue(int value){
        schema.required();
        assertTrue(schema.range(5,10).isValid(value));
    }

    @ParameterizedTest
    @ValueSource(ints = {6, 9})
    void checkRangeNegative_returnTrue(int value){
        schema.required();
        assertFalse(schema.range(2,3).isValid(value));
    }


    @Test
    void checkRangeNull_returnFalse(){
        schema.required();
        assertFalse(schema.range(5,10).isValid(null));
    }

}