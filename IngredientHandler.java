package ADT;


import java.util.Vector;

public class IngredientHandler 
{
	public static Ingredient IngredientList[]={
	new Ingredient("Chickens",500),
	new Ingredient("Tomatoes",500),
	new Ingredient("Patatoes",500),
	new Ingredient("Melons",500),
	new Ingredient("Cheese",500)};
	

	public static void AddIngredient(String name,int count)
	{	
		Ingredient temp[] = new Ingredient[IngredientList.length +1];
	
		for(int j = 0; j < IngredientList.length ; j++)
		{
			temp[j] = IngredientList[j];
		}
		
		temp[temp.length - 1] = new Ingredient(name,count);
		IngredientList=temp;
	}
	public static void Remove(String target)
	{	
		boolean found = false;
		for(int x = 0; x < IngredientList.length; x++) {
			if(IngredientList[x].name.equalsIgnoreCase(target)) {
				found = true;
				IngredientList[x] = null;
			}
		}
		if(!found) {
			return;
		}
		Ingredient[] temp = new Ingredient[IngredientList.length - 1];
		int indexa = 0;
		int indexb = 0;
		while(indexb < temp.length) {
			if(IngredientList[indexa] != null) {
				temp[indexb] = IngredientList[indexa];
				indexb++;
			}
			indexa++;
		}
		IngredientList = temp;
	}
	
	public static void UpdateInventory(Ingredient I,int added)
	{
		int update=-1;
		for(int i=0;i<IngredientList.length;i++)
		{
			if(I==(IngredientList[i]))
			{
				update=i;
				break;
			}
		}
		
		IngredientList[update].count=IngredientList[update].count +added;
		
	}
	// Returns index of an Ingredient;
	public static Ingredient FindInventory(String search)
	{
		/*Ingredient exist=null;
		int length=IngredientList.length;
		int index=0;
		while(index!=length)
		{	
			String tempname=IngredientList[index].name;
		
			if(tempname.equalsIgnoreCase(search));
			{
				exist= IngredientList[index];
				System.out.println(tempname);
			}
			index++;
		}
		
		return exist;*/
		boolean found = false;
		Ingredient target = null;
		String name;
		for(int i = 0; i < IngredientList.length; i++) {
			name = IngredientList[i].name;
			if(name.equalsIgnoreCase(search)) {
				found = true;
				target = IngredientList[i];
			}
		}
		if(found) {
			return target;
		} else {
			return null;
		}
		
	}
}
