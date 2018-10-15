package zbvc.cs2800;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class TestNumStack {

  private NumStack num;

  @Before
  public void setUp() throws Exception {
    num = new NumStack();
  }

  // Created NumStack class, pop and push method. Modified pop method to return
  // correct float.
  @Test
  public void pushThenPopOntoNumStack() throws BadType, StackEmpty {
    num.push(3.0f);
    assertEquals("Test 1: Pushed then popped float onto stack", 3.0f, num.pop(), 0);
  }

  // Modified push method to call stack push method. Modified pop method to call
  // stack pop method.
  @Test
  public void pushPushPopOntoNumStack() throws BadType, StackEmpty {
    num.push(1.0f);
    num.push(2.0f);
    assertEquals("Test 2: Pushed float twice, then pop", 2.0f, num.pop(), 0);
  }

  // Created isEmpty method
  @Test
  public void emptyNumStackCheckEmpty() {
    assertEquals("Test 3: Checked if empty NumStack is empty", true, num.isEmpty());
  }

  // Modified isEmpty method to return true only if size is 0
  @Test
  public void unemptyNumStackCheckEmpty() {
    num.push(1.0f);
    assertEquals("Test 4: Checked if unempty NumStack is empty", false, num.isEmpty());
  }
}
