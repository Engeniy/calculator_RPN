package strategy.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static strategy.util.UtilsToRPN.getPriority;

public class ValidatorExpression {

    public static boolean  isCorrectSymbol(String preparedExpression) {
        Pattern p = Pattern.compile("[0-9+\\-*/().^ ]+");
        Matcher m = p.matcher(preparedExpression);
        return m.matches();
    }

    public static boolean isCorrectDoubleNubmers(String preparedExpression) {
        int index = preparedExpression.indexOf('.');
        if (index == -1) {
            return true;
        } else {
            boolean point = true;
            for (int i = index + 1; i < preparedExpression.length(); i++) {

                if (preparedExpression.charAt(i) < '0' || '9' < preparedExpression.charAt(i)) {
                    if (preparedExpression.charAt(i) == '.') {
                        if (point) return false;
                        point = true;
                    } else point = false;
                }
            }
        }
        return true;
    }

    public static boolean isCorrectBracket(String expression) {
        int checkBracket = 0;
        for (int i = 0; i < expression.length(); i++) {
            String oneSymbol = expression.substring(i, i + 1);
            if (oneSymbol.equals("(")) {
                checkBracket++;
            } else if (oneSymbol.equals(")")) {
                checkBracket--;
            }
            if (checkBracket < 0) {
                return false;
            }
        }
        return checkBracket == 0;
    }

    public static boolean isCorrectStartOfLine(String preparedExpression) {
        String symbol = String.valueOf(preparedExpression.charAt(0));
        Pattern p = Pattern.compile("[-(0-9]");
        Matcher m = p.matcher(symbol);
        return m.matches();
    }

    public static boolean isCorrectCalculationSings(String expression) {
        int checkCalculationSings = 0;
        int priority;
        for (int i = 0; i < expression.length(); i++) {
            priority = getPriority(expression.charAt(i));
            if (priority > 1 && priority != 3) {
                checkCalculationSings++;
            } else {
                if (checkCalculationSings > -1) {
                    checkCalculationSings--;
                }
            }
            if (checkCalculationSings > 0) {
                return false;
            }
        }
        return checkCalculationSings < 0;
    }
}
