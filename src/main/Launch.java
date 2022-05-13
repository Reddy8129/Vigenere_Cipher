package main;

import edu.duke.*;

public class Launch {

	public static void main(String[] args) {
		VigenereBreaker vb = new VigenereBreaker();
//		FileResource resource = new FileResource();
//		String encrypted = resource.asString();
//		// String str=vb.sliceString("abcdefghijklm",0,5);
//		int[] key = vb.tryKeyLength(encrypted, 4, 'e');
//
//		for (int i = 0; i < key.length; i++) {
//			System.out.println(key[i]);
//		}
		vb.breakVigenere();
		

	}
	
}
