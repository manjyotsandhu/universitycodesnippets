package zbvc.cs2800;

/**
 * The entry class creates an entry object that takes one of three types of
 * parameters (string, float or symbol) and declares the type as an enumeration.
 * There are also getter methods for the relevant types.
 * 
 * @author zbvc854
 *
 */

public class Entry {

  /**
   * Value of float, if object constructed with float.
   */
  private float number;

  /**
   * Symbol enumeration, if object constructed with symbol.
   */
  private Symbol other;

  /**
   * Contents of string, if object constructed with string.
   */
  private String str;

  /**
   * Assigned type enumeration of object.
   */
  private Type type;

  /**
   * Constructor assigns float parameter as a variable and defines (@link Type)
   * as a NUMBER enumeration.
   * 
   * @param value
   *          Parameter of type float, taken by constructor.
   */
  public Entry(float value) {
    number = value;
    type = Type.NUMBER; // Assign type as NUMBER enumeration.
  }

  /**
   * Constructor assigns Symbol parameter as a variable and defines (@link Type)
   * as a SYMBOL enumeration.
   * 
   * @param which
   *          Parameter of type Symbol, taken by constructor.
   */
  public Entry(Symbol which) {
    other = which;
    type = Type.SYMBOL; // Assign type as SYMBOL enumeration.
  }

  /**
   * Constructor assigns a String parameter as a variable and defines (@link
   * Type) as a STRING enumeration.
   * 
   * @param string
   *          Parameter of type String, taken by constructor.
   */
  public Entry(String string) {
    str = string;
    type = Type.STRING; // Assign type as STRING enumeration.
  }

  /**
   * Method used to return the float value for this object.
   * 
   * @return float Returns the float value assigned to this object.
   * @throws BadType
   *           Throws exception when assigned (@link Type) of object does not
   *           match (@link Type) NUMBER.
   * @see BadType
   */
  public float getValue() throws BadType {
    // Throw exception if type of object is not of type NUMBER.
    throwException(Type.NUMBER);
    return number;
  }

  /**
   * Method used to return the symbol enumeration for this object.
   * 
   * @return Symbol Returns the symbol assigned to this object.
   * @throws BadType
   *           Throws exception when assigned (@link Type) of object does not
   *           match type SYMBOL.
   * @see BadType
   */
  public Symbol getSymbol() throws BadType {
    // Throw exception if type of object is not of type SYMBOL.
    throwException(Type.SYMBOL);
    return other;
  }

  /**
   * Method used to return the string variable for this object.
   * 
   * @return String Returns the string assigned to this object.
   * @throws BadType
   *           Throws exception when assigned (@link Type) of object does not
   *           match type STRING.
   * @see BadType
   */
  public String getString() throws BadType {
    // Throw exception if type of object is not of type STRING.
    throwException(Type.STRING);
    return str;
  }

  /**
   * Returns the (@link Type) enumeration of this object.
   * 
   * @return Type Returns the (@link Type) assigned to this object.
   */
  public Type getType() {
    return type;
  }

  /**
   * Takes parameter of type (@link Type) and throws exception if it does not
   * match type of this object.
   * 
   * @param typeOfEntry
   *          Type enumeration to be compared to object type.
   * @throws BadType
   *           If parameter does not match the type of this object.
   */
  public void throwException(Type typeOfEntry) throws BadType {
    // Throw exception if type of object does not match that of the parameter.
    if (this.getType() != typeOfEntry) {
      throw new BadType(
          "Requested type of get method does not match current object type");
    }
  }

  /**
   * If an entry and this object are of same type and contents, method will
   * return true. In any other case, method will return false.
   * 
   * @param first
   *          Entry object to be compared.
   * @return boolean Will return true if two entries have same contents and
   *         type. In any other case, false returned.
   * @throws BadType
   *           Throws exception if assigned (@link Type) of parameter does not
   *           match type of object.
   * @see BadType
   */

  public boolean equals(Entry first) throws BadType {
    // If type of object and type of parameter matches
    if (this.getType() == first.getType()) {

      // If type of object is SYMBOL and symbols match.
      if (this.getType() == Type.SYMBOL
          && this.getSymbol() == first.getSymbol()) {
        return true;
      }

      // If type of object is NUMBER and values match.
      if (this.getType() == Type.NUMBER
          && Float.compare(this.getValue(), first.getValue()) == 0) {
        return true;
      }

      // If type of object is STRING and string contents match.
      if (this.getType() == Type.STRING
          && this.getString().equals(first.getString())) {
        return true;
      }

    }
    return false;
  }

  /**
   * Method for generating a hash code for object (auto-generated method).
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + Float.floatToIntBits(number);
    result = prime * result + ((other == null) ? 0 : other.hashCode());
    result = prime * result + ((str == null) ? 0 : str.hashCode());
    result = prime * result + ((type == null) ? 0 : type.hashCode());
    return result;
  }

}