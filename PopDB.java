package Shared;
import static org.apache.commons.codec.binary.Hex.decodeHex;
import static org.apache.commons.io.FileUtils.readFileToByteArray;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Vector;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import java.util.Random;

import org.apache.commons.codec.DecoderException;

import Shared.Communicator.*;
import Shared.Lib.*;


public class PopDB extends DatabaseCommunicator {

	
	String[] ItemName = {"Bread", "Lettuce", "Chicken", "Mayo", "Beef", "Cheese", "Tomato Sauce", "Pasta", "Chicken Wings", "Buffalo Sauce", "Mozzarella Sticks", "Syrup", "Seltzer", "Juice", "Ice Cream", "Toppings", "Chocolate Cake Packets"};

	int[] Amount = {100, 250, 200, 100, 300, 500, 250, 500, 200, 500, 1000, 200, 400, 600, 300, 200, 200};
	
	String[] weekday = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
	
	String[] Name={"Chicken Sandwhich","Hamburger","CheeseBurger","Pasta","Buffalo Wings","Mozzarella Sticks","Soda","Juice","Sundae","Cake"};

	double[] price={9.50,8.00,9.25,10.00,6.95,5.00,1.50,1.75,3.95,4.25}; 

	double[] cost= {2.25,2.00,2.25,3.00,1.75,.30,.50,1.00, 0.75, 0.75};

	int[] valid={1,1,1,1,1,1,1,1,1,1};

	String[] Ingredients={"Bread,Lettuce,Chicken,Mayo","Bread,Lettuce,Beef,Mayo","Bread,Lettuce,Beef,Mayo,Cheese","Tomato Sauce,Cheese,Pasta","Chicken Wings,Buffalo Sauce",
			"Tomato Sauce,Mozzerella Sticks","Syrup,Seltzer","Juice","Ice Cream,Toppings","Chocolate Cake Packets"};

	String[] Description={" A chicken patty on bread with lettuce and mayo.","A hamburger with mayo and lettuce.","A beef patty on bread with mayo,cheese, and lettuce","Penne pasta with tomato sauce and cheese.","Chicken wings with buffalo sauce.","Breaded mozzerella sticks served with tomato sauce.","Carbonated Beverage","Fruit flavored drink ","Vanilla Ice Cream with hot fudge, whipped cream and a cherry.","Cake" };

	String[] MenuSection={"Entree","Entree","Entree","Entree","Appetizer","Appetizer","Drinks","Drinks","Dessert","Dessert"};

	
	public void popMENU()
	{
		this.connect("admin", "gradMay17");
		this.tell("use MAINDB;");
		
		int menuid = 1;
		
		//int, str, dub, dub, bool, str, str, str
		for(int i = 0; i < Name.length; i++)
		{
			String Params = ""+ menuid + "," + "'" +  Name[i] +"'" + "," + price[i] + "," + cost[i] + "," + 1 + "," + 
					"'"+ Ingredients[i] + "'" + "," + "'"+ Description[i] +"'" + ", " + "'" + MenuSection[i] + "'";
			String sqlcomm = "INSERT INTO MENU (MENU_ID, ITEM_NAME, PRICE, COST, VALID, INGREDIENTS, DESCRIPTION, MENU_SECTION) VALUES (" + Params + ");";
			this.update(sqlcomm);
			menuid++;
			System.out.println(Name[i]);
		}
		this.disconnect();
	}
	//int, int, int, int, str, doub, int, str, str, int
	// Order_id, tableid, employeeid, seatnumber, itemname, price, quantity, spec, instr, currentstatus, menu_item_id
	public void popOrderHistory()
	{
		this.connect("admin", "gradMay17");
		this.tell("use MAINDB;");
		
		int maxnum = Name.length - 1;
		double rand = Math.random();
		double rand2 = Math.random();
		double rand3 = Math.random();
		double rand4 = Math.random();
		
		for(int j = 1; j < 1000; j++)
		{
			int lim = (int)Math.floor(rand3 * 10);
			for(int i = 0; i < lim; i++)
			{
				rand4 = Math.random();
				rand = Math.random();
				rand2 = Math.random();
				int weekd = (int)Math.floor(rand4 * 10);
				int itemindex = (int)Math.floor(rand * 10);
				int amount = (int)Math.floor(rand2 * 10);
				if(itemindex <= maxnum && (amount != 0) && (weekd <= 6))
				{
					String item = Name[itemindex];
					String params = "'" + item +"'" + ", " + amount + ", " + "'"+ weekday[weekd] + "'";
					String sqlcomm = "INSERT INTO OrderHistory (Item_Name, Amount, Date) Values (" + params + ");";
					this.update(sqlcomm);
				}
			}
			rand3 = Math.random();
		}
		
	}
	
	
	public void popInventory()
	{
		this.connect("admin", "gradMay17");
		this.tell("use MAINDB;");
		
		
		for(int i = 0; i < ItemName.length; i++)
		{
			String Params = "'"+ItemName[i]+"'" + "," + Amount[i];
			String sqlcomm = "INSERT INTO INVENTORY (Item_Name, Amount) VALUES (" 
						+ Params + ");";
			this.update(sqlcomm);
			
		}
	}
	
	public PopDB()
	{

	}
	
	
}
