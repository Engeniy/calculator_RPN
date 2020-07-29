import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        try (Scanner scanner = new Scanner(System.in)) {
            ExpressionHandler expressionHandler = new ExpressionHandlerImpl();
            System.out.println("enter expression: ");
            String expression = scanner.nextLine();
            String result = expressionHandler.solveExpression(expression);
            System.out.println("result:");
            System.out.println(result);
        }
    }
}
