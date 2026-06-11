package hexlet.code.schemas;

import java.util.Map;

public class MapSchema extends BaseSchema<MapSchema> {

    private Integer size = null;
    private Map<String, BaseSchema<?>> shapedSchemas = null;

    // добавляет ограничение на размер мапы. Количество пар ключ-значений в объекте Map должно быть равно заданному
    public MapSchema sizeof(Integer sizeOfMap) {
        this.size = sizeOfMap;
        return this;
    }

    public MapSchema shape(Map<String, BaseSchema<?>> schemas) {
        this.shapedSchemas = schemas;
        return this;
    }

    public <T> boolean isValid(Map<String, T> value) {
        if (value == null) {
            return !requiredBool;
        }

        if (size != null && value.size() != size) {
            return false;
        }

        if (shapedSchemas != null) {
            for (Map.Entry<String, BaseSchema<?>> entry : shapedSchemas.entrySet()) {
                String key = entry.getKey();
                BaseSchema<?> schema = entry.getValue();
                Object fieldValue = value.get(key);

                if (!isFieldValid(schema, fieldValue)) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean isFieldValid(BaseSchema<?> schema, Object fieldValue) {
        if (schema instanceof StringSchema stringSchema) {
            if (fieldValue != null && !(fieldValue instanceof String)) {
                return false;
            }
            return stringSchema.isValid((String) fieldValue);
        }

        if (schema instanceof NumberSchema numberSchema) {
            if (fieldValue != null && !(fieldValue instanceof Integer)) {
                return false;
            }
            return numberSchema.isValid((Integer) fieldValue);
        }

        if (schema instanceof MapSchema mapSchema) {
            if (fieldValue != null && !(fieldValue instanceof Map)) {
                return false;
            }
            @SuppressWarnings("unchecked")
            Map<String, Object> mapValue = (Map<String, Object>) fieldValue;
            return mapSchema.isValid(mapValue);
        }

        return true;
    }

}
