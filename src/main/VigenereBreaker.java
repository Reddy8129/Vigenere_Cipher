package main;

import edu.duke.FileResource;

public class VigenereBreaker {

	public String sliceString(String message, int whichSlice, int totalSlice) {
		String slicedStr = new String();
		for (int i = whichSlice; i < message.length(); i += totalSlice) {
			slicedStr += message.charAt(i);
		}
		return slicedStr;
	}

	public int[] tryKeyLength(String encrypted, int klength, char mostCommon) {
		CaesarCracker crack = new CaesarCracker(mostCommon);
		int start, end, count = 0;
		String sliceStr;
		int[] key = new int[klength];
		for (int i = 0; i < klength; i++) {
			sliceStr = sliceString(encrypted, i, klength);
			key[i] = crack.getKey(sliceStr);
		}

		return key;

	}
	void breakVigenere()
	{
		FileResource resource = new FileResource();
		String encrypted = resource.asString();
		int key[] = tryKeyLength(encrypted,4,'e');
		for (int i = 0; i < key.length; i++) {
			System.out.println(key[i]);
		}
		VigenereCipher vc= new VigenereCipher(key);
		String decrypted=vc.decrypt(encrypted);
		System.out.println(decrypted);
	}

}
