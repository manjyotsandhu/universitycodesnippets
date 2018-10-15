package zbvc.cs2800;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class TestStandardCalc {

  private Calculator calc;

  @Before
  public void setUp() throws Exception {
    this.calc = new StandardCalc();
  }

  // Creation of StandardCalc class and evaluate method. Created if statement to
  // identify a string is empty
  @Test(expected = InvalidExpressionException.class)
  public void testEmpty()
      throws InvalidExpressionException, BadType, StackEmpty {
    this.calc.evaluate("");
  }

  // Modified return statement to return float 0
  @Test
  public void zeroNum() throws InvalidExpressionException, BadType, StackEmpty {
    assertEquals("Input as number zero", 0f, this.calc.evaluate("0.0"), 0f);
  }

  // Creation of RevPolishCalc instance (rpCalc), added method to push each
  // entry onto a reverse stack. Added method to go through stack, and if float,
  // append to return string. Changed return statement to return this string.
  @Test
  public void oneNum() throws InvalidExpressionException, BadType, StackEmpty {
    assertEquals("Input as number one", 1f, this.calc.evaluate("1.0"), 0f);
  }
  
  @Test
  public void simpleAdditionCalculation() throws InvalidExpressionException, BadType, StackEmpty {
    assertEquals("Simple addition calculation", 5.0f, this.calc.evaluate("2 + 3"), 0f);
  }
  
  @Test
  public void otherSimpleCalculations() throws InvalidExpressionException, BadType, StackEmpty {
    assertEquals("Simple subtraction calculation", 4.0f, this.calc.evaluate("6 - 2"), 0f);
    assertEquals("Simple division calculation", 6.0f, this.calc.evaluate("12 / 2"), 0f);
    assertEquals("Simple multiplication calculation", 12.0f, this.calc.evaluate("3 * 4"), 0f);
  }
  
  @Test
  public void bracketCalcTest() throws InvalidExpressionException, BadType, StackEmpty {
    System.out.println("curr test");
    assertEquals("Bracket test", 3.0f, this.calc.evaluate("( 1 + 2 )"), 0f);
  }

}
