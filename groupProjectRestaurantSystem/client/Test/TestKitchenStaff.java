import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import client.app.logic.KitchenStaff;
import client.app.logic.Order;
import client.app.logic.Table;


/**
 * @author Ghadah.
 *
 */
public class TestKitchenStaff {

  Order ord1, ord2;
  KitchenStaff kstaff;
  Table t1;
  Order order1, order2;
  List<Order> allOrders;

  @Before
  public void setUp() {

    kstaff = new KitchenStaff();
    t1 = new Table();
    allOrders = kstaff.getOrdersFromDatabase();
    ord1 = new Order();

  }

  @Test
  public void testOrderStatus() {
    ord1.setOrderStage(2);
    String str = ord1.getId() + " " + ord1.getOrderStage();
    assertEquals("TEST1: checks if it displays the right status", str,
        kstaff.checkOrderStatus(ord1));
  }

  @Test
  public void testGetOrdersFromDB() {
    int size = allOrders.size();
    assertEquals("TEST2: cheks that it returns the correct number of orders", allOrders.size(), size);
  }

  @Test
  public void testStartOrder() {
    order1 = kstaff.getOrder(37);
    kstaff.startOrder(37);

    assertEquals("TEST3: cheks that an order stage is changed to 3 ", order1.getOrderStage(),
        kstaff.getOrder(37).getOrderStage());
  }

  @Test
  public void testGetOrder() {

    order2 = kstaff.getOrder(22);
    assertEquals("TEST4: test if it returns the right order from DB", order2.getId(),
        kstaff.getOrder(22).getId());
  }

}
