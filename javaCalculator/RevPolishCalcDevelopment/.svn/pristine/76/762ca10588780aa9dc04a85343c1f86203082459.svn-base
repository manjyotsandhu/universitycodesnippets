package zbvc.cs2800;

/**
 * This class implements a stack, containing only String entries. Has pop, push
 * and isEmpty methods.
 * 
 * @author zbvc854
 *
 */
public class StrStack {

  /**
   * Creation of a stack, which will contain the string entries.
   */
  private Stack stk = new Stack();

  /**
   * Method to add a string entry onto the stack.
   * 
   * @param string
   *          The string that will be pushed onto the stack.
   */
  public void push(String string) {
    stk.push(new Entry(string));
  }

  /**
   * Method to remove the topmost string from the stack and return it.
   * 
   * @return string Returns the topmost string contents from the stack
   * @throws BadType
   *           Exception called if incompatible type used for stack
   * @throws StackEmpty
   *           Exception called if call made to empty stack
   */
  public String pop() throws BadType, StackEmpty {
    return stk.pop().getString();
  }

  /**
   * Method to check if the stack has no entries.
   * 
   * @return boolean Returns true if the stack is empty, otherwise false.
   */
  public boolean isEmpty() {
    return stk.size() == 0;
  }

}
