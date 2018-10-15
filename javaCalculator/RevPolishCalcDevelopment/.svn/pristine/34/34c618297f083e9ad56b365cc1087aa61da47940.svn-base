package zbvc.cs2800;

import java.util.LinkedList;
import java.util.List;

/**
 * This class implements a program that will create a stack of entries. Includes
 * methods to pop and push entries, check top entry and size of stack.
 * 
 * @author zbvc854
 *
 */

public class Stack {

  /**
   * Number of entries in the stack.
   */
  private int stackSize;

  /**
   * Linked list to act as stack and store entries.
   */
  private List<Entry> stackList = new LinkedList<Entry>();

  /**
   * Method used to push an entry onto the stack. Will increase the stack size
   * by 1 and assign this new entry as the top of the stack, by storing it in a
   * variable.
   * 
   * @param entry
   *          This is the entry which will be added to the stack.
   */
  public void push(Entry entry) {
    stackList.add(entry);
    stackSize = stackSize + 1;
  }

  /**
   * This method will return the top most entry of the stack.
   * 
   * @return Entry Will return the entry from the top of the stack, by using the
   *         index number.
   * @throws StackEmpty
   *           Exception when call made to entry in empty stack.
   */
  public Entry top() throws StackEmpty {
    if (stackList.size() == 0) { // If the stack is empty, throw exception.
      throw new StackEmpty("Empty stack present, cannot carry out top method.");
    }
    return stackList.get(this.size() - 1);
  }

  /**
   * This method will return the size of the stack.
   * 
   * @return int Will return the stack size, from the correct integer stored in
   *         the variable.
   */
  public int size() {
    return stackSize;
  }

  /**
   * This method will remove the top most entry, decrease the stack size by one
   * and return the removed entry.
   * 
   * @return Entry Will return the removed entry.
   * @throws StackEmpty
   *           Exception when call made to entry in empty stack.
   */
  public Entry pop() throws StackEmpty {
    if (stackList.size() == 0) { // If stack is empty, throw exception.
      throw new StackEmpty("Empty stack present, cannot carry out pop method.");
    }
    // Remove top entry from stack
    Entry temp = stackList.remove(this.size() - 1);
    stackSize = stackSize - 1;
    return temp;
  }

}
