package Shared.UnitTesting;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Writer {
	public static void write(String[] newText, String name) throws IOException {
		File newDoc = new File(name + ".txt");
		if(!newDoc.exists()) {
			newDoc.createNewFile();
		}
		FileWriter fwriter = new FileWriter(newDoc.getAbsoluteFile());
		BufferedWriter bwriter = new BufferedWriter(fwriter);
		for(String a : newText) {
			if(a == null) {
				bwriter.write("\n");
			} else {
				bwriter.write(a + "\n");
			}
		}
		bwriter.close();
	}
}
