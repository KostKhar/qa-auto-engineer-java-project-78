package hexlet.code.schemas;

public class NumberSchema {
    private boolean required = false;
    private Boolean positive = null;  // null - проверка не задана
    private Integer min = null;
    private Integer max = null;

    public NumberSchema required() {
        this.required = true;
        return this;
    }

    public NumberSchema positive() {
        this.positive = true;
        return this;
    }

    public NumberSchema range(Integer min, Integer max) {
        this.min = min;
        this.max = max;
        return this;
    }

    public boolean isValid(Integer value) {
        if (required && value == null) {
            return false;
        }

        if (value == null) {
            return true;
        }

        if (positive != null && positive && value <= 0) {
            return false;
        }

        if (min != null && value < min) {
            return false;
        }

        if (max != null && value > max) {
            return false;
        }

        return true;
    }
}
