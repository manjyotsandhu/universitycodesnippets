package zbvc.cs2800;

public interface Calculator {

  public float evaluate(String string) throws InvalidExpressionException, BadType, StackEmpty;

}
