package hexlet.code.schemas;

public class BaseSchema<T> {
    private boolean requiredBool = false;

    protected boolean isRequired() {
        return requiredBool;
    }

    @SuppressWarnings("unchecked")
    public T required() {
        requiredBool = true;
        return (T) this;
    }

}
