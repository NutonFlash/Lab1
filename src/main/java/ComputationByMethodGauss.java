public class ComputationByMethodGauss {

    public ComputationByMethodGauss(){}

    public double calculateDeterminant(Matrix matrix) {
        Matrix convertedMatrix = convertMatrixForDetermination(matrix);
        double determinant = 0;
        if (convertedMatrix!=null) {
            determinant = convertedMatrix.getCoefficientsOfUnknowns()[0][0];
            for (int i = 1; i<convertedMatrix.getNumberOfUnknowns() ; ++i) {
                determinant *= convertedMatrix.getCoefficientsOfUnknowns()[i][i];
            }
        }
        return determinant;
    }

    private Matrix convertMatrixForDetermination(Matrix matrix) {
        double[][] coefficientsOfUnknowns = new double[matrix.getNumberOfUnknowns()][matrix.getNumberOfUnknowns()];
        double[] freeCoefficients = new double[matrix.getNumberOfUnknowns()];
        for (int i = 0; i < matrix.getNumberOfUnknowns(); ++i) {
            for (int j = 0; j < matrix.getNumberOfUnknowns(); ++j)
                coefficientsOfUnknowns[i][j] = matrix.getCoefficientsOfUnknowns()[i][j];
            freeCoefficients[i] = matrix.getFreeCoefficients()[i];
        }
        for (int i=0; i<matrix.getNumberOfUnknowns()-1 ; ++i) {
            double maxElement = 0;
            int lineIndex = i;
            for (int j=i; j<matrix.getNumberOfUnknowns() ; ++j) {
                if (coefficientsOfUnknowns[j][i]!=0 && Math.abs(coefficientsOfUnknowns[j][i])>Math.abs(maxElement)) {
                    maxElement = coefficientsOfUnknowns[j][i];
                    lineIndex = j;
                }
            }
            if (maxElement == 0 && i==0)
                return null;
            else if (maxElement!=0) {
                double[] firstLine = new double[matrix.getNumberOfUnknowns()];
                double freeCofFirstLine = freeCoefficients[i];
                for (int z = 0; z < matrix.getNumberOfUnknowns(); ++z)
                    firstLine[z] = coefficientsOfUnknowns[i][z];
                coefficientsOfUnknowns[i] = coefficientsOfUnknowns[lineIndex];
                coefficientsOfUnknowns[lineIndex] = firstLine;
                freeCoefficients[i] = freeCoefficients[lineIndex];
                freeCoefficients[lineIndex] = freeCofFirstLine;
                double[] divideCoefficients = new double[matrix.getNumberOfUnknowns() - 1 - i];
                int arrIndex = 0;
                for (int s = i + 1; s < matrix.getNumberOfUnknowns(); ++s) {
                    divideCoefficients[arrIndex] = -coefficientsOfUnknowns[s][i] / maxElement;
                    arrIndex++;
                }
                arrIndex = 0;
                for (int k = i + 1; k < matrix.getNumberOfUnknowns(); ++k) {
                    for (int l = 0; l < matrix.getNumberOfUnknowns(); ++l) {
                        coefficientsOfUnknowns[k][l] += divideCoefficients[arrIndex] * coefficientsOfUnknowns[i][l];
                    }
                    freeCoefficients[k] += divideCoefficients[arrIndex] * freeCoefficients[i];
                    arrIndex++;
                }
            }
        }
        return new Matrix(coefficientsOfUnknowns,freeCoefficients, matrix.getNumberOfUnknowns(), matrix.getNumberOfUnknowns());
    }

    public double calculateMatrixDeterminant(Matrix matrix) {
        return calculateMatrixMinor(matrix.getNumberOfUnknowns(),matrix.getCoefficientsOfUnknowns());
    }

    private double calculateMatrixMinor(int matrixOrder, double[][] coefficientsOfUnknowns) {
        double result = 0;
        if (matrixOrder == 2) {
            result = calculateMatrixMinorForOrderTwo(coefficientsOfUnknowns);
        }
        else
        {
            for (int i = 0; i < matrixOrder ; ++i) {
                double[][] lowerMatrix = new double[matrixOrder-1][matrixOrder-1];
                for (int j = 1; j<matrixOrder ; ++j) {
                    int indexOfArray = 0;
                    for (int z = 0; z < matrixOrder; ++z)
                        if (i != z) {
                            lowerMatrix[j-1][indexOfArray] = coefficientsOfUnknowns[j][z];
                            indexOfArray++;
                        }
                }
                if (i % 2 != 0)
                    result -= coefficientsOfUnknowns[0][i] * calculateMatrixMinor(matrixOrder - 1, lowerMatrix);
                else
                    result += coefficientsOfUnknowns[0][i] * calculateMatrixMinor(matrixOrder - 1, lowerMatrix);
            }
        }
        return result;
    }

    private double calculateMatrixMinorForOrderTwo(double[][] coefficientsOfUnknowns) {
        return (coefficientsOfUnknowns[0][0]*coefficientsOfUnknowns[1][1] - coefficientsOfUnknowns[1][0]*coefficientsOfUnknowns[0][1]);
    }

    public Matrix convertToTriangularMatrix(final Matrix matrix) {
        double[][] coefficientsOfUnknowns = new double[matrix.getNumberOfUnknowns()][matrix.getNumberOfUnknowns()];
        double[] freeCoefficients = new double[matrix.getNumberOfUnknowns()];
        for (int i = 0; i < matrix.getNumberOfUnknowns(); ++i) {
            for (int j = 0; j < matrix.getNumberOfUnknowns(); ++j)
                coefficientsOfUnknowns[i][j] = matrix.getCoefficientsOfUnknowns()[i][j];
            freeCoefficients[i] = matrix.getFreeCoefficients()[i];
        }
        for (int i = 0; i < matrix.getNumberOfUnknowns()-1; ++i) {
            double leadingElement = coefficientsOfUnknowns[i][i];
            for (int j = i; j < matrix.getNumberOfUnknowns(); ++j) {
                coefficientsOfUnknowns[i][j] /= leadingElement;
            }
            freeCoefficients[i] /= leadingElement;
            for (int k = i+1; k < matrix.getNumberOfUnknowns(); ++k) {
                double leadingElementOfCurrentEquation = coefficientsOfUnknowns[k][i];
                for (int j = i; j < matrix.getNumberOfUnknowns(); ++j)
                    coefficientsOfUnknowns[k][j] -= leadingElementOfCurrentEquation*coefficientsOfUnknowns[i][j];
                freeCoefficients[k] -= leadingElementOfCurrentEquation*freeCoefficients[i];
            }
        }
        return new Matrix(coefficientsOfUnknowns,freeCoefficients, matrix.getNumberOfUnknowns(), matrix.getNumberOfUnknowns());
    }

    public double[] findUnknownsOfEquations(Matrix triangularMatrix) {
        double[] definitionOfUnknowns = new double[triangularMatrix.getNumberOfUnknowns()];
        final double[][] coefficientsOfUnknowns = triangularMatrix.getCoefficientsOfUnknowns();
        final double[] freeCoefficients = triangularMatrix.getFreeCoefficients();
        for (int i = triangularMatrix.getNumberOfUnknowns()-1; i>=0 ; --i) {
            double subtrahend = 0;
            for (int j = i+1; j<triangularMatrix.getNumberOfUnknowns(); ++j) {
                subtrahend += coefficientsOfUnknowns[i][j]*definitionOfUnknowns[j];
            }
            definitionOfUnknowns[i] = (freeCoefficients[i]-subtrahend) / coefficientsOfUnknowns[i][i];
        }
        return definitionOfUnknowns;
    }

    public double[] calculateFaults(Matrix matrix, double[] definitionOfUnknowns) {
        double[] faults = new double[matrix.getNumberOfUnknowns()];
        for (int i = 0 ; i<matrix.getNumberOfUnknowns() ; ++i) {
            double leftSum = 0;
            for (int j = 0 ; j<matrix.getNumberOfUnknowns() ; ++j)
                leftSum += matrix.getCoefficientsOfUnknowns()[i][j]*definitionOfUnknowns[j];
            faults[i] = Math.abs(leftSum-matrix.getFreeCoefficients()[i]);
        }
        return faults;
    }
}
