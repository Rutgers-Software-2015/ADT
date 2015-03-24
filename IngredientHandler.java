package ADT;

public class IngredientHandler 
{
	public Ingredient IngredientList[];
	
	public Ingredient[] AllIngredient=
		{
			new Ingredient("Tomatoes",100),
			new Ingredient("Lettuce",100),
			new Ingredient("Celery",100),
			new Ingredient("Chicken",100),
		};
	public void QueryIngredient()
	{
		IngredientList= AllIngredient;
	}
	
}
