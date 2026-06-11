package hexlet.code.schemas;

public class StringSchema extends BaseSchema <StringSchema> {

    private Integer minLength = null;
    private String contains;


    public StringSchema minLength(Integer length) {
        minLength = length;
        return this;
    }

    public StringSchema contains(String substring) {
        contains = substring;
        return this;
    }

    public boolean isValid(String value) {
        if (!requiredBool && (value == null || value.isEmpty())) {
            return true;
        }

        if (requiredBool && (value == null || value.isEmpty())) {
            return false;
        }

        if (minLength != null && value.length() < minLength) {
            return false;
        }

        return contains == null || value.contains(contains);
    }


}
