package zbvc.cs2800;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class TestOpStack {

  private OpStack stk;

  @Before
  public void setUp() throws Exception {
    stk = new OpStack();
  }

  // Created OpStack class. Created push and pop methods in OpStack class.
  // Created stack variable in OpStack class. Modified push method to push
  // Symbol onto stack. Modified pop method to return correct symbol.
  @Test
  public void pushThenPopOntoStack() throws BadType, StackEmpty {
    stk.push(Symbol.MINUS);
    assertEquals("Test 1: Pushed symbol onto stack, then pop", Symbol.MINUS, stk.pop());
  }

  // Changed push method to push the parameter onto the stack. Changed pop
  // method to call the pop method from stack and getSymbol().
  @Test
  public void pushThenPushThenPop() throws BadType, StackEmpty {
    stk.push(Symbol.PLUS);
    stk.push(Symbol.TIMES);
    assertEquals("Test 2: Pushed two symbols onto stack, then one pop", Symbol.TIMES, stk.pop());
  }

  // Created isEmpty method, changed return type to boolean and set to return
  // true.
  @Test
  public void emptyStackTest() {
    assertEquals("Test 3: Empty stack, test if empty", true, stk.isEmpty());
  }

  @Test
  public void pushThenEmptyStackTest() {
    stk.push(Symbol.DIVIDE);
    assertEquals("Test 4: Pushed symbol, test if empty stack", false, stk.isEmpty());
  }

}
