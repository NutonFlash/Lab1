import parseFileExceptions.InconsistentMatrixException;
import parseFileExceptions.IncorrectMatrixLine;
import parseFileExceptions.NumberOfEquationException;
import parseFileExceptions.NumberOfUnknownException;

import java.io.*;

public class InputFromFile {
    private final String filePath;
    private BufferedReader bufferedReader;

    public InputFromFile(String filePath) {
        this.filePath = filePath;
    }

    private boolean validateFile() {
        boolean fileValidation = false;
        File file = new File(filePath);
        try (FileReader fileReader = new FileReader(file)) {
            if (file.canRead() && file.exists() && file.isFile()) {
                fileReader.read();
                fileValidation = true;
            } else System.out.println("Ошибка! Не удается открыть файл.");
        } catch (IOException exception) {
            System.out.println("Ошибка! Не удается открыть файл.");
        }
        return fileValidation;
    }

    private int getMatrixProperty() throws IOException, NumberFormatException {
        String strProperty = bufferedReader.readLine();
        if (strProperty != null) {
            if (strProperty.trim().matches("\\d+")) {
                int numberOfProperty = Integer.parseInt(strProperty);
                if (numberOfProperty > 0 && numberOfProperty < 21) {
                    return numberOfProperty;
                }
            }
            bufferedReader.close();
            throw new NumberFormatException();
        }
        bufferedReader.close();
        throw new IOException();
    }

    public Matrix getMatrixFromFile() throws IOException, NumberOfEquationException, NumberOfUnknownException, InconsistentMatrixException, IncorrectMatrixLine {
        if (validateFile()) {
            bufferedReader = new BufferedReader(new FileReader(filePath));
            int numberOfEquations;
            int numberOfUnknowns;
            try {
                numberOfEquations = getMatrixProperty();
            } catch (NumberFormatException exception) {
                bufferedReader.close();
                throw new NumberOfEquationException();
            }
            try {
                numberOfUnknowns = getMatrixProperty();
            } catch (NumberFormatException exception) {
                bufferedReader.close();
                throw new NumberOfUnknownException();
            }
            if (numberOfUnknowns > numberOfEquations)
                throw new InconsistentMatrixException();
            double[][] coefficientsOfUnknowns = new double[numberOfEquations][numberOfUnknowns];
            double[] freeCoefficients = new double[numberOfEquations];
            for (int i = 0; i < numberOfEquations; ++i) {
                String matrixLine = bufferedReader.readLine();
                String[] strMatrixRow;
                if (matrixLine != null) {
                    String expression = String.format("(-?\\d+(\\.\\d+)? ){%d}", (numberOfUnknowns + 1));
                    matrixLine = matrixLine.trim() + " ";
                    if (matrixLine.matches(expression)) {
                        strMatrixRow = matrixLine.split(" ", numberOfUnknowns + 1);
                        for (int j = 0; j < strMatrixRow.length - 1; ++j) {
                            coefficientsOfUnknowns[i][j] = Double.parseDouble(strMatrixRow[j]);
                        }
                        freeCoefficients[i] = Double.parseDouble(strMatrixRow[numberOfUnknowns]);
                    } else {
                        bufferedReader.close();
                        throw new IncorrectMatrixLine(i + 1);
                    }
                } else {
                    bufferedReader.close();
                    throw new IOException();
                }
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
        return null;
    }
}