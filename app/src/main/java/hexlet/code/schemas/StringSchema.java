package hexlet.code.schemas;

public class StringSchema {

    private boolean required = true;
    private Integer minLength = null;
    private String contains;


    // не позволяет использовать null или пустую строку в качестве значения
    public StringSchema required() {
       required = true;
       return this;
    }

    //минимальной длины для строки
    public StringSchema minLength(int length) {
        minLength = length;
        return this;
    }

    //Строка должна содержать определённую подстроку
    public StringSchema contains(String substring) {
        contains = substring;
        return this;
    }

    public boolean isValid(String value) {
        if(required && (value != null && value.isEmpty())) {
            return false;
        }

        // Если значение null или пустое, и required не задан - пропускаем
        if (value == null || value.isEmpty()) {
            return true;
        }

        // Проверка minLength
        if (minLength != null && value.length() <= minLength) {
            return false;
        }

        // Проверка contains
        return contains == null || value.contains(contains);
    }



}
