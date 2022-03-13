package parseFileExceptions;

public class InconsistentMatrixException extends Exception {
    public InconsistentMatrixException() {
        System.out.println("Введенное СЛАУ по определению является несовместимым.");
    }
}
