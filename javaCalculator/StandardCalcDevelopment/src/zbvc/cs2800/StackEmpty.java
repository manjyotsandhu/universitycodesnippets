package zbvc.cs2800;

/**
 * Exception thrown when a call is made to an empty stack.
 * 
 * @author zbvc854
 *
 */

@SuppressWarnings("serial")
public class StackEmpty extends Exception {

  /**
   * Default constructor for exception.
   */
  public StackEmpty() {
  }

  /**
   * Constructor which takes a string message to be printed to the screen.
   * 
   * @param message
   *          Message to be displayed on screen.
   */
  public StackEmpty(String message) {
    System.out.println(message);
  }

}
