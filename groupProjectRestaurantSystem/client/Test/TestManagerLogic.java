import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import client.app.logic.ManagerLogic;

/**
 * 
 */

/**
 * @author markharrison
 *
 */
public class TestManagerLogic {

  ManagerLogic testLogic;
  @Before
  public void setUp() throws Exception {
  
  testLogic = new ManagerLogic();
  testLogic.getStockList().size();
}
  // 1. Test that the Order status method returns a value
  @Test
  public void testOrderStatus() {
    assertEquals("Test that the method returns a value",testLogic.getOrderStatus(),3);
  }
  // 2. Test that the Outstanding order request method returns a value
  @Test
  public void testOutstandingOrderRequest(){
    
    assertEquals("Test that the outstanding order request method returns a value",testLogic.getoutstandingOrderRequests(),10);
    
  }
  // 3. Test that the stock levels method returns a value
  @Test
  public void testStockLevels(){
    assertEquals("Test that the stock level is returned correctly",testLogic.getStockLevels(),90);
    
    
  }
  // 4. Test that the table info method returns a valid value
  @Test
  public void testTableinfo(){
    
    assertEquals("Test the table info method returns a valid value",testLogic.getTableInfo(),50);
    
  
  }
  // 5. Testing the method returns a dummy int value
  // 6. Method changed to return an arraylist of values.
  @Test
  public void testConnection(){
    
    //assertEquals("Testing method for simple return value",testLogic.getStockLevelsData(),10);
    assertEquals("Testing method to return an arraylist of values", testLogic.getStockLevelsData(),10);
    
  }

}
