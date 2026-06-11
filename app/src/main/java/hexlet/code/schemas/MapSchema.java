package hexlet.code.schemas;

import java.util.Map;

public class MapSchema extends BaseSchema<Map<String, Object>> {

    private Integer size = null;
    private Map<String, ? extends BaseSchema<?>> shapedSchemas = null;

    public MapSchema required() {
        setRequired();
        return this;
    }

    // добавляет ограничение на размер мапы. Количество пар ключ-значений в объекте Map должно быть равно заданному
    public MapSchema sizeof(Integer sizeOfMap) {
        this.size = sizeOfMap;
        return this;
    }

    public MapSchema shape(Map<String, ? extends BaseSchema<?>> schemas) {
        this.shapedSchemas = schemas;
        return this;
    }

    public <T> boolean isValid(Map<String, T> value) {
        if (value == null) {
            return isRequired();
        }

        if (!isSizeValid(value)) {
            return false;
        }

        return isShapeValid(value);
    }

    private boolean isSizeValid(Map<String, ?> value) {
        return size == null || value.size() == size;
    }

    private <T> boolean isShapeValid(Map<String, T> value) {
        if (shapedSchemas == null) {
            return true;
        }

        for (Map.Entry<String, ? extends BaseSchema<?>> entry : shapedSchemas.entrySet()) {
            String key = entry.getKey();
            BaseSchema<?> schema = entry.getValue();
            Object fieldValue = value.get(key);

            if (!isFieldValid(schema, fieldValue)) {
                return false;
            }
        }

        return true;
    }

    private boolean isFieldValid(BaseSchema<?> schema, Object fieldValue) {
        if (schema instanceof StringSchema stringSchema) {
            return validateStringField(stringSchema, fieldValue);
        }

        if (schema instanceof NumberSchema numberSchema) {
            return validateNumberField(numberSchema, fieldValue);
        }

        if (schema instanceof MapSchema mapSchema) {
            return validateMapField(mapSchema, fieldValue);
        }

        return true;
    }

    private boolean validateStringField(StringSchema schema, Object fieldValue) {
        if (fieldValue != null && !(fieldValue instanceof String)) {
            return false;
        }
        return schema.isValid((String) fieldValue);
    }

    private boolean validateNumberField(NumberSchema schema, Object fieldValue) {
        if (fieldValue != null && !(fieldValue instanceof Integer)) {
            return false;
        }
        return schema.isValid((Integer) fieldValue);
    }

    @SuppressWarnings("unchecked")
    private boolean validateMapField(MapSchema schema, Object fieldValue) {
        if (fieldValue != null && !(fieldValue instanceof Map)) {
            return false;
        }
        Map<String, Object> mapValue = (Map<String, Object>) fieldValue;
        return schema.isValid(mapValue);
    }

}
