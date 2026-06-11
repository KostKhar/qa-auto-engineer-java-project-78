package hexlet.code.schemas;

public class BaseSchema<T> {
    private boolean requiredBool = false;

    protected boolean isRequired() {
        return requiredBool;
    }

    protected void setRequired() {
        requiredBool = true;
    }

}
