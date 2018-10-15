import static org.junit.Assert.*;

import org.junit.Test;

import client.app.logic.Ingredient;

public class TestIngredient {
  
  Ingredient chicken = new Ingredient(1,"chicken",true,150,"may contain nuts", 20170606, 0);  
    
  @Test
  public void testConstructor() {
    chicken = new Ingredient(1,"chicken",true,150,"may contain nuts", 20170606, 0);
    assertNotNull(chicken);
  }
  
  @Test
  public void testGetId() {
    assertEquals(1,chicken.getId());
  }
  
  @Test
  public void testGetAllergy() {
    assertEquals("may contain nuts", chicken.getAllergy());
  }
  
  @Test 
  public void testGetExpiryDate() {
    assertEquals(20170606, chicken.getExpiryDate());
  }
  
  @Test
  public void testGetName() {
    assertEquals("chicken", chicken.getName());
  }
  
  @Test
  public void testGetQuantity() {
    assertEquals(0,chicken.getQuantity());
  }
  
  @Test
  public void testAddQuantity() {
    chicken.addQuantity(50);
    assertEquals(50,chicken.getQuantity());
  }
  
  @Test
  public void testRemoveQuantity() {
    chicken.addQuantity(50);
    chicken.removeQuantity(25);
    assertEquals(25,chicken.getQuantity());
  }
  
  @Test
  public void testSetandGetCost() {
    chicken.setCost(50.50);
    assertEquals(50.50, chicken.getCost(), 0.01);
  }
  
}
