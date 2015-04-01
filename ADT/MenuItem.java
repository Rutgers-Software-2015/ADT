package Shared.ADT;
/**This is the Abstract Data Type of MenuItem. It contains the constructor and variables assoicated with it.
 * 
 * @author Everyone
 *
 */
import java.text.DecimalFormat;

public class MenuItem {
	
	public int MENU_ID; //Menu Identification Integer
	public Ingredient ings[]; //Array of Ingredients
	public String STRING_ID; //String associated with MENU_ID
	public float PRICE; //Price associated with MENU_ID
	public boolean VALID; //Shows validity of current MenuItem
	
	public MenuItem(int MENU_ID){
		
		float payment = 0;
		this.MENU_ID = MENU_ID;
		
		if(doesExist(MENU_ID)){
			ings = checkIngredients(MENU_ID);
			STRING_ID = checkItemName(MENU_ID, payment);
			PRICE = checkItemPrice(MENU_ID, payment);
			VALID = true;
		}
		else{
			VALID = false;
			}
		}
	
	public MenuItem(int MENU_ID, float payment){
		
		this.MENU_ID = MENU_ID;
		
		if(doesExist(MENU_ID)){
			ings = checkIngredients(MENU_ID);
			STRING_ID = checkItemName(MENU_ID, payment);
			PRICE = checkItemPrice(MENU_ID, payment);
			VALID = true;
		}
		else{
			VALID = false;
			}
		}
		
	
	private boolean doesExist(int MENU_ID){
		/*
		 * mySQL statement to check validity of the MENU_ID
		 */
	
		return true;
	}
	
	private Ingredient[] checkIngredients(int MENU_ID) {
		/*
		 *  mySQL statement to check database for ingredient on menuItem
		 */
		Ingredient list1[]=new Ingredient[2];
		switch(MENU_ID){
		case -1:
			return null;
		case 1:
			list1[0]=IngredientHandler.IngredientList[0];
			list1[1]=IngredientHandler.IngredientList[0];
			return list1;
		case 2:
			list1[0]=IngredientHandler.IngredientList[1];
			list1[1]=IngredientHandler.IngredientList[1];
			return list1;
		case 3:
			list1[0]=IngredientHandler.IngredientList[2];
			list1[1]=IngredientHandler.IngredientList[2];
			return list1;
		case 4:
			list1[0]=IngredientHandler.IngredientList[1];
			list1[1]=IngredientHandler.IngredientList[1];
			return list1;
		case 5:
			list1[0]=IngredientHandler.IngredientList[0];
			list1[1]=IngredientHandler.IngredientList[1];
			return list1;
		case 6:
			list1[0]=IngredientHandler.IngredientList[1];
			list1[1]=IngredientHandler.IngredientList[2];
			return list1;
		default: 
			return null;
		}
		
	}
	
	private String checkItemName(int MENU_ID, float payment){
		/*
		 * mySQL statement to check database for item name associated with MENU_ID
		 */
		
		//USING THE FOLLOWING FOR EXAMPLE ONLY
		switch(MENU_ID){
		case -1:
			return "Customer Payment";
		case 1:
			return "Chicken";
		case 2:
			return "Pasta";
		case 3:
			return "Canoli";
		case 4:
			return "Gulash";
		case 5:
			return "Sausage";
		case 6:
			return "Veggie";
		default: 
			return null;
		}
	}
	
	private float checkItemPrice(int MENU_ID, float payment){
		/*
		 * mySQL statement to check database for price associated with MENU_ID
		 */
		
		//USING THE FOLLOWING FOR EXAMPLE ONLY

		switch(MENU_ID){
		case -1:
			return 0-payment;
		case 1:
			return (float)12.99;
		case 2:
			return (float)13.99;
		case 3:
			return (float)6.99;
		case 4:
			return (float)9999.99;
		case 5:
			return (float)17.99;
		case 6:
			return (float)11.99;
		default: 
			return 0;
		}
	}
	public static int getId(String name)
	{	
		
			if(name=="Chicken")
			{
				return 1;
			}
			else if(name=="Pasta")
			{
				return 2;
			}
			else if(name=="Canoli")
			{
				return 3;
			}
			else if(name=="Gulash")
			{
				return 4;
			}
			else if(name=="Sausage")
			{
				return 5;
			}
			else if(name=="Veggie")
			{
				return 6;
			}
			return -1;
	}
	
}
