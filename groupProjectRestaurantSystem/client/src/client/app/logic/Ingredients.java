package client.app.logic;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Martin Fahy - zbvc377.
 *
 */

@XmlRootElement(name = "ingredients")
public class Ingredients {
	@XmlElement(name = "ingredient", type = Ingredient.class)
	private List<Ingredient> setofingredients = new ArrayList<>();
	  
	public Ingredients() {};
	  
	public Ingredients(List<Ingredient> itemIngredients) {
	  this.setofingredients = itemIngredients;
	}
	  
	public List<Ingredient> getIngredients() {
	  return setofingredients;
	}
	  
	public void setIngredients(List<Ingredient> ingredients) {
	  this.setofingredients = ingredients;
	}
}
