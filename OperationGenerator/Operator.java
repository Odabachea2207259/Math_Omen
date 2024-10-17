public class Operator {
    public static char getOperatorForLevel(int level) {
        switch (level) {
            case 1:
            case 2:
                return Math.random() > 0.5 ? '+' : '-';
            case 3:
                return '*';
            case 4:
                return '/';
            case 5:
            case 6:
                return Math.random() > 0.5 ? '+' : '-';
            case 7:
            case 8:
                return Math.random() > 0.5 ? '*' : '/';
            case 9:
                return '^';
            case 10:
                return Math.random() > 0.5 ? '*' : '/';
            default:
                return '+';
        }
    }
}
