public class Operation {
    private double[] operands;
    private char[] operators;
    private double result;

    public Operation(double[] operands, char[] operators) {
        this.operands = operands;
        this.operators = operators;
        this.result = calculateResult();
    }

    private double calculateResult() {
        double currentResult = operands[0];

        for (int i = 0; i < operators.length; i++) {
            switch (operators[i]) {
                case '+':
                    currentResult += operands[i + 1];
                    break;
                case '-':
                    currentResult -= operands[i + 1];
                    break;
                case '*':
                    currentResult *= operands[i + 1];
                    break;
                case '/':
                    currentResult /= operands[i + 1];
                    break;
                case '^':
                    currentResult = (double) Math.pow(currentResult,
                            operands[i + 1]);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid Operator");
            }
        }

        return currentResult;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(operands[0]); // Add first operand

        // Add each operand and its respective operation
        for (int i = 0; i < operators.length; i++) {
            sb.append(" ").append(operators[i]).append(" ").append(operands[i + 1]);
        }
        return sb.toString();
    }

    public double getResult() {
        return result;
    }
}