
public class MatrixGenerator {

    public MatrixGenerator(){}

    public Matrix generate(int numberOfEquations, int numberOfUnknowns) {
        double[][] coefficientsOfUnknowns = new double[numberOfEquations][numberOfUnknowns];
        double[] freeCoefficients = new double[numberOfEquations];
        for (int i = 0; i<numberOfEquations ; ++i) {
            for (int j = 0; j < numberOfUnknowns; ++j) {
                    if (Math.random()*10>5)
                        coefficientsOfUnknowns[i][j] = Double.parseDouble(String.format("%.1f",Math.random() * 10 + 1).replace(",","."));
                    else coefficientsOfUnknowns[i][j] = Double.parseDouble(String.format("%.1f",Math.random() * -10 - 1).replace(",","."));
            }
            if(Math.random()*10>5)
                freeCoefficients[i] = Double.parseDouble(String.format("%.1f", Math.random() * 30).replace(",","."));
            else freeCoefficients[i] = Double.parseDouble(String.format("%.1f", Math.random() * -30).replace(",","."));
        }
        return new Matrix(coefficientsOfUnknowns, freeCoefficients, numberOfEquations, numberOfUnknowns);
    }
}
