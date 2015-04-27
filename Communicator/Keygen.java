package Shared.Communicator;
import java.io.File;
import java.io.IOException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.apache.commons.codec.DecoderException;

import static org.apache.commons.codec.binary.Hex.decodeHex;
import static org.apache.commons.codec.binary.Hex.encodeHex;
import static org.apache.commons.io.FileUtils.readFileToByteArray;
import static org.apache.commons.io.FileUtils.writeStringToFile;

public class Keygen {

	private static Key key = null;	
	public static File file = new File("AES.key");
	
	public static void main(String[] args) {

		File file = Keygen.file;
		
		Keygen k = new Keygen();
		if(k.verify()){
			
			k.generateKey();

			try {
				k.saveKey(file);
				k.loadKey(file);
				k.dialog3();
			
			} catch (IOException e) {
				k.dialogfail();
				e.printStackTrace();
			
			}
		}
		
		System.exit(0);
		
	}
	
	private void generateKey()
	{
		System.out.println("\n******************KEY GENERATE******************");
		System.out.println("Generating AES Key...\n");
		
		try{
			KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
		    keyGenerator.init(128); // 128 default; 192 and 256 also possible
		    key = keyGenerator.generateKey();
		    System.out.println("Key generated: "+key.getEncoded());
		    System.out.println("Key length: "+key.getEncoded().length);
			
		}
		catch(NoSuchAlgorithmException e){
			System.out.println("Could not generate key!");
			System.out.println(e.toString());
			key = null;
		}
	}
	
	private static void saveKey(File file) throws IOException
	{
		System.out.println("\nSaving key...");
		System.out.println("Output key: "+key);
		System.out.println("Output bytes: "+key.getEncoded());
	    char[] hex = encodeHex(key.getEncoded());
	    System.out.println("Output hex: "+String.valueOf(hex));
	    writeStringToFile(file, String.valueOf(hex));
	    System.out.println("Key saved!");
	}

	private static void loadKey(File file) throws IOException
	{
		System.out.println("\nTesting key load...");
	    String data = new String(readFileToByteArray(file));
	    System.out.println("Input hex: "+data);
	    byte[] encoded;
	    try {
	        encoded = decodeHex(data.toCharArray());
	    } catch (DecoderException e) {
	        e.printStackTrace();
	        return;
	    }
	    key = new SecretKeySpec(encoded, "AES");
	    System.out.println("Input bytes: "+key.getEncoded());
	    System.out.println("Input key: "+key);
	    System.out.println("Key loaded!");
	}
	
	private boolean verify()
	{
		if(file.exists())
		{
			boolean one = dialog1();
			if(!one){
				return false;
			}
			boolean two = dialog2();
			if(one == true && two == true){
				return true;
			}
			return false;
		}
		else{
			boolean one = dialog1();
			if(one == true){
				return true;
			}
			return false;
		}
	}
	
	private boolean dialog1()
	{
		JOptionPane pane = new JOptionPane("Generate a new encryption key?");
		    Object[] options = new String[] { "Yes", "No" };
		    pane.setOptions(options);
		    pane.setMessageType(JOptionPane.WARNING_MESSAGE);
		    JDialog dialog = pane.createDialog(new JFrame(), "Verification");
		    dialog.show();
		    Object obj = pane.getValue(); 
		    int result = -1;
		    for (int k = 0; k < options.length; k++)
		      if (options[k].equals(obj))
		        result = k;
		    System.out.println("User's choice: " + result);
		    if(result == 0){
		    	return true;
		    }
		    return false;
	}
	
	private boolean dialog2()
	{
		JOptionPane pane = new JOptionPane(
		        "An AES key already exists, are you sure you want to overwrite it?\n"
		        + "Note: All data using the current key will become irretrievable.");
		    Object[] options = new String[] { "Yes", "No" };
		    pane.setOptions(options);
		    pane.setMessageType(JOptionPane.WARNING_MESSAGE);
		    JDialog dialog = pane.createDialog(new JFrame(), "Verification");
		    dialog.show();
		    Object obj = pane.getValue(); 
		    int result = -1;
		    for (int k = 0; k < options.length; k++)
		      if (options[k].equals(obj))
		        result = k;
		    System.out.println("User's choice: " + result);
		    if(result == 0){
		    	return true;
		    }
		    return false;
	}
	
	private void dialog3()
	{
		JOptionPane.showMessageDialog(new JFrame(), "Key Generation Successful!");
	}
	
	private void dialogfail()
	{
		JOptionPane.showMessageDialog(new JFrame(), "Key Generation Failed!", "Error", JOptionPane.ERROR_MESSAGE);
	}
}
