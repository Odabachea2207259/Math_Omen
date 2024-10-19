import java.util.Random;

public class OperationGenerator {
    private Random rand = new Random();

    public double generateNumber(int upperLimit) {
        return rand.nextInt(upperLimit) + 1;
    }

    public Operation generateOperation(int level, int n) {
        double[] operands = new double[n];
        char[] operators = new char[n - 1];

        // Change difficulty by level
        for (int i = 0; i < n; i++) {
            if (level <= 6) {
                operands[i] = generateNumber(10);
            } else if (level == 7) {
                operands[i] = generateNumber(15);
            } else if (level == 8 || level == 10) {
                operands[i] = generateNumber(20);
            } else if (level == 9) {
                operands[i] = rand.nextInt(4);
            } else {
                operands[i] = generateNumber(10);
            }
        }

        for (int i = 0; i < n - 1; i++) {
            operators[i] = Operator.getOperatorForLevel(level);
            if (operators[i] == '/' && operands[i + 1] == 0) {
                operands[i + 1] = 1; // Handle division by zero
            }
        }

        return new Operation(operands, operators);
    }
}
