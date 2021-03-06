package zbvc.cs2800;

/**
 * This class implements a program that will create a stack of only floats.
 * Includes methods to pop, push and check if the stack is empty.
 * 
 * @author zbvc854
 * 
 */

public class NumStack {

  /**
   * Stack to hold float entries only.
   */
  private Stack stk = new Stack();

  /**
   * Method to call push stack method from Stack class, only pushes floats onto
   * stack.
   * 
   * @param number
   *          This is the float that will be pushed onto the stack.
   */
  public void push(float number) {
    stk.push(new Entry(number));
  }

  /**
   * Method to call pop method from stack, which removes the topmost element and
   * returns.
   * 
   * @return float Will return the value of the topmost element on the stack,
   *         which will be a float.
   * @throws BadType
   *           Exception when incompatible type used in stack.
   * @throws StackEmpty
   *           Exception when call is made to an empty stack.
   */
  public float pop() throws BadType, StackEmpty {
    return stk.pop().getValue();
  }

  /**
   * Method to check if the stack is empty.
   * @return boolean Will return true if the stack is empty.
   */
  public boolean isEmpty() {
    return stk.size() == 0;
  }
  
  public int getSize() {
    return stk.size();
  }

}
