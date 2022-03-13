public class Matrix {

    public Matrix(double[][] coefficientsOfUnknowns, double[] freeCoefficients, int numberOfEquations, int numberOfUnknowns) {
        this.coefficientsOfUnknowns = coefficientsOfUnknowns;
        this.freeCoefficients = freeCoefficients;
        this.numberOfEquations = numberOfEquations;
        this.numberOfUnknowns = numberOfUnknowns;
    }

    private double[][] coefficientsOfUnknowns;
    private double[] freeCoefficients;
    private int numberOfEquations;
    private int numberOfUnknowns;


    public double[][] getCoefficientsOfUnknowns() {
        return this.coefficientsOfUnknowns;
    }

    public double[] getFreeCoefficients() {
        return this.freeCoefficients;
    }

    public int getNumberOfEquations() { return numberOfEquations; }
    public int getNumberOfUnknowns() { return numberOfUnknowns; }

    @Override
    public String toString(){
        String matrix = "";
        for (int i = 0; i<numberOfEquations ; ++i) {
            for (int j = 0; j < numberOfUnknowns; ++j) {
                matrix+=String.format("%.2f",this.coefficientsOfUnknowns[i][j])+"\t";
            }
            matrix+=" |"+String.format("%.2f",freeCoefficients[i])+"\n";
        }
        return matrix;
    }
}
