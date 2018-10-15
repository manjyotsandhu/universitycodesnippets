package zbvc.cs2800;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestRevPolishCalc {

  private Calculator calc;

  @Before
  public final void setUp() throws Exception {
    this.calc = new RevPolishCalc();
  }

  // Made Calculator and RevPolishCalc class. Created evaluate method in calc
  // and rev polish calc. Changed calculator to interface. Creation of invalid
  // expression exception.
  @Test(expected = InvalidExpressionException.class)
  public void testEmptyExpression()
      throws InvalidExpressionException, BadType, StackEmpty {
    this.calc.evaluate("");
  }

  // Return 5 from evaluate method
  @Test
  public void oneNumEvaluation() throws BadType, StackEmpty {
    try {
      assertEquals("Test evaluation with one number", 5.0f,
          this.calc.evaluate("5"), 0f);
    } catch (InvalidExpressionException exception) {
      Assert.fail(exception + " Not seen as a valid expression");
    }
  }

  // Pushed input onto stack and popped off
  @Test
  public void zeroEvaluation()
      throws InvalidExpressionException, BadType, StackEmpty {
    assertEquals("Test evaluation with one number", 0.0f,
        this.calc.evaluate("0"), 0f);
  }

  // Created algorithm in evaluate method to take in the string input, push onto
  // stack if float. If symbol, identify which symbol (using a created toSymbol
  // method) and carry out correct calculation, using cases. Modified stack
  // class to fix previous error.
  @Test
  public void twoNumberAddition()
      throws InvalidExpressionException, BadType, StackEmpty {
    assertEquals("Test addition of two numbers", 7.0f,
        this.calc.evaluate("5 2 +"), 0f);
  }

  // Added switch/case statements for rest of symbols
  @Test
  public void sumTests()
      throws InvalidExpressionException, BadType, StackEmpty {
    assertEquals("Test for subtraction", 3.0f, this.calc.evaluate("5 2 -"), 0f);
    assertEquals("Test for division", 5.0f, this.calc.evaluate("10 2 /"), 0f);
    assertEquals("Test for multiplication", 6.0f, this.calc.evaluate("3 2 *"),
        0f);
  }

  @Test
  public void longSums()
      throws InvalidExpressionException, BadType, StackEmpty {
    assertEquals("First long sum test", 7.0f,
        this.calc.evaluate("10 3 + 4 + 8 - 2 -"), 0f);
    assertEquals("Second long sum test", -20.0f,
        this.calc.evaluate("0 1 - 4 5 * *"), 0f);
  }

  // Added if statement to throw exception if size of stack is less than 2, when
  // reaching an operator.
  @Test(expected = InvalidExpressionException.class)
  public void oneOperator()
      throws InvalidExpressionException, BadType, StackEmpty {
    this.calc.evaluate("4 *");
  }
}
