package break_ciphers;

import java.util.HashSet;

import edu.duke.FileResource;

public class Launcher {

	public static void main(String[] args) {
		VigenereBreaker vb = new VigenereBreaker();
		try {
			vb.breakVigenere();

		} catch (Exception e) {
			System.out.println(e);
			System.out.println("You have not selected a file. Please select a file to continue");
		}
//		FileResource resource = new FileResource();
//		HashSet<String> dict = vb.readDictionary(resource);
//		System.out.println(vb.mostCommonCharIn(dict));

	}

}
