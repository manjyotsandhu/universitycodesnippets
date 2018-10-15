package zbvc.cs2800;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import zbvc.cs2800.Entry;
import zbvc.cs2800.Symbol;
import zbvc.cs2800.Type;

/**
 * Test class which contains all test for the Entry class.
 * 
 * @author zbvc854
 *
 */
public class TestEntry {

  /**
   * Variable to store an entry object.
   */
  private Entry ent;

  /**
   * Stores another entry object, for comparison test purposes.
   */
  private Entry ent2;

  /**
   * Tests a set value is returned from the constructor.
   * 
   * @throws BadType
   *           Exception if requested get method return type does not match type
   *           of object.
   */

  @Before
  public void setUp() throws Exception {

  }

  // Test 1- Set up constructor that accepted float. Set up getValue method
  // which returned 5.0f.
  @Test
  public void setUpFloatConstructorTest() throws BadType {
    ent = new Entry(5.0f);
    assertEquals("TEST1: Returned value from constructor is a float", 5.0f,
        ent.getValue(), 0);
  }

  /**
   * Tests a set symbol is returned from the constructor.
   * 
   * @throws BadType
   *           Exception if requested get method return type does not match type
   *           of object.
   */
  // Test 2 - Set up constructor that accepted Symbol. Set up Symbol with MINUS
  // enumeration and getSymbol method which returned symbol MINUS.
  @Test
  public void setUpSymbolConstructorTest() throws BadType {
    ent = new Entry(Symbol.MINUS);
    assertEquals("TEST2: Returned value from constructor is a symbol",
        Symbol.MINUS, ent.getSymbol());
  }

  /**
   * Tests a set string is returned from the constructor.
   * 
   * @throws BadType
   *           Exception if requested get method return type does not match type
   *           of object.
   */
  // Test 3 - Set up constructor that accepted String. Set up getString method
  // which returned "Hello".
  @Test
  public void setUpStringConstructorTest() throws BadType {
    ent = new Entry("Hello");
    assertEquals("TEST3: Returned value is a string", "Hello", ent.getString());
  }

  /**
   * Tests if returned type of object is correct.
   */
  // Test 4 - Set up Type enumeration class with SYMBOL enumeration. Set up
  // getType() method which returned type SYMBOL
  @Test
  public void getTypeTest() {
    ent = new Entry(Symbol.MINUS);
    assertEquals("TEST4: Returned value is a type", Type.SYMBOL, ent.getType());
  }

  /**
   * Tests if constructor and get value method returns the correct float.
   * 
   * @throws BadType
   *           Exception if requested get method return type does not match type
   *           of object.
   */
  // Test 5 - Set up getValue method to return the constructor parameter
  @Test
  public void setUpConstructorAndGetValue() throws BadType {
    ent = new Entry(4.0f);
    assertEquals("TEST5: Returned value from main constructor is 4.0)", 4.0f,
        ent.getValue(), 0);
  }

  /**
   * Tests if constructor and get symbol method returns the correct symbol.
   * 
   * @throws BadType
   *           Exception if requested get method return type does not match type
   *           of object.
   */
  // Test 6 - Set up the getSymbol method to return the constructor parameter
  @Test
  public void setUpConstructorAndGetSymbol() throws BadType {
    ent = new Entry(Symbol.PLUS);
    assertEquals(
        "TEST6: Returns value from main constructor is the symbol PLUS)",
        Symbol.PLUS, ent.getSymbol());
  }

  /**
   * Tests if constructor and get symbol method returns the correct string.
   * 
   * @throws BadType
   *           Exception if requested get method return type does not match type
   *           of object.
   */
  // Test 7 - Set up the getString method to return the constructor parameter
  @Test
  public void setUpConstructorAndGetString() throws BadType {
    ent = new Entry("World");
    assertEquals("TEST7: Returns value from main constructor is string World",
        "World", ent.getString());
  }

  /**
   * Tests if return type method returns float, when object constructed with a
   * float.
   */
  // Test 8 - Set up float constructor to assign the created type variable to
  // NUMBER. Modified getType method so that it returned the variable type.
  // Changed symbol constructor to set type variable as type SMYBOL.
  @Test
  public void floatConstructorReturnsType() {
    ent = new Entry(1.0f);
    assertEquals("TEST8: Returns type NUMBER, as the type for float",
        Type.NUMBER, ent.getType());
  }

  /**
   * Tests if return type method returns string, when object constructed with a
   * string.
   */
  // Test 9 - Created STRING as enumeration, set up string constructor to assign
  // type
  // as STRING
  @Test
  public void stringConstructorReturnsType() {
    ent = new Entry("Computer");
    assertEquals("TEST9: Returns type STRING, as the type for string",
        Type.STRING, ent.getType());
  }

  /**
   * Tests if equals method returns true for two different objects, of same
   * type.
   * 
   * @throws BadType
   *           Exception if requested get method return type does not match type
   *           of object.
   */
  // Test 10 - Create an equals method that will return false. Created DIVIDE
  // enumeration in Symbol class.
  @Test
  public void equalsMethodReturnsTrue() throws BadType {
    ent = new Entry(Symbol.DIVIDE);
    ent2 = new Entry(Symbol.DIVIDE);
    assertEquals("TEST10: Equals method returns true", true, ent.equals(ent2));
  }

  /**
   * Tests if equals method returns false for two objects that are of different
   * type.
   * 
   * @throws BadType
   *           Exception if requested get method return type does not match type
   *           of object.
   */
  // Test 11 - Modify equals method to compare the type of two entries (enums),
  // with an if statement, and return correct result. Added TIMES enum to Symbol
  // class.
  @Test
  public void equalsMethodComparesTypeAndReturnResult() throws BadType {
    ent = new Entry(Symbol.DIVIDE);
    ent2 = new Entry("Hello");
    assertEquals(
        "TEST 11: Results false when comparing ent and ent2 in equals method",
        false, ent.equals(ent2));
  }

