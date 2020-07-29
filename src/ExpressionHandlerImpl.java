import strategy.calculator.ReversePolishNotationCalculator;
import strategy.calculator.impl.DefaultReversePolishNotationCalculatorImpl;
import strategy.converter.StringConverter;
import strategy.converter.impl.StringConverterToRPNImpl;

import static constant.AnswerValidConstants.*;
import static strategy.validator.ValidatorExpression.*;

public class ExpressionHandlerImpl implements ExpressionHandler {

    private String preparedExpression;
    private ReversePolishNotationCalculator reversePolishNotationCalculator = new DefaultReversePolishNotationCalculatorImpl();
    private StringConverter stringConverter = new StringConverterToRPNImpl();


    @Override
    public String solveExpression(String expression) {

        try {
            preparedExpression = stringConverter.prepareExpression(expression);

            if (!isCorrectSymbol(preparedExpression)) {
                return NOT_CORRECT_CHARACTERS;
            }

            if (!isCorrectDoubleNubmers(preparedExpression)) {
                return NOT_CORRECT_DOUBLE_NUMBERS;
            }

            if (!isCorrectBracket(preparedExpression)) {
                return NOT_CORRECT_BRACKET;
            }

            if (!isCorrectStartOfLine(preparedExpression)) {
                return NOT_CORRECT_START_OF_LINE;
            }

            if (!isCorrectCalculationSings(preparedExpression)) {
                return NOT_CORRECT_CALCULATION_SINGS;
            }

            String rpn = String.valueOf(stringConverter.translationToRPN(preparedExpression));

            System.out.println("reverse polish notation: ");
            System.out.println(rpn);

            String result = String.valueOf(reversePolishNotationCalculator.calculate(rpn));
            result = prepareResult(result);
            return result;

        } catch (Exception ex) {
            return NOT_CORRECT_DATA;
        }
    }

    private String prepareResult(String rpn) {
        if (rpn.length() - rpn.indexOf('.') == 2) {
            return rpn.replace(".0", "");
        }
        return rpn;
    }

}
