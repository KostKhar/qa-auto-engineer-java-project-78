package hexlet.code.schemas;

import hexlet.code.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MapSchemaTest {

    private Validator v;
    private MapSchema schema;

    @BeforeEach
    void setUp() {
        v = new Validator();
        this.schema = v.map();
    }

    @Test
    void checkValidWithoutRequired_returnTrue() {
        assertTrue(schema.isValid(null), "null map should be valid when not required");
    }

    @Test
    void checkRequiredWithNull_returnFalse() {
        schema.required();
        assertFalse(schema.isValid(null), "null map should be invalid when required");
    }

    @Test
    void checkRequiredWithEmptyMap_returnTrue() {
        schema.required();
        assertTrue(schema.isValid(new HashMap<>()), "empty map should be valid when required");
    }

    @Test
    void checkValidEmptyMap_returnTrue() {
        assertTrue(schema.isValid(new HashMap<>()), "empty map should be valid when not required");
    }

    @Test
    void checkValidMap_returnTrue() {
        var data = new HashMap<String, String>();
        data.put("key1", "value1");
        assertTrue(schema.isValid(data), "non-empty map should be valid");
    }

    @Test
    void checkValidMapWithSizeOf_returnFalse() {
        var data = new HashMap<String, String>();
        data.put("key1", "value1");
        assertFalse(schema.sizeof(2).isValid(data), "map with 1 entry should fail sizeof(2)");
    }

    @Test
    void checkValidMapWithSizeOf_returnTrue() {
        var data = new HashMap<String, String>();
        data.put("key1", "value1");
        data.put("key2", "value2");

        assertTrue(schema.sizeof(2).isValid(data), "map with 2 entries should pass sizeof(2)");
    }

    @Test
    void checkValidMapWithSizeOfNull_returnTrue() {
        var data = new HashMap<String, String>();
        assertTrue(schema.sizeof(null).isValid(data), "sizeof(null) should skip size check");
    }

    @Test
    void checkValidMapWithSizeOfZero_returnTrue() {
        assertTrue(schema.sizeof(0).isValid(new HashMap<>()), "empty map should pass sizeof(0)");
    }

    @Test
    void checkShapeWithValidData_returnTrue() {
        Map<String, BaseSchema<?>> schemas = new HashMap<>();
        schemas.put("firstName", v.string().required());
        schemas.put("lastName", v.string().required().minLength(2));
        schema.shape(schemas);

        var human1 = new HashMap<String, String>();
        human1.put("firstName", "John");
        human1.put("lastName", "Smith");
        assertTrue(schema.isValid(human1), "valid human data should pass shape validation");
    }

    @Test
    void checkShapeWithExtraKeys_returnTrue() {
        Map<String, BaseSchema<?>> schemas = new HashMap<>();
        schemas.put("firstName", v.string().required());
        schema.shape(schemas);

        var data = new HashMap<String, String>();
        data.put("firstName", "John");
        data.put("extraKey", "ignored");
        assertTrue(schema.isValid(data), "extra keys outside shape should be allowed");
    }

    @Test
    void checkShapeWithMissingRequiredKey_returnFalse() {
        Map<String, BaseSchema<?>> schemas = new HashMap<>();
        schemas.put("firstName", v.string().required());
        schemas.put("lastName", v.string().required());
        schema.shape(schemas);

        var data = new HashMap<String, String>();
        data.put("firstName", "John");
        assertFalse(schema.isValid(data), "missing required key should fail shape validation");
    }

    @Test
    void checkShapeWithNullLastName_returnFalse() {
        Map<String, BaseSchema<?>> schemas = new HashMap<>();
        schemas.put("firstName", v.string().required());
        schemas.put("lastName", v.string().required().minLength(2));
        schema.shape(schemas);

        var human2 = new HashMap<String, String>();
        human2.put("firstName", "John");
        human2.put("lastName", null);
        assertFalse(schema.isValid(human2), "null required field should fail shape validation");
    }

    @Test
    void checkShapeWithShortLastName_returnFalse() {
        Map<String, BaseSchema<?>> schemas = new HashMap<>();
        schemas.put("firstName", v.string().required());
        schemas.put("lastName", v.string().required().minLength(2));
        schema.shape(schemas);

        var human3 = new HashMap<String, String>();
        human3.put("firstName", "Anna");
        human3.put("lastName", "B");
        assertFalse(schema.isValid(human3), "too short lastName should fail shape validation");
    }

    @Test
    void checkShapeWithWrongFieldType_returnFalse() {
        Map<String, BaseSchema<?>> schemas = new HashMap<>();
        schemas.put("name", v.string().required());
        schema.shape(schemas);

        var data = new HashMap<String, Object>();
        data.put("name", 42);
        assertFalse(schema.isValid(data), "non-string value for string field should fail");
    }

    @Test
    void checkShapeWithNumberField_returnTrue() {
        Map<String, BaseSchema<?>> schemas = new HashMap<>();
        schemas.put("age", v.number().required().positive());
        schema.shape(schemas);

        var data = new HashMap<String, Object>();
        data.put("age", 25);
        assertTrue(schema.isValid(data), "valid number field should pass shape validation");
    }

    @Test
    void checkShapeWithInvalidNumberField_returnFalse() {
        Map<String, BaseSchema<?>> schemas = new HashMap<>();
        schemas.put("age", v.number().required().positive());
        schema.shape(schemas);

        var data = new HashMap<String, Object>();
        data.put("age", -1);
        assertFalse(schema.isValid(data), "negative number for positive field should fail");
    }

    @Test
    void checkShapeWithWrongNumberFieldType_returnFalse() {
        Map<String, BaseSchema<?>> schemas = new HashMap<>();
        schemas.put("age", v.number().required());
        schema.shape(schemas);

        var data = new HashMap<String, Object>();
        data.put("age", "25");
        assertFalse(schema.isValid(data), "string value for number field should fail");
    }

    @Test
    void checkShapeWithNestedMap_returnTrue() {
        Map<String, BaseSchema<?>> addressShape = new HashMap<>();
        addressShape.put("city", v.string().required());

        Map<String, BaseSchema<?>> schemas = new HashMap<>();
        schemas.put("address", v.map().shape(addressShape));
        schema.shape(schemas);

        var address = new HashMap<String, String>();
        address.put("city", "Moscow");

        var data = new HashMap<String, Object>();
        data.put("address", address);
        assertTrue(schema.isValid(data), "valid nested map should pass shape validation");
    }

    @Test
    void checkShapeWithInvalidNestedMap_returnFalse() {
        Map<String, BaseSchema<?>> addressShape = new HashMap<>();
        addressShape.put("city", v.string().required());

        Map<String, BaseSchema<?>> schemas = new HashMap<>();
        schemas.put("address", v.map().shape(addressShape));
        schema.shape(schemas);

        var address = new HashMap<String, String>();
        address.put("city", "");

        var data = new HashMap<String, Object>();
        data.put("address", address);
        assertFalse(schema.isValid(data), "invalid nested map should fail shape validation");
    }

}
