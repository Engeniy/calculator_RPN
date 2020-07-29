package strategy.calculator.impl;

import strategy.calculator.ReversePolishNotationCalculator;

import java.util.Stack;

import static strategy.util.UtilsToRPN.getPriority;

public class DefaultReversePolishNotationCalculatorImpl implements ReversePolishNotationCalculator {

    @Override
    public double calculate(String expression) {
        StringBuilder operand;
        Stack<Double> stack = new Stack<>();

        for (int i = 0; i < expression.length(); i++) {
            if (expression.charAt(i) == ' ')
                continue;

            if (getPriority(expression.charAt(i)) == 0) {
                operand = new StringBuilder();
                while (expression.charAt(i) != ' ' && getPriority(expression.charAt(i)) == 0) {
                    operand.append(expression.charAt(i++));
                    if (i == expression.length())
                        break;
                }
                stack.push(Double.parseDouble(operand.toString()));
            }

            if (getPriority(expression.charAt(i)) > 1) {
                double a = stack.pop();
                double b = stack.pop();

                if (expression.charAt(i) == '+') stack.push(b + a);
                if (expression.charAt(i) == '-') stack.push(b - a);
                if (expression.charAt(i) == '*') stack.push(b * a);
                if (expression.charAt(i) == '/') stack.push(b / a);
                if (expression.charAt(i) == '^') stack.push(Math.pow(b, a));
            }
        }
        return stack.pop();
    }
}
