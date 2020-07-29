package strategy.converter.impl;

import strategy.converter.StringConverter;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static constant.CalculatorConstants.BRACKET;
import static constant.CalculatorConstants.NUMBER;
import static strategy.util.UtilsToRPN.getPriority;

public class StringConverterToRPNImpl implements StringConverter {

    private String preparedExpression;

    @Override
    public StringBuilder translationToRPN(String preparedExpression) {

        boolean isNumberOrPoint = false;
        int priority;

        StringBuilder convertedExpression = new StringBuilder();
        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < preparedExpression.length(); i++) {
            priority = getPriority(preparedExpression.charAt(i));

            if (priority == 0) {
                if (isNumberOrPoint) {
                    convertedExpression.append(' ');
                    isNumberOrPoint = false;
                }
                convertedExpression.append(preparedExpression.charAt(i));
            }
            if (priority == 1) {
                if (isNumberOrPoint) {
                    convertedExpression.append(' ');
                    isNumberOrPoint = false;
                }
                stack.push(preparedExpression.charAt(i));
            }

            if (priority > 1) {
                isNumberOrPoint = true;
                while (!stack.empty()) {
                    if (getPriority(stack.peek()) >= priority) {
                        convertedExpression.append(' ');
                        convertedExpression.append(stack.pop());
                    } else break;
                }
                stack.push(preparedExpression.charAt(i));
            }

            if (priority == -1) {
                isNumberOrPoint = true;
                while (getPriority(stack.peek()) != 1) {
                    convertedExpression.append(' ');
                    convertedExpression.append(stack.pop());
                }
                stack.pop();
            }
        }
        while (!stack.empty()) {
            convertedExpression.append(' ');
            convertedExpression.append(stack.pop());
        }
        return convertedExpression;
    }

    @Override
    public String prepareExpression(String expression) {

        preparedExpression = expression.replaceAll(" ", "");

        char firstElement = preparedExpression.charAt(0);
        char secondElement = preparedExpression.charAt(1);

        if (firstElement == '-' && secondElement == '(') {
            methodForBracket(0, BRACKET);

        } else if (firstElement == '-') {
            methodForBracket(0, NUMBER);
        }

        if (preparedExpression.indexOf('(') != -1) {

            boolean isEndString = true;
            int index = 0;
            while (isEndString) {
                char element0 = preparedExpression.charAt(index);
                char element1 = preparedExpression.charAt(index + 1);
                char element2 = preparedExpression.charAt(index + 2);
                if (element0 == '(' && element1 == '-' && element2 == '(') {
                    methodForBracket(index + 1, 1);
                    index += 3;
                } else if (element0 == '(' && element1 == '-') {
                    methodForBracket(index + 1, 2);
                    index += 3;
                }
                index++;

                if (index >= preparedExpression.length() - 2)
                    isEndString = false;
            }
        }

        StringBuilder sb = new StringBuilder(preparedExpression);

        for (int i = 0; i < sb.length(); i++) {
            sb.reverse();
            if (sb.charAt(i) == '%') {
                int indexStartNumber = findReverseEndNumber(i + 1, sb);
                sb.insert(indexStartNumber, "(");
                sb.replace(i, i + 1, ")001/");
            }
            preparedExpression = sb.reverse().toString();
        }

        return preparedExpression;
    }

    private void methodForBracket(int index, int type) {
        StringBuilder sb = new StringBuilder(preparedExpression);
        int indexBacket = -1;
        if (type == BRACKET) {
            indexBacket = findLastBracketForExponentiation(index + 1, preparedExpression) + 1;
        }
        if (type == NUMBER) {
            indexBacket = findEndNumber(index + 1, preparedExpression);
        }
        sb.insert(indexBacket, ")");
        sb.insert(index, "(0");
        preparedExpression = sb.toString();
    }

    private int findEndNumber(int index, String expression) {
        Pattern pattern = Pattern.compile("[^0-9.]");
        Matcher matcher = pattern.matcher(expression);
        return matcher.find(index) ? matcher.start() : expression.length();
    }

    private int findReverseEndNumber(int index, StringBuilder sb) {
        Pattern pattern = Pattern.compile("[^0-9.]");
        Matcher matcher = pattern.matcher(sb);
        return matcher.find(index) ? matcher.start() : sb.length();
    }

    private int findLastBracketForExponentiation(int index, String expression) {
        int checkBracket = 0;

        for (int i = index; i < expression.length(); i++) {
            char oneSymbol = expression.charAt(i);
            if (oneSymbol == '(') {
                checkBracket++;
            } else if (oneSymbol == ')') {
                checkBracket--;
            }
            if (checkBracket == 0) {
                return i;
            }
        }
        return -1;
    }
}
