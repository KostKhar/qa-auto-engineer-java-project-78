package hexlet.code.schemas;

public class BaseSchema <T> {
    protected boolean requiredBool = false;

    @SuppressWarnings("unchecked")
    public T required() {
        requiredBool = true;
        return (T) this;
    }

}
