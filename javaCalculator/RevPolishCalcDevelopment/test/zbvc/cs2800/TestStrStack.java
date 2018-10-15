package zbvc.cs2800;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class TestStrStack {

  private StrStack stk;

  @Before
  public void setUp() throws Exception {
    stk = new StrStack();
  }

  // Created StrStack class. Push, pop methods in StrStack class. Changed pop
  // method signature to return type String. Made a private stack. Modified
  // pop method to return the correct string, modified push method to push the
  // string as an entry
  @Test
  public void pushThenPopOnStack() throws BadType, StackEmpty {
    stk.push("Hello");
    assertEquals("Test 1: Pushed string onto stack, then pop", "Hello", stk.pop());
  }

  // Changed push method to add the parameter. Changed pop method to return the
  // stack entry, to string.
  @Test
  public void pushThenPopOnStackWithCorrectMethod() throws BadType, StackEmpty {
    stk.push("World");
    assertEquals("Test 2: Pushed string onto stack, then pop", "World", stk.pop());
  }

  // Added isEmpty method, set to return boolean true
  @Test
  public void emptyStackAndTestEmpty() throws BadType, StackEmpty {
    assertEquals("Test 3: Pushed string, then pop", true, stk.isEmpty());
  }

  // Changed isEmpty method to return if size is equal to 0
  @Test
  public void emptyStackReturnFalseIfNotEmpty() {
    stk.push("Hello");
    assertEquals("Test 4: Pushed string, tested if empty", false, stk.isEmpty());
  }

}
