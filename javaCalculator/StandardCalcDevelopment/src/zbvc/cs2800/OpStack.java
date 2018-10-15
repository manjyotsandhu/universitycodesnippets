package zbvc.cs2800;

/**
 * This class implements a stack, only containing Symbol entries. There are push
 * and pop methods to add and remove entries from the stack. There is also an
 * isEmpty() method to see if the stack is empty.
 * 
 * @author zbvc854
 *
 */
public class OpStack {

  /**
   * Creation of a stack object, wherein the symbol entries will be contained.
   */
  private Stack stk = new Stack();

  /**
   * Method wherein a symbol will be added to the stack.
   * 
   * @param symbol
   *          The symbol to be added to the stack.
   */
  public void push(Symbol symbol) {
    stk.push(new Entry(symbol));
  }

  /**
   * A method to remove the topmost symbol from the stack and return it.
   * 
   * @return Symbol Will return the topmost symbol.
   * @throws BadType
   *           Exception called if incompatible type of entry used.
   * @throws StackEmpty
   *           Exception called if call made to an empty stack.
   */
  public Symbol pop() throws BadType, StackEmpty {
    return stk.pop().getSymbol();
  }

  /**
   * Method to check if the stack is empty.
   * 
   * @return boolean Returns true if the size of stack is 0, otherwise returns
   *         false.
   */
  public boolean isEmpty() {
    return stk.size() == 0;
  }

}
