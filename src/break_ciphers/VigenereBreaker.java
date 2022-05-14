package break_ciphers;

import java.util.HashSet;

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
		System.out.println("Select File containing encrypted message");
		FileResource resource = new FileResource();
		String encrypted = resource.asString();
		System.out.println("Select file containig dictionary words");
		FileResource fr = new FileResource();
		HashSet<String> dict =readDictionary(fr);
		System.out.println("Breaking cipher please wait...\n.\n.\n.");
		String decrypted= breakForLanguage(encrypted, dict);
		System.out.println("Congratulations you have successfully decrypted message!\n\n\n Message:\n");
		System.out.println(decrypted.substring(0,50));
	}
	
	public HashSet<String> readDictionary(FileResource fr)
	{
		HashSet<String> dict = new HashSet<String>();
		System.out.println("Reading words from dictionary...");
		for(String word: fr.lines())
		{
			dict.add(word.toLowerCase());
		}
		return dict;
	}
	
	public int countWords(String message, HashSet<String> dict)
	{
		String[] decryptedWords = message.split("\\W+");
		int wordCount=0;
		for(String decryptedWord: decryptedWords)
		{
			if(dict.contains(decryptedWord.toLowerCase()))
			{
				wordCount++;
			}
		}
		
		return wordCount;
	}
	
	public String breakForLanguage(String encrypted, HashSet<String> dict)
	{
		int wordCount=0;
		String message = " ";
		int foundKey=0;
		for(int klength=1;klength<=100;klength++)
		{
			int[] key= tryKeyLength(encrypted, klength, 'e');
			VigenereCipher vc = new VigenereCipher(key);
			String decrypted= vc.decrypt(encrypted);
			int count = countWords(decrypted, dict);
			if(count>wordCount)
			{
				foundKey=klength;
				wordCount=count;
				message = decrypted;
			}
		}
		System.out.println("Found key length: " +foundKey);
		System.out.println(wordCount+" words matched with dictionary words");
		return message;
	}
}
