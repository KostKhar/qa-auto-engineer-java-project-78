package hexlet.code;

public final class App {

    private static final int MIN_LENGTH = 5;

    private App() {
    }

    public static void main(String[] args) {
        var v = new Validator();
        System.out.println(v.string().required().minLength(MIN_LENGTH).contains("hex").isValid("hex to fix"));
    }
}
