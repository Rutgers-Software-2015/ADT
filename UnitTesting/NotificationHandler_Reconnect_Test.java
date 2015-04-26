package Shared.UnitTesting;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;

import javax.swing.JFrame;

import Shared.Notifications.NotificationHandler;

public class NotificationHandler_Reconnect_Test extends JFrame {

	private NotificationHandler_Reconnect_Test parent = this;
	
	public static void main(String[] args) {
		
		new NotificationHandler_Reconnect_Test();
	}
	
	NotificationHandler_Reconnect_Test()
	{
		super();
		parent = this;
		boolean pass = true;
		PrintStream filewrite = null;
		try {
			filewrite = new PrintStream(System.getProperty("user.dir")+"/src/Shared/UnitTesting/NotificationHandler_Reconnect_Test_Result.txt");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		filewrite.println("Starting new instance of NotificationHandler...");
		NotificationHandler comm = new NotificationHandler(100,"Waiter");
		filewrite.println("Starting dialog watcher thread...");
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
				java.awt.Window win[] = java.awt.Window.getWindows(); 
				for(int j=0;j<win.length;j++){ 
				win[j].dispose(); 
				} 
				
					}
				}
		});
		thread.start();
		filewrite.println("Connecting to DB through handler...");
		comm.connect("admin","gradMay17");
		if(comm.getConnectionStatus()==0){
			filewrite.println("Connected!");
		}
		else{
			filewrite.println("Failed to connect!");
			pass = false;
		}
		filewrite.println("Directly disconnecting from DB...");
		comm.disconnect();
		filewrite.println("Attempt reconnect through handler reconnect function...");
		comm.reconnect();
		if(comm.getConnectionStatus()==0){
			filewrite.println("Successful reconnect!");
		}
		else{
			filewrite.println("Reconnect failed!");
			pass = false;
		}
		
		filewrite.println("Stopping dialog watcher thread...");
		thread.stop();
		thread = null;
		
		filewrite.println("");
		if(pass){
			filewrite.println("TEST = PASS");
		}
		else{
			filewrite.println("TEST = FAIL");
		}
		
		filewrite.close();
		comm.disconnect();
		
		try {
			Desktop.getDesktop().open(new File(System.getProperty("user.dir")+"/src/Shared/UnitTesting/NotificationHandler_Reconnect_Test_Result.txt"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.exit(0);
	}

}
