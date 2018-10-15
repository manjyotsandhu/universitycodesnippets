/**
 * 
 */
package project.groupx.restaurant.exceptions;


/**
 * @author Johannes Herforth.
 *
 */

public class DBEntryNotFound extends Exception {

  /**
   * Exception version.
   */
  
  private static final long serialVersionUID = 1L;

  public DBEntryNotFound(String errorMessage) {
    super(errorMessage);
  }
  
}
