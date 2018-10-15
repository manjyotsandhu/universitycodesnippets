import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import client.app.logic.Ingredient;
import client.app.logic.MenuItem;

public class TestingMenuItems {

  private ArrayList<Ingredient> ingredients;
  private ArrayList<Ingredient> ingredients2;
  private MenuItem menuItem;
  private MenuItem menuItem2;

  @Before
  public void setUp() {
    Ingredient a = new Ingredient(1, "tomato", true, 30, "some allergy", 0);
    Ingredient b = new Ingredient(2, "beans", false, 10, "allergy", 0);
    Ingredient c = new Ingredient(3, "egg", true, 40, "allergy", 0);
    a.addQuantity(3);
    b.addQuantity(3);

    ingredients = new ArrayList<Ingredient>();
    ingredients.add(a);
    ingredients.add(b);
    ingredients2 = new ArrayList<Ingredient>();
    ingredients2.add(b);
    ingredients2.add(c);

    menuItem = new MenuItem(1, "Pizza", 24.00, ingredients);
    menuItem2 = new MenuItem(2, "Pasta", 12.00, ingredients2);
  }

  @Test
  public void testGetItemId() {
    assertEquals("Test to return correct menu ID", 1, menuItem.getId());
  }

  @Test
  public void testGetItemPrice() {
    assertEquals("Test to return correct price", 24.00, menuItem.getPrice(),
        0.1);
  }

  @Test
  public void testGetItemName() {
    assertEquals("Test to return correct name", "Pizza", menuItem.getName());
  }

  @Test
  public void testAvailability() {
    assertEquals("Test to return correct availability", true,
        menuItem.isAvailable());
  }

  @Test
  public void testNoAvailability() {
    assertEquals("Test to return availability", false, menuItem2.isAvailable());
  }

  @Test
  public void testCaloriesAfterAddedIngredients() {
    assertEquals("Test correct calories", 40, menuItem.getCalories());
  }

  @Test
  public void testVegetarian() {
    assertEquals("Returns correct vegetarian result", false,
        menuItem.isVegeDish());
  }
  
  @Test
  public void testAssignAllergy() {
	  assertEquals("Returns correct allergy list", " allergy, some allergy", menuItem.getAllergyInfo());
  }
  
  @Test
  public void testSameAssignAllergy() {
	  assertEquals("Returns correct allergy list", " allergy", menuItem2.getAllergyInfo());
  }
}