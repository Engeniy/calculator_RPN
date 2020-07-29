package strategy.converter;

public interface StringConverter {

    StringBuilder translationToRPN(String preparedExpression);

    String prepareExpression(String expression);
}
