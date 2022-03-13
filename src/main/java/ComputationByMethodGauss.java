public class ComputationByMethodGauss {

    public ComputationByMethodGauss(){}

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

    public float calculateMatrixDetermination(Matrix matrix) {
        return calculateMatrixMinor(matrix.getNumberOfUnknowns(),matrix.getCoefficientsOfUnknowns());
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

    public double[] calculateFaults(Matrix matrix, double[] definitionOfUnknowns) {
        double[] faults = new double[matrix.getNumberOfEquations()];
        for (int i = 0 ; i<matrix.getNumberOfEquations() ; ++i) {
            double leftSum = 0;
            for (int j = 0 ; j<matrix.getNumberOfUnknowns() ; ++j)
                leftSum += matrix.getCoefficientsOfUnknowns()[i][j]*definitionOfUnknowns[j];
            faults[i] = Math.abs(leftSum-matrix.getFreeCoefficients()[i]);
        }
        return faults;
    }
    private float calculateMatrixMinor(int matrixOrder, double[][] coefficientsOfUnknowns) {
        float result = 0;
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

    private float calculateMatrixMinorForOrderTwo(double[][] coefficientsOfUnknowns) {
        return (float) (coefficientsOfUnknowns[0][0]*coefficientsOfUnknowns[1][1] - coefficientsOfUnknowns[1][0]*coefficientsOfUnknowns[0][1]);
    }
}
