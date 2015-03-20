package Utils;

public class MenuItem {
	public int MENU_ID;
	public Ingredient[] ings;
	public MenuItem(int MENU_ID) {
		this.MENU_ID = MENU_ID;
		ings = checkIngredients(MENU_ID);
	}
	private Ingredient[] checkIngredients(int MENU_ID) {
		/*
		 *  mySQL statement to check database for ingredient on menuItem
		 */
		return null;
	}
	
}
