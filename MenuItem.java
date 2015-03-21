package ADT;

public class MenuItem {
	
	public int MENU_ID; //Menu Identification Integer
	public Ingredient[] ings; //Array of Ingredients
	public String STRING_ID; //String associated with MENU_ID
	public float PRICE; //Price associated with MENU_ID
	public boolean VALID; //Shows validity of current MenuItem
	
	public MenuItem(int MENU_ID){
		
		this.MENU_ID = MENU_ID;
		
		if(doesExist(MENU_ID)){
			ings = checkIngredients(MENU_ID);
			STRING_ID = checkItemName(MENU_ID);
			PRICE = checkItemPrice(MENU_ID);
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
		return null;
	}
	
	private String checkItemName(int MENU_ID){
		/*
		 * mySQL statement to check database for item name associated with MENU_ID
		 */
		
		//USING THE FOLLOWING FOR EXAMPLE ONLY
		switch(MENU_ID){
		case 1:
			return "Chicken";
		case 2:
			return "Pasta";
		case 3:
			return "Canoli";
		case 4:
			return "Rahul is a Nazi";
		case 5:
			return "Sausage ;)";
		case 6:
			return "Veggie";
		default: 
			return null;
		}
	}
	
	private float checkItemPrice(int MENU_ID){
		/*
		 * mySQL statement to check database for price associated with MENU_ID
		 */
		
		//USING THE FOLLOWING FOR EXAMPLE ONLY
		switch(MENU_ID){
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
	
}
