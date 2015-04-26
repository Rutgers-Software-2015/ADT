package Shared.UnitTesting;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.LinkedList;

import javax.swing.JFrame;

import Shared.Notifications.NotificationEntry;
import Shared.Notifications.NotificationHandler;

public class NotificationHandler_GetNotifications_Test extends JFrame{

	public static void main(String[] args) {
		
		new NotificationHandler_GetNotifications_Test();
	}
	
	public NotificationHandler_GetNotifications_Test()
	{
		super();
		boolean pass = true;
		PrintStream filewrite = null;
		try {
			filewrite = new PrintStream(System.getProperty("user.dir")+"/src/Shared/UnitTesting/NotificationHandler_GetNotifications_Test_Result.txt");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		filewrite.println("Starting new instance of NotificationHandler...");
		NotificationHandler comm = new NotificationHandler(100,"Waiter");
		filewrite.println("Connecting to DB...");
		comm.connect("admin","gradMay17");
		if(comm.getConnectionStatus()==0){
			filewrite.println("Connection successful!");
		}
		else{
			filewrite.println("Connection failed!");
			filewrite.println("");
			filewrite.println("TEST = FAIL");
			filewrite.close();
			try {
				Desktop.getDesktop().open(new File(System.getProperty("user.dir")+"/src/Shared/UnitTesting/NotificationHandler_GetNotifications_Test_Result.txt"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.exit(0);
		}
		filewrite.println("Starting first retrieval of notifications...");
		LinkedList<NotificationEntry> notifications = comm.getNotifications();
		filewrite.println("Notifications received!");
		filewrite.println("Creating test notification...");
		comm.sendMessage("All", "This is a unit test!", 0);
		filewrite.println("Test notification created and pushed!");
		filewrite.println("Check if notification was within first retrieval...");
		for(int i = 0;i < notifications.size();i++){
			if(notifications.get(i).actorClass == "All" && notifications.get(i).message == "This is a unit test!"){
				filewrite.println("Notification found in first retrieval... test failed");
				pass = false;
			}
		}
		if(pass == true){
			filewrite.println("No notification found!");
		}
		filewrite.println("Starting second retrieval of notifications...");
		notifications = comm.getNotifications();
		filewrite.println("Notifications received!");
		filewrite.println("Check for new notification...");
		pass = false;
		for(int i = 0;i < notifications.size();i++){
			if(notifications.get(i).actorClass.equals("All") && notifications.get(i).message.equals("This is a unit test!")){
				filewrite.println("Notification found in second retrieval!");
				pass = true;
			}
		}
		if(pass == false){
			filewrite.println("No notification found!");
		}
		filewrite.println("Deleting test notification...");
		comm.update("DELETE FROM MAINDB.Notifications WHERE message=\"This is a unit test!\"");
		filewrite.println("Test notification deleted!");
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
			Desktop.getDesktop().open(new File(System.getProperty("user.dir")+"/src/Shared/UnitTesting/NotificationHandler_GetNotifications_Test_Result.txt"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.exit(0);
	}

}
