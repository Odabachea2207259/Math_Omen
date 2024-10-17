/**
 * MathOperation
 */
public class MathOperation {
    public static void main(String[] args) {
        OperationGenerator generator = new OperationGenerator();
        int operandsToGen = 2;

        for (int level = 1; level <= 10; level++) {
            if (level >= 5) {
                operandsToGen = 3;
            }
            Operation op = generator.generateOperation(level, operandsToGen);
            System.out.println("Level " + level + ": " + op);
        }
    }

}