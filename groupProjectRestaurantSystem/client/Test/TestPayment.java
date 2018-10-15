import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import client.app.logic.Payment;

/**
 * Test class for Payment class
 */

/**
 * @author markharrison
 *
 */
public class TestPayment {

  Payment payment;
  @Before
  public void setUp() throws Exception {
    payment = new Payment(12.599,300,"1234123443214321",340999,409,"John Smith");
  }

  // Test constructor
  @Test
  public void testConstructor() {
    payment = new Payment(12.5,300,"1234123443214321",340999,409,"John Smith");
    assertEquals("Test if object has been created",payment.getCvc(),409);
  }

  // Test Receipt
  @Test
  public void testReceiptFunction(){
    
    System.out.print(payment.Receipt());
    // Output is correct as shown in console.
    
    
  }
}
