package zbvc.cs2800;

import java.util.Scanner;

public class StandardCalc implements Calculator {

  private StrStack reverseStack;
  private StrStack symbolStack;
  private RevPolishCalc rpCalc = new RevPolishCalc();

  @Override
  public float evaluate(String expression)
      throws InvalidExpressionException, BadType, StackEmpty {

    this.reverseStack = new StrStack();
    this.symbolStack = new StrStack();
    StringBuilder returnValue = new StringBuilder();

    // if empty expression
    if (expression == null | expression.equals("")) {
      throw new InvalidExpressionException(
          "Cannot evaulate a string that is either null or empty.");
    }

    Scanner input = new Scanner(expression);

    // if expression has next
    while (input.hasNext()) {
      // push onto reverseStack
      this.reverseStack.push(input.next());
    }

    input.close();
    String nextEntry;
    boolean numberTurn = true;

    // while reverse stack has entries
    while (!this.reverseStack.isEmpty()) {

      nextEntry = this.reverseStack.pop();

      // if float and number is expected, append to return string
      if (Character.isDigit(nextEntry.charAt(0)) && numberTurn == true) {
        returnValue.append(String.valueOf(nextEntry) + " ");
        numberTurn = false; // set to expect symbol next
        continue;

      } else {
        //if there is a left bracket
        if (nextEntry.equals("(")) {
          System.out.println("left bracket");
          String nextOp = this.symbolStack.pop();
        }
        
        //if there is a right bracket
        symbolStack.push(nextEntry); // add symbol to symbol stack
        numberTurn = true; // set to expect number next

      }

    }

    // while symbols to add to expression
    while (!this.symbolStack.isEmpty()) {
      String nextSymbol = this.symbolStack.pop();
      returnValue.append(nextSymbol + " "); // add symbol to expression
    }

    System.out.println(returnValue.toString());
    return this.rpCalc.evaluate(returnValue.toString());
  }
  
  

}
