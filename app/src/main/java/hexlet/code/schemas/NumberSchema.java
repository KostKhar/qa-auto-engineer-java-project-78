package hexlet.code.schemas;

public class NumberSchema extends BaseSchema<NumberSchema> {
    private Boolean positive = null;
    private Integer min = null;
    private Integer max = null;


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
        if (value == null) {
            return !isRequired();
        }

        if (!isPositiveValid(value)) {
            return false;
        }

        return isRangeValid(value);
    }

    private boolean isPositiveValid(Integer value) {
        return positive == null || !positive || value > 0;
    }

    private boolean isRangeValid(Integer value) {
        if (min != null && value < min) {
            return false;
        }

        return max == null || value <= max;
    }
}
