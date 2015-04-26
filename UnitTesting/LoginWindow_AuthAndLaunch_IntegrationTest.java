package Shared.UnitTesting;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.math.BigInteger;
import java.security.SecureRandom;

import Login.LoginWindow;

public class LoginWindow_AuthAndLaunch_IntegrationTest{

	/**
	 * Login Integration Test
	 * 
	 * 1) Will initiate launch cycle for each interface
	 * 2) Will authenticate the login session
	 * 3) If authenticated, it will load the interface
	 * 4) Repeat for 6 interfaces
	 * 
	 * @author Samuel Baysting
	 * 
	 */
	
	LoginWindow login = null;
	Boolean closedDialog = false;
	
	public static void main(String[] args) {
		
		new LoginWindow_AuthAndLaunch_IntegrationTest();
	}
	
	@SuppressWarnings("deprecation")
	public LoginWindow_AuthAndLaunch_IntegrationTest()
	{
		SecureRandom random = new SecureRandom();
		PrintStream filewrite = null;
		try {
			filewrite = new PrintStream(System.getProperty("user.dir")+"/src/Shared/UnitTesting/LoginWindow_AuthAndLaunch_IntegrationTest_Result.txt");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String test1 = new BigInteger(64, random).toString(32);
		String test2 = new BigInteger(64, random).toString(32);
		String test3 = new BigInteger(64, random).toString(32);
		Object logins[] = {test1,"waiter","host",test2,"busboy","kitchen","customer","manager","waiter"};
		Object passes[] = {test1,"waiter","host",test2,"busboy","kitchen","customer","manager",test3};
		boolean[] expectedResults = {false,true,true,false,true,true,true,true,false};
		boolean[] actualResults = new boolean[9];
		//By the end there should be 6 windows open
		for(int i = 0;i < logins.length; i++){
			login = new LoginWindow();
			filewrite.println("Creating new login window...");
			Thread thread = new Thread(new Runnable()
			{
				@SuppressWarnings("static-access")
				public void run(){
					while(true){
					System.out.println("DIALOG WATCHER");
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
						if(login.error != null){
							System.out.println("CLOSE DIALOG");
							login.error.getRootFrame().dispose();
							closedDialog = true;
						}
					}
				}
			});
			filewrite.println("Starting dialog watcher thread...");
			thread.start();
			try{
				filewrite.println("Attempting login with:");
				filewrite.println("USERNAME: "+(String)logins[i]);
				filewrite.println("PASSWORD: "+(String)passes[i]);
				login.userLoginBox.setText((String) logins[i]);
				login.userPassBox.setText((String) passes[i]);
				login.loginButton.doClick();
				if(closedDialog == false){
					filewrite.println("Successful login!");
					actualResults[i] = true;
				}
				else{
					filewrite.println("Failed login - Dialog captured - Invalid username/password");
					actualResults[i] = false;
				}
			}catch(Exception e){
				filewrite.println("Failed login - Exception thrown");
				actualResults[i] = false;
			}

			filewrite.println("Closing open windows...");
			java.awt.Window win[] = java.awt.Window.getWindows(); 
			for(int j=0;j<win.length;j++){ 
			win[j].dispose(); 
			} 
			filewrite.println("Stopping dialog watcher thread...");
			closedDialog = false;
			thread.stop();
			thread = null;
			filewrite.println("SUBTEST "+i+" RESULT: "+actualResults[i]);
			filewrite.println("");
			
		}
		
		filewrite.println("");
		filewrite.println("EXPECTED RESULTS:");
		for(int i = 0; i < expectedResults.length; i++){
			filewrite.println(expectedResults[i]);
		}
		
		filewrite.println("");
		filewrite.println("ACTUAL RESULTS:");
		for(int i = 0; i < actualResults.length; i++){
			filewrite.println(actualResults[i]);
		}
		
		boolean pass = true;
		for(int i = 0;i < actualResults.length;i++){
			if(actualResults[i]!=expectedResults[i]){
				pass = false;
			}
		}
		filewrite.println("IS EQUAL? "+pass);
		filewrite.println("");
		if(pass){
			filewrite.println("TEST = PASS");
		}
		else{
			filewrite.println("TEST = FAIL");
		}
		
		filewrite.close();
		
		try {
			Desktop.getDesktop().open(new File(System.getProperty("user.dir")+"/src/Shared/UnitTesting/LoginWindow_AuthAndLaunch_IntegrationTest_Result.txt"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.exit(0);
	}

}
