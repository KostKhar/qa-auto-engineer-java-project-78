package hexlet.code.schemas;

public class StringSchema extends BaseSchema<String> {

    private Integer minLength = null;
    private String contains;

    public StringSchema required() {
        setRequired();
        return this;
    }

    public StringSchema minLength(Integer length) {
        minLength = length;
        return this;
    }

    public StringSchema contains(String substring) {
        contains = substring;
        return this;
    }

    public boolean isValid(String value) {
        if (isEmptyValue(value)) {
            return isRequired();
        }

        if (!isMinLengthValid(value)) {
            return false;
        }

        return isContainsValid(value);
    }

    private boolean isEmptyValue(String value) {
        return value == null || value.isEmpty();
    }

    private boolean isMinLengthValid(String value) {
        return minLength == null || value.length() >= minLength;
    }

    private boolean isContainsValid(String value) {
        return contains == null || value.contains(contains);
    }


}
