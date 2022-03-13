package parseFileExceptions;

public class IncorrectMatrixLine extends Exception {
    public IncorrectMatrixLine(int numberOfIncorrectLine) {
        System.out.println("Неверно введена "+numberOfIncorrectLine+" строка матрицы!\nКоэффициенты должны быть указаны через пробел.");
    }
}
