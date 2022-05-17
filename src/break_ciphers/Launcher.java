package break_ciphers;

import java.util.HashSet;

import edu.duke.FileResource;

public class Launcher {

	public static void main(String[] args) {
		VigenereBreaker vb = new VigenereBreaker();
		vb.breakVigenere();
//		FileResource resource = new FileResource();
//		HashSet<String> dict = vb.readDictionary(resource);
//		System.out.println(vb.mostCommonCharIn(dict));

	}

}
