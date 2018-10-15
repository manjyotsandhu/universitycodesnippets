import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import client.app.logic.Ingredient;
import client.app.logic.MenuItem;
import client.app.logic.Order;

/**
 * @author Ghadah.
 *
 */
public class OrderTest {



  MenuItem item;
  MenuItem item2;
  ArrayList<MenuItem> mitem;
  Order order;
  long date;

  /**
   * .
   */
  @Before
  public void setUp() throws Exception {
    Ingredient[] list = {new Ingredient("tomato", false, 10), new Ingredient("lettuce", false, 12),
        new Ingredient("meat", true, 100)};
    Ingredient[] list2 = {new Ingredient("tomato", false, 10), new Ingredient("lettuce", false, 12),
        new Ingredient("cucumber", true, 11)};

    item = new MenuItem(0, "Burger", 10.25, list);
    item2 = new MenuItem(1, "salad", 8.50, list2);
    mitem = new ArrayList<MenuItem>();
    order = new Order(item, 1, 1);
    date = order.getOrderTime();
  }

  @Test
  public void testModifyOrder() {

    order.modifyOrder(0, item2);
    assertEquals("TEST1: checks that order is changed", item2.getName(),
        order.getOrder().get(0).getName());
  }

  @Test
  public void testSetStat() {
    order.setStatus("Pending", 1);
    assertEquals("TEST2: tests both set, get methods", "Pending", order.getStatus());
  }

  @Test
  public void testCancelOrder() {
    order.cancelOrder(0);
    assertEquals("TEST3: test remove method", 0, order.getOrder().size());

  }

  @Test
  public void testConfirmOrder() {
    order.confirmOrder(0);
    assertTrue("TEST4: test confirm method", order.isConfirmed);
  }

  @Test
  public void testGetOrderTime() {
    assertEquals("TEST5: getting the total time ", date, order.getOrderTime());

  }

  @Test
  public void testOrderTotal() {
    assertEquals("TEST6: getting the total price ", item.getPrice(), order.getOrderTotal(), 0.2);

  }

  @Test
  public void testOrder() {
    mitem.add(item);
    mitem.add(item2);
    Order order2 = new Order(mitem, 2, 2);
    assertEquals("TESE7: creates order object by passing an arrayList of menu items",
        order2.getOrder(), mitem);
  }

}
