package ADT;


import java.util.Vector;

public class IngredientHandler 
{
	public Vector<Ingredient> IngredientList;
	

	public void IngredientHandler()
	{
		IngredientList=new Vector<Ingredient>();
	}
	public void AddIngredient(String name,int count)
	{
		IngredientList.add(new Ingredient(name,count));

	}
	
}
