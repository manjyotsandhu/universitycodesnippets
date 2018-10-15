package zbvc.cs2800;

import java.util.Scanner;

public class RevPolishCalc implements Calculator {

  private NumStack evaluateStack;

  @SuppressWarnings("resource")
  @Override
  public float evaluate(String expression)
      throws InvalidExpressionException, BadType, StackEmpty {

    // new evaluate stack and input
    this.evaluateStack = new NumStack();
    Scanner expressionInput = new Scanner(expression);

    // if expression is empty, throw exception
    if (expression == null | expression.equals("")) {
      throw new InvalidExpressionException(
          "Cannot evaulate a string that is either null or empty.");
    }

    // while there is another entry in expression
    while (expressionInput.hasNext()) {

      // if next token is a float, push onto stack
      if (expressionInput.hasNextFloat()) {
        this.evaluateStack.push(expressionInput.nextFloat());
      } else { // if symbol
        // convert string to symbol
        Symbol token = toSymbol(expressionInput.next());
        
        // invalid expression
        if (this.evaluateStack.getSize() < 2) {
          throw new InvalidExpressionException("Invalid expression entered");
        }
        
        switch (token) {
          case PLUS:
            this.evaluateStack
                .push(this.evaluateStack.pop() + this.evaluateStack.pop());
            break;
          case DIVIDE:
            float firstPopDivide = this.evaluateStack.pop();
            float secondPopDivide = this.evaluateStack.pop();
            this.evaluateStack.push(secondPopDivide / firstPopDivide);
            break;
          case MINUS:
            float firstPopMinus = this.evaluateStack.pop();
            float secondPopMinus = this.evaluateStack.pop();
            this.evaluateStack.push(secondPopMinus - firstPopMinus);
            break;
          case TIMES:
            this.evaluateStack
                .push(this.evaluateStack.pop() * this.evaluateStack.pop());
            break;
          default:
            break;
        }
      }
    }

    expressionInput.close();
    return this.evaluateStack.pop();

  }

  private Symbol toSymbol(String wordSymbol) {
    Symbol returnedSymbol;
    switch (wordSymbol) {
      case "+": {
        returnedSymbol = Symbol.PLUS;
        break;
      }
      case "-": {
        returnedSymbol = Symbol.MINUS;
        break;
      }
      case "/": {
        returnedSymbol = Symbol.DIVIDE;
        break;
      }
      case "*": {
        returnedSymbol = Symbol.TIMES;
        break;
      }
      default: {
        returnedSymbol = Symbol.INVALID;
        break;
      }
    }
    return returnedSymbol;
  }

}
