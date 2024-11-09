package OperationGenerator;

import java.util.Scanner;

/**
 * MathOperation
 */
public class MathOperation {
    public static void main(String[] args) {
        OperationGenerator generator = new OperationGenerator();
        int operandsToGen = 2;

        Scanner scan = new Scanner(System.in);

        for (int level = 1; level <= 10; level++) {
            Operation op = generator.generateOperation(level, operandsToGen);
            System.out.println("Level " + level + ": " + op + " = ?");

            System.out.print("Respuesta: ");
            String actual = String.format("%.2f", scan.nextDouble());
            String expected = String.format("%.2f", op.getResult());
            if (!actual.equals(expected)) {
                System.out.println(String.format("\t%.2f", op.getResult()));
            }
            else
            {
                System.out.println("\t¡Correcto!");
            }
            System.out.println();
        }
        
        scan.close();
    }

}