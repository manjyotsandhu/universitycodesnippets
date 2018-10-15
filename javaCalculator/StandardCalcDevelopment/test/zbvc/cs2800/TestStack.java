package zbvc.cs2800;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * Contains all test for the Stack class.
 * 
 * @author zbvc854
 *
 */

public class TestStack {

  /**
   * Variable of type stack to store the test Stack.
   */
  private Stack testStack;

  @Before
  public void setUp() throws Exception {
    testStack = new Stack();
  }

  /**
   * Tests if an integer is pushed onto the stack and top integer can be
   * checked.
   * 
   * @throws StackEmpty
   *           Exception if a call is made to an entry in an empty stack.
   */
  // Test 1 - Created push and top method in Stack class. Changed return type of
  // top to int. Changed the return from null to desired value (3).
  // @Test
  // public void pushOntoStackThenTop() throws StackEmpty {
  // testStack.push(3);
  // assertEquals("TEST 1: Pushes 3 onto stack, checks top and returns 3", 3,
  // testStack.top());
  // }

  /**
   * Tests if an integer is pushed onto the stack and the correct size is
   * returned.
   * 
   * @throws StackEmpty
   *           Exception if a call is made to an entry in an empty stack.
   */
  // Test 2 - Created size method in Stack class. Changed return type of size
  // method
  // to int and changed return value from null to 1.
  // @Test
  // public void pushOntoStackThenSize() {
  // testStack.push(3);
  // assertEquals("TEST 2: Pushed 4 onto stack, checks size and returns 1", 1,
  // testStack.size());
  // }

  /**
   * Tests if an integer is pushed onto stack and removed, with pop.
   * 
   * @throws StackEmpty
   *           Exception if a call is made to an entry in an empty stack.
   */
  // Test 3 - Created pop method, changed return type to int, changed return
  // value from null to 3
  // @Test
  // public void pushOntoStackThenPop() throws StackEmpty {
  // testStack.push(3);
  // assertEquals("TEST 3: Pushed 3 onto stack, returns and removes top", 3,
  // testStack.pop());
  // }

  /**
   * Tests if two integers are pushed onto stack and returns correct size.
   * 
   * @throws StackEmpty
   *           Exception if a call is made to an entry in an empty stack.
   */
  // Test 4 - Created size variable in Stack method, altered size method
  // to return the variable value stackSize. Altered push method to add 1
  // to stackSize variable each time method is run.
  // @Test
  // public void pushThenReturnsCorrectSize() {
  // testStack.push(3);
  // testStack.push(3);
  // assertEquals(
  // "TEST 4: Pushes two integers onto the stack, returns 2 as size", 2,
  // testStack.size());
  // }

  /**
   * Tests if two integers can be pushed onto a stack, one popped and the
   * correct stack size returned.
   * 
   * @throws StackEmpty
   *           Exception if a call is made to an entry in an empty stack.
   */
  // Test 5 - Altered pop method to decrease size variable by 1.
  // @Test
  // public void pushThenPopReturnsCorrectSize() throws StackEmpty {
  // testStack.push(3);
  // testStack.push(3);
  // testStack.pop();
  // assertEquals("TEST 5: Pushed two integers, pop stack, returns 1 as size",
  // 1,
  // testStack.size());
  // }

  /**
   * Tests if top method returns the correct integer.
   * 
   * @throws StackEmpty
   *           Exception if a call is made to an entry in an empty stack.
   */
  // Test 6 - Created new variable for top element called topInteger.
  // Modified push method to assign the parameter input as topInteger.
  // Modified top method to return topInteger value.
  // @Test
  // public void pushThenTopReturnsCorrectInteger() throws StackEmpty {
  // testStack.push(5);
  // assertEquals("TEST 6: Pushed integer then top, returns 5", 5,
  // testStack.top());
  // }

  /**
   * Tests if a pop can occur and the top method still returns the correct
   * integer.
   * 
   * @throws StackEmpty
   *           Exception if a call is made to an entry in an empty stack.
   */
  // Test 7 - Created linked list variable in Stack class. Modified
  // push method to include an 'add' method, to add an int to the list.
  // @Test
  // public void pushThenPopThenTopReturnsCorrectValue() throws StackEmpty {
  // testStack.push(8);
  // testStack.push(9);
  // testStack.pop();
  // assertEquals("TEST 7: Pushed two integers, pop, then check top value which"
  // + "should return 8", 8, testStack.top());
  // }

  /**
   * Tests if the modified pop method will still return the top integer.
   * 
   * @throws StackEmpty
   *           Exception if a call is made to an entry in an empty stack.
   */
  // Test 8 - Modified pop method to return top integer.
  // @Test
  // public void pushThenPopReturnsCorrectValueFromList() throws StackEmpty {
  // testStack.push(10);
  // assertEquals("TEST 8: Pushed integer, then pop should return 10", 10,
  // testStack.pop());
  // }

  /**
   * Tests if exception is thrown when top is called to an empty stack.
   * 
   * @throws StackEmpty
   *           Exception if a call is made to an entry in an empty stack.
   */
  // Test 9 - Created StackEmptpy class and extended it. Created default
  // exception constructor and additional constructor,
  // with parameter of a string message. Extended each test, that used the top
  // method, to throw the exception. In top() method, added a throw StackEmpty
  // to method signature. Added an if statement: If stack size is equal to 0,
  // throw the StackEmpty exception. Otherwise, return the int at top of stack.
  @Test(expected = StackEmpty.class)
  public void topWithAnEmptyStack() throws StackEmpty {
    testStack.top();
  }

  /**
   * Tests if exception is throw when pop method is called to an empty stack.
   * 
   * @throws StackEmpty
   *           Exception if a call is made to an entry in an empty stack.
   */
  // Test 10 - Added a throw StackEmpty to the pop method. Added an if
  // statement: If stack size equal to 0, throw empty stack exception.
  // Otherwise, continue with normal pop method procedure. Added throw
  // StackEmpty to other tests using pop method.
  @Test(expected = StackEmpty.class)
  public void popWithAnEmptyStack() throws StackEmpty {
    testStack.pop();
  }

  /**
   * Test if stack and push/top methods operate as normal, with entries instead
   * of integers.
   * 
   * @throws StackEmpty
   *           Exception if a call is made to an entry in an empty stack.
   */
  // Test 11 - Modified all methods that returned an int to return an entry
  // instead, excluding stack size. Changed topInteger from an integer to type
  // Entry. Changed the List from type Integer to type Entry.
  @Test
  public void pushWithFloatEntryAndTop() throws StackEmpty {
    Entry ent = new Entry(4.0f);
    testStack.push(ent);
    assertEquals("TEST 11: Push float entry onto stack and check top", ent,
        testStack.top());
  }

  /**
   * Tests if stack and push/pop methods operate as normal, with entries instead
   * of integers.
   * 
   * @throws StackEmpty
   *           Exception if a call is made to an entry in an empty stack.
   */
  // Test 12 - No changes in code, test to check if recent change from integers
  // to entry has effected pop method.
  @Test
  public void pushWithFloatEntryAndPop() throws StackEmpty {
    Entry ent = new Entry(4.0f);
    testStack.push(ent);
    assertEquals("TEST 12: Push float entry onto stack and pop", ent,
        testStack.pop());
  }
}
