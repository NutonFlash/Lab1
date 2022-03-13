public class Main {
    public static void main(String[] args) {
        ComputationByMethodGauss computationByMethodGauss = new ComputationByMethodGauss();
        final Matrix matrix = new InputManager().getMatrix();
        if (matrix != null) {
            System.out.println("Расширенная матрица введенной СЛАУ:\n"+matrix);
            float determinant = computationByMethodGauss.calculateMatrixDetermination(matrix);
            if (determinant == 0)
                System.out.println("Определитель матрицы свободных коэффициентов равен нулю, поэтому СЛАУ является несовместной.");
            else {
                System.out.format("Определитель матрицы свободных коэффициентов равен %.3f\n", determinant);
                Matrix convertedMatrix = computationByMethodGauss.convertToTriangularMatrix(matrix);
                System.out.println("Треугольная матрица СЛАУ:\n" + convertedMatrix);
                double[] definitionOfUnknowns = computationByMethodGauss.findUnknownsOfEquations(convertedMatrix);
                System.out.println("Найденные неизвестные:");
                for (int i=0; i<definitionOfUnknowns.length ; ++i)
                    System.out.println("x"+(i+1)+": "+definitionOfUnknowns[i]);
                double[] faults = computationByMethodGauss.calculateFaults(matrix, definitionOfUnknowns);
                System.out.println("\n\n");
                System.out.println("Невязки уравнений:");
                for (int i=0; i<definitionOfUnknowns.length ; ++i)
                    System.out.println("Невязка "+(i+1)+"-ого уравнения: "+faults[i]);
            }
        } else {
            System.out.println("Упс... Что-то пошло не так.");
            System.exit(1);
        }
    }
}
