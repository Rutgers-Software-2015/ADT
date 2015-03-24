package ADT;


import java.util.Vector;

public class IngredientHandler 
{
	public static Ingredient IngredientList[]={
	new Ingredient("Tomatoes",500),
	new Ingredient("Chickens",500),
	new Ingredient("Patatoes",500),
	new Ingredient("Melons",500),
	new Ingredient("Cheese",500)};
	

	public void AddIngredient(String name,int count)
	{	
		Ingredient temp[] = new Ingredient[IngredientList.length +1];
	
		for(int j = 0; j < IngredientList.length ; j++)
		{
			temp[j] = IngredientList[j];
		}
		
		temp[temp.length - 1] = new Ingredient(name,count);

	}
	
	public void UpdateInventory(Ingredient I,int added)
	{
		int update=-1;
		for(int i=0;i<IngredientList.length;i++)
		{
			if(I.equals(IngredientList[i]))
			{
				update=i;
				break;
			}
		}
		
		IngredientList[update].count=IngredientList[update].count +added;
		
	}
}
