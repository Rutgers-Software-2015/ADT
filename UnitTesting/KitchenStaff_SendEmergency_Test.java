package Shared.UnitTesting;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import javax.swing.JFrame;

import KitchenStaff.KitchenStaffHandler;
import Shared.Communicator.DatabaseCommunicator;
import Shared.Notifications.NotificationGUI;

/*
 * This test shows that an Emergency Message goes to all Employees.
 *  @author Rahul Tandon
 *  @debugger Rahul Tandon
 *  @tester Rahul Tandon
 */
/*
 * Preconditions : 
 */
public class KitchenStaff_SendEmergency_Test  extends JFrame
{	private static NotificationGUI notification;
	private KitchenStaff_SendEmergency_Test parent = this;
	public static void main(String[] args) 
	{
		try {
			new KitchenStaff_SendEmergency_Test();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	KitchenStaff_SendEmergency_Test() throws SQLException
	{	
		super();
		parent = this;
		
		Random r= new Random();
		PrintStream filewrite=null;
		NotificationGUI notification=null;
		DatabaseCommunicator DBC=new DatabaseCommunicator();
		try 
		{
			notification=new NotificationGUI(5,"KitchenStaff");
		}
		catch( NullPointerException e)
		{
			
		}
		
	
		try 
		{
			filewrite = new PrintStream(System.getProperty("user.dir")+"/src/Shared/UnitTesting/KitchenStaff_SendEmergency_Test_Result.txt");
		} 
		catch (FileNotFoundException e2)
		{
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		
		filewrite.println("Before sending an emergency: Messages sent to all employees before the test are below:");
		DBC.connect("admin", "gradMay17");
		DBC.tell("use MAINDB;");
		ResultSet allActors=DBC.tell("select * from Notifications where actor='All';");
		
		allActors.beforeFirst();
		while(allActors.next())
		{
			filewrite.println("Actor:"+allActors.getString("actor"));
			filewrite.println("Message:"+allActors.getString("message"));
		}
		
		
		filewrite.println(" ");
		filewrite.println("Generate a random choice: Choice=1 or Choice=0");
		filewrite.println("If Choice=1 Emergency should not send.\nIf Choice=0 Emergency should send.");	
		
		
		
		
		
	// Run the test 5 times
	for(int i=0;i<5;i++)
	{
		filewrite.println("Test " +i+": ");
		int choice=r.nextInt(2); 
		filewrite.println("The choice is:"+choice);
		filewrite.println("Trying to send a message:");
		
		if(choice==1)
		{
			filewrite.println("The emergency message we are trying to send is: 'There is a fire.'");
			boolean sent=KitchenStaffHandler.SendEmergency("There is a fire.", 1, notification);
			if(sent)
			{
				filewrite.println("The emergency was sent even though choice=1");
				filewrite.println("TEST=FAIL");
			}
			else
			{
				filewrite.println("The emergency was not sent because choice=1");
				filewrite.println("TEST=PASS");
			}
		}
		else if(choice==0)
		{
			filewrite.println("The emergency message we are trying to send is: 'There is a grease fire.'");
			boolean sent=KitchenStaffHandler.SendEmergency("There is a grease fire.", 0, notification);
			if(sent)
			{
				filewrite.println("The emergency was sent because choice=0");
				filewrite.println("TEST=PASS");
			}
			else
			{
				filewrite.println("The emergency was not sent even though choice=0");
				filewrite.println("TEST=FAIL");
			}
		}
		filewrite.println(" ");
	}
	
	//Now we should display the notification table from the database to show the messages were sent.
	filewrite.println("After the test the messages sent to all Employees are shown below.");
	DBC.connect("admin", "gradMay17");
	DBC.tell("use MAINDB;");
	allActors=DBC.tell("select * from Notifications where actor='All';");
	
	allActors.beforeFirst();
	while(allActors.next())
	{
		filewrite.println("Actor:"+allActors.getString("actor"));
		filewrite.println("Message:"+allActors.getString("message"));
	}
	filewrite.close();
	try {
		Desktop.getDesktop().open(new File(System.getProperty("user.dir")+"/src/Shared/UnitTesting/KitchenStaff_SendEmergency_Test_Result.txt"));
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

}