  /**
   * Tests if equals method returns false for two objects of same type, but
   * different contents.
   * 
   * @throws BadType
   *           Exception if requested get method return type does not match type
   *           of object.
   */
  // Test 12 - Added LEFTBRACKET and RIGHTBRACKET enumeration to Symbol class.
  // Modify equals method to compare if a symbol is the same.
  @Test
  public void equalsMethodComparesDifferentTypeAndSymbolAndReturnsResult()
      throws BadType {
    ent = new Entry(Symbol.LEFTBRACKET);
    ent2 = new Entry(Symbol.RIGHTBRACKET);
    assertEquals(
        "TEST 12: Result compares two different symbol entries, returns false",
        false, ent.equals(ent2));
  }

  /**
   * Tests if equals method returns true for two objects of Symbol type and same
   * contents.
   * 
   * @throws BadType
   *           Exception if requested get method return type does not match type
   *           of object.
   */
  // Test 13 - Tests if two of same symbol will return true
  @Test
  public void equalsMethodReturnsTrueForSameSymbol() throws BadType {
    ent = new Entry(Symbol.LEFTBRACKET);
    ent2 = new Entry(Symbol.LEFTBRACKET);
    assertEquals(
        "TEST 13: Result returned true for comparison of symbols in equals method",
        true, ent.equals(ent2));
  }

  /**
   * Tests if equals method returns true for two objects of float type and same
   * contents
   * 
   * @throws BadType
   *           Exception if requested get method return type does not match type
   *           of object.
   */
  // Test 14 - Added if statement with conditions if entry of type NUMBER
  // and are same floats, return true. Tests if equals method returns true for
  // same float
  @Test
  public void equalsMethodReturnsTrueForSameFloat() throws BadType {
    ent = new Entry(6.0f);
    ent2 = new Entry(6.0f);
    assertEquals("TEST 14: Result returns true for same comparison for float",
        true, ent.equals(ent2));
  }

  /**
   * Tests if equals method returns false for two objects of float type and
   * different contents.
   * 
   * @throws BadType
   *           Exception if requested get method return type does not match type
   *           of object.
   */
  // Test 15 - Test if two different floats will return false for equals method
  @Test
  public void equalsMethodReturnsFalseForDifferentFloats() throws BadType {
    ent = new Entry(6.0f);
    ent2 = new Entry(7.0f);
    assertEquals(
        "TEST 15: Result returns false for comparison of two different", false,
        ent.equals(ent2));
  }

  /**
   * Tests if equals method returns true for two objects of String type and same
   * contents.
   * 
   * @throws BadType
   *           Exception if requested get method return type does not match type
   *           of object.
   */
  // Test 16 - Added if statement to equals method to compare two strings
  // and return true if they have the same contents. Tests if two strings
  // same contents and returns true
  @Test
  public void equalsMethodReturnsTrueForSameString() throws BadType {
    ent = new Entry("TestingStringEquals");
    ent2 = new Entry("TestingStringEquals");
    assertEquals(
        "TEST 16: Returns true for two strings that have same contents", true,
        ent.equals(ent2));
  }

  /**
   * Tests if equals method returns false for two objects of String type and
   * different contents.
   * 
   * @throws BadType
   *           Exception if requested get method return type does not match type
   *           of object.
   */
  // Test 17 - Tests if comparison of two different string with equals method
  // returns false
  @Test
  public void equalsMethodReturnsTrueForDifferentString() throws BadType {
    ent = new Entry("TestingStringNotEqual");
    ent2 = new Entry("TestingString");
    assertEquals("TEST 17: Compares two different string using equals method "
        + "and returns false", false, ent.equals(ent2));
  }

  /**
   * Tests if a bad type exception is thrown if get symbol method is called for
   * an entry constructed with a float.
   * 
   * @throws BadType
   *           Exception if requested get method return type does not match type
   *           of object.
   */
  // Test 18 - Created BadType class and extended exception. Created default
  // constructor and constructor which took a string parameter. Added throw
  // BadType to every test which used getSymbol method. Added throw BadType to
  // main getSymbol method. In getSymbol method, constructed if statement: throw
  // exception BadType if type of entry does not match enumeration type SYMBOL.
  @Test(expected = BadType.class)
  public void floatEntryAndGetSymbolBadType() throws BadType {
    ent = new Entry(2.0f);
    ent.getSymbol();
  }

  /**
   * Tests if a bad type exception is thrown if a get string method is called
   * for an entry constructed with a float.
   * 
   * @throws BadType
   *           Exception if requested get method return type does not match type
   *           of object.
   */
  // Test 19 - Added throw BadType to each test that used getString method. To
  // main getString method added throw BadType. Added if statement to throw
  // exception if entry type does not match enumeration type STRING.
  @Test(expected = BadType.class)
  public void floatEntryAndGetStringBadType() throws BadType {
    ent = new Entry(2.0f);
    ent.getString();
  }

  /**
   * Tests if a bad type exception is thrown if a get value method is called for
   * an entry constructed with a string.
   * 
   * @throws BadType
   *           Exception if requested get method return type does not match type
   *           of object.
   */
  // Test 20 - Added throw BadType to each test that used getValue method. Added
  // throw BadType to main getValue method. Added if statement to getValue
  // method which will throw exception if the entry type does not match
  // enumeration type NUMBER.
  @Test(expected = BadType.class)
  public void stringEntryAndGetValueBadType() throws BadType {
    ent = new Entry("Hello");
    ent.getValue();
  }
}
