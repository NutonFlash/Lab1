package parseFileExceptions;

public class NumberOfUnknownException extends Exception {
    public NumberOfUnknownException() {
        System.out.println("Ошибка при чтении количества неизвестных СЛАУ!\nКоличество неизвестных должно быть от 1 до 20.");
    }
}
