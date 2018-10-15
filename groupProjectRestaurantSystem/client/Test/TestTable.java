import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import client.app.logic.Table;

public class TestTable {

  Table table, table2, table3;

  @Before
  public void setUp() throws Exception {

    table = new Table();
    table2 = new Table(90, 80, 70);
    table3 = new Table(100, 200);
  }

  // 1. Testing the default constructor.
  @Test
  public void testEmptyConstructor() {
    assertEquals("Test for new object creation", table.equals(null), false);
  }

  // 2. Testing the tableNum variable.
  @Test
  public void testTableNum() {
    assertEquals("Test tableNum variable", 0, table.getTableNum());

  }

  // 3. Testing the orderNum variable.
  @Test
  public void testOrderNum() {
    assertEquals("Test orderNum variable", 0, table.getOrderNum());

  }

  // 4. Testing the custNum variable.
  @Test
  public void testCustNum() {
    assertEquals("Test custNum variable", 0, table.getCustNum());

  }

  // 5. Testing the 3 parameter constructor.
  @Test
  public void testThreeParaConstructor() {

    assertEquals("Test the 3 parameter constructor custNum", table2.getCustNum(), 70);
    assertEquals("Test the 3 parameter constructor orderNum", table2.getOrderNum(), 80);
    assertEquals("Test the 3 parameter constructor tableNum", table2.getTableNum(), 90);
  }

  // 6. Testing the 2 parameter constructor.
  @Test
  public void testTwoParameterConstructor() {

    assertEquals("Test the 2 parameter constructor orderNum", table3.getOrderNum(), 200);
    assertEquals("Test the 2 parameter constructor tableNum", table3.getTableNum(), 100);

  }

  // 7. Testing the getter for tableNum.
  @Test
  public void testTableNumGetter() {

    assertEquals("Test the tableNum getter", 0, table.getTableNum());

  }

  // 8. Testing the getter for orderNum.
  @Test
  public void testOrderNumGetter() {

    assertEquals("Test the orderNum getter", 0, table.getOrderNum());

  }

  // 9. Testing the getter for custNum.
  @Test
  public void testCustNumGetter() {

    assertEquals("Test the custNum getter", 0, table.getCustNum());

  }

  // 10. Testing the setter for custNum.
  @Test
  public void testCustNumSetter() {
    table.setCustNum(8);
    assertEquals("Test the custNum setter", table.getCustNum(), 8);

  }

  // 11. Testing the setter for tableNum
  @Test
  public void testTableNumSetter() {

    table.setTableNum(10);
    assertEquals("Test the tableNum setter", table.getTableNum(), 10);
  }

  // 12. Testing the setter for orderNum.
  @Test
  public void testOrderNumSetter() {
    table.setOrderNum(999);
    assertEquals("Test the orderNum setter", table.getOrderNum(), 999);

  }

}
