import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import client.app.UI.UIPayment;
import client.app.logic.Ingredient;
import client.app.logic.MenuItem;
import client.app.logic.Order;
import client.app.logic.Table;
import client.app.logic.Waiter;

public class TestWaiter {
 
  private Waiter nigel;
  private Order order1;
  
  @Before
  public void setUp() throws Exception {
    nigel = new Waiter(1,"Nigel");
	order1 = new Order();
    }
  
  /**
   * AS A WAITER I WANT TO CONFIRM AN ORDER
   */
  @Test
  public void confirmOrder() {
	order1=nigel.getOrder(16); //Get order from web service that has id of 1
	nigel.resetOrder(order1); //Reset the order stage
    assertEquals("TEST 1:Order originally unconfirmed",1,order1.getOrderStage()); //Ensure the order stage is 1-'Unconfirmed'
    nigel.confirmOrder(order1); //confirms order by setting order stage to 2 on web service
    assertEquals("TEST 1:Confirm a customers order",2,order1.getOrderStage()); //now 2-'Confirmed'
  }

   /**
    * AS A WAITER I WANT TO CANCEL AN ORDER
    */
  @Test
  public void cancelOrder() {
	  order1=nigel.getOrder(16); //Get order from web service that has id of 16
	  nigel.resetOrder(order1); //Reset the order stage
	  assertEquals("TEST 2:Order originally unconfirmed",1,order1.getOrderStage()); //Ensure the order stage is 1-'Unconfirmed'
	  nigel.cancelOrder(order1);
	  assertEquals("TEST 2:Cancels a customers order setting orderstage to 0",0,order1.getOrderStage()); //now 0-'Cancelled'
   }
  /**
   * AS A WAITER I WANT TO MARK AN ORDER AS DELIVERED
   */
  
  @Test
  public void markAsDelivered() {
	  order1=nigel.getOrder(16); //Get order from web service that has id of 16
	  nigel.resetOrder(order1); //Reset the order stage
	  assertEquals("TEST 3:Order originally undelivered",1,order1.getOrderStage()); //Ensure the order stage is 1-'Unconfirmed'
	  nigel.deliverOrder(order1);
	  assertEquals("TEST 3:Order now marked as stage 5 delivered",5,order1.getOrderStage()); //now 5-'Delivered'
  }
  /**
   * AS A WAITER I WANT TO SEE WHICH TABLES HAVE RECIEVED THEIR ORDERS BUT ARE YET TO PAY
   */
  @Test
  public void checkIfPaid() {
	  UIPayment pay =new UIPayment(0.0,order1);
	  order1=nigel.getOrder(16); //Get order from web service that has id of 16
	  nigel.resetOrder(order1); //reset  order stage
	  assertEquals("Test 4",0.0,order1.getTotalPaid(),0.001);//check its unpaid
	  pay.postPayment(order1);//update total paid by posting payment to webserver
	  assertEquals("TEST 4",order1.getTotalCost(),order1.getTotalPaid(),0.001);//assert paid is the same as cost
   }
  
}
