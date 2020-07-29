package strategy.util;

public class UtilsToRPN {
    public static int getPriority(char element) {
        switch (element) {
            case '^':
                return 4;
            case '*':
            case '/':
                return 3;
            case '+':
            case '-':
                return 2;
            case '(':
                return 1;
            case ')':
                return -1;
            default:
                return 0;
        }
    }
}
