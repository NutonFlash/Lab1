package parseFileExceptions;

public class NumberOfEquationException extends Exception {
    public NumberOfEquationException() {
        System.out.println("Ошибка при чтении количества уравнений СЛАУ!\nКоличество уравнений должно быть от 1 до 20.");
    }
}
