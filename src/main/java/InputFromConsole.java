import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class InputFromConsole {

    public InputFromConsole(){}

    public Matrix getMatrixFromConsole() throws NumberFormatException {
        int numberOfEquations = getNumberOfMatrixEquations();
        int numberOfUnknowns = getNumberOfMatrixUnknowns();
        if (numberOfUnknowns>numberOfEquations) {
            System.out.println("Такое СЛАУ по определению является несовместимым.");
            throw new NumberFormatException();
        }
        double[][] coefficientsOfUnknowns = new double[numberOfEquations][numberOfUnknowns];
        double[] freeCoefficients = new double[numberOfEquations];
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            for (int i = 0; i < numberOfEquations; ++i) {
                System.out.println("Введите "+numberOfUnknowns+" коэффициент(-а)ов неизвестных переменных уравнения "+(i+1)+":");
                for (int j = 0; j < numberOfUnknowns; ++j) {
                    boolean coefficientValidation = false;
                    while (!coefficientValidation) {
                        System.out.print(">>>");
                        String strMatrixCoefficient = bufferedReader.readLine();
                        if (strMatrixCoefficient != null) {
                            coefficientValidation = validateMatrixCoefficient(strMatrixCoefficient);
                            if (coefficientValidation) {
                                coefficientsOfUnknowns[i][j] = Double.parseDouble(strMatrixCoefficient);
                            }
                        } else throw new IOException();
                    }
                }
                System.out.println("Введите свободный член уравнения "+(i+1)+":");
                boolean freeCoefficientValidation = false;
                while (!freeCoefficientValidation) {
                    System.out.print(">>>");
                    String strFreeCoefficient = bufferedReader.readLine();
                    if (strFreeCoefficient != null) {
                        freeCoefficientValidation = validateMatrixCoefficient(strFreeCoefficient);
                        if (freeCoefficientValidation) {
                            freeCoefficients[i] = Double.parseDouble(strFreeCoefficient);
                        }
                    } else throw new IOException();
                }
            }
        } catch (IOException exception) {
            System.out.println("Ошибка чтения из консоли.");
            System.exit(1);
        }
        Matrix matrix = new Matrix(coefficientsOfUnknowns, freeCoefficients, numberOfEquations, numberOfUnknowns);
        MatrixUtils matrixUtils = new MatrixUtils();
        for (int i=0; i<matrix.getNumberOfEquations() ; ++i) {
            if (matrix.getCoefficientsOfUnknowns()[i][i] == 0) {
                if(!matrixUtils.replaceMatrixLine(matrix,i) && !matrixUtils.replaceMatrixColumn(matrix,i)) {
                    System.out.println("Ведущий элемент "+(i+1)+" строки равняется нулю.\nНе удалось найти корректную перестановку, поэтому СЛАУ не сможет быть решено методом Гаусса.");
                    throw new NumberFormatException();
                }
            }
        }
        return matrix;
    }
    //получить количество уравнений СЛАУ
    public int getNumberOfMatrixEquations() {
        int numberOfEquations = 0;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            boolean equationsValidation = false;
            while (!equationsValidation) {
                System.out.println("Введите количество уравнений:");
                String strNumberOfEquations = bufferedReader.readLine();
                if (strNumberOfEquations != null) {
                    equationsValidation = validateNumberOfMatrixEquations(strNumberOfEquations);
                    if (equationsValidation)
                        numberOfEquations = Integer.parseInt(strNumberOfEquations);
                } else throw new IOException();
            }
        } catch (IOException exception) {
            System.out.println("Ошибка чтения из консоли.");
            System.exit(1);
        }
        return numberOfEquations;
    }

    //получить количество неизвестных СЛАУ
    public int getNumberOfMatrixUnknowns() {
        int numberOfUnknowns = 0;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            boolean unknownsValidation = false;
            while (!unknownsValidation) {
                System.out.println("Введите количество неизвестных:");
                String strNumberOfUnknowns = bufferedReader.readLine();
                if (strNumberOfUnknowns != null) {
                    unknownsValidation = validateNumberOfMatrixUnknowns(strNumberOfUnknowns);
                    if (unknownsValidation)
                        numberOfUnknowns = Integer.parseInt(strNumberOfUnknowns);
                } else throw new IOException();
            }
        } catch (IOException exception) {
            System.out.println("Ошибка чтения из консоли.");
            System.exit(1);
        }
        return numberOfUnknowns;
    }

    //валидация количества уравнений СЛАУ
    private boolean validateNumberOfMatrixEquations(String strNumberOfEquations) {
        boolean validation = false;
        if (strNumberOfEquations != null) {
            int numberOfEquations;
            if (strNumberOfEquations.trim().matches("\\d+")) {
                numberOfEquations = Integer.parseInt(strNumberOfEquations);
                if (numberOfEquations>0 && numberOfEquations<21)
                    validation = true;
                else System.out.println("Ошибка, кол-во уравнений в СЛАУ должно быть от 1 до 20.");
            } else System.out.println("Ошибка, кол-во уравнений в СЛАУ задается целым числом.");
        }
        return validation;
    }
    //валидация количества неизвестных СЛАУ
    private boolean validateNumberOfMatrixUnknowns(String strNumberOfUnknowns) {
        boolean validation = false;
        if (strNumberOfUnknowns != null) {
            int numberOfEquations;
            if (strNumberOfUnknowns.trim().matches("\\d+")) {
                numberOfEquations = Integer.parseInt(strNumberOfUnknowns);
                if (numberOfEquations>0 && numberOfEquations<21)
                    validation = true;
                else System.out.println("Ошибка, кол-во неизвестных в СЛАУ должно быть от 1 до 20.");
            } else System.out.println("Ошибка, кол-во неизвестных в СЛАУ задается целым числом.");
        }
        return validation;
    }

    private boolean validateMatrixCoefficient(String strMatrixCoefficient) {
        boolean validation = false;
        if (strMatrixCoefficient != null) {
            if (strMatrixCoefficient.trim().replace(",",".").matches("-?\\d+(\\.\\d+)?"))
                validation = true;
            else System.out.println("Ошибка, введите число.");
        }
        return validation;
    }
}
