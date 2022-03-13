import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class InputManager {

    public InputManager() {}

    public Matrix getMatrix() {
        Matrix matrix = null;
        inputType choseInputType = chooseInputType();
        if (choseInputType == inputType.CONSOLE) {
            InputFromConsole inputFromConsole = new InputFromConsole();
            try {
                matrix = inputFromConsole.getMatrixFromConsole();
            } catch (NumberFormatException exception){}
            if (matrix == null)
                matrix = getMatrix();
            else return matrix;
        }
        else if (choseInputType == inputType.FILE) {
            System.out.println("Введите путь до файла с коэффициентами СЛАУ:");
            try {
                String filePath = new BufferedReader(new InputStreamReader(System.in)).readLine();
                if (filePath != null) {
                    matrix = parseFile(filePath);
                    if (matrix == null)
                        matrix = getMatrix();
                    else return matrix;
                } else {
                    throw new IOException();
                }
            } catch (IOException exception) {
                System.out.println("Ошибка чтения из консоли.");
                System.exit(1);
            }
        } else {
            InputFromConsole inputFromConsole = new InputFromConsole();
            int numberOfEquations = inputFromConsole.getNumberOfMatrixEquations();
            int numberOfUnknowns = inputFromConsole.getNumberOfMatrixUnknowns();
            if (numberOfUnknowns>numberOfEquations) {
                System.out.println("Такое СЛАУ по определению является несовместимым.");
                matrix = getMatrix();
            } else return new MatrixGenerator().generate(numberOfEquations, numberOfUnknowns);
        }
        return matrix;
    }
    private enum inputType {
        CONSOLE,
        FILE,
        MATRIX_GENERATOR
    }
    public inputType chooseInputType() {
        inputType choseInputType = null;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        boolean isAnswerCorrect = false;
        while (!isAnswerCorrect) {
            try {
                System.out.println("Каким способом вы хотите ввести коэффициенты СЛАУ?\n1)через консоль\n2)через файл\n3)сгенерировать случайную матрицу\n\nВведите номер желаемого способа:");
                String inputType = bufferedReader.readLine();
                if (inputType != null) {
                    if (inputType.trim().matches("[1-3]")) {
                        isAnswerCorrect = true;
                        switch (inputType.trim()) {
                            case "1": {
                                choseInputType = InputManager.inputType.CONSOLE;
                                break;
                            }
                            case "2": {
                                choseInputType = InputManager.inputType.FILE;
                                break;
                            }
                            case "3": {
                                choseInputType = InputManager.inputType.MATRIX_GENERATOR;
                                break;
                            }
                        }
                    } else
                        System.out.println("Ошибка, ожидался номер доступного способа ввода СЛАУ.\nПопробуйте еще раз...");
                } else {
                    throw new IOException();
                }
            } catch (IOException exception) {
                System.out.println("Ошибка чтения из консоли.");
                System.exit(1);
            }
        }
        return choseInputType;
    }

    private Matrix parseFile(String filePath) {
        Matrix matrix = null;
        InputFromFile inputFromFile = new InputFromFile(filePath);
        try {
            matrix = inputFromFile.getMatrixFromFile();
        } catch (IOException exception) {
            System.out.println("Ошибка, данные в файле введены некорректно!");
        } catch (Exception exception){}
        return matrix;
    }
}
