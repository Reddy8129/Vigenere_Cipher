package break_ciphers;

import java.util.HashMap;
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
		HashMap<String, HashSet<String>> allDict = new HashMap<String,HashSet<String>>();
		String[] languages ={"Danish", "Dutch", "English", "French", "German", "Italian", "Portuguese","Spanish"};
		System.out.println("Select File containing encrypted message");
		FileResource resource = new FileResource();
		//Read encrypted message as String into variable encrypted.
		String encrypted = resource.asString();
		//Read all language dictionaries into HashMap allDict.
		FileResource fr;
		for(String language: languages)
		{
			System.out.println("Reading words from "+language+" dictionary...");
			fr= new FileResource("data/dictionaries/"+language);
			HashSet<String> lanDict = readDictionary(fr);
			allDict.put(language, lanDict);
		}
		System.out.println("Breaking cipher please wait...\n.\n.\n.");
		
		String decrypted= breakForAllLangs(encrypted, allDict);
		System.out.println("\n\n*****************************************************************");
		System.out.println("Congratulations you have successfully decrypted message!\n\n\n Message:\n");
		System.out.println(decrypted);
	}
	
	public HashSet<String> readDictionary(FileResource fr)
	{
		HashSet<String> dict = new HashSet<String>();
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
		char commonChar = mostCommonCharIn(dict);
		for(int klength=1;klength<=100;klength++)
		{
			int[] key= tryKeyLength(encrypted, klength, commonChar);
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
		//System.out.println("Found key length: " +foundKey);
		System.out.println(wordCount+" words matched with dictionary words");
		return message;
	}
	
	public char mostCommonCharIn(HashSet<String> dict)
	{
		String alphabets="abcdefghijklmnopqrstuvwxyz";
		char commonChar=' ';
		int commonCharCount=0;
		HashMap<Character,Integer> charCount = new HashMap<Character,Integer>();
		for (int i = 0; i < alphabets.length(); i++) {
			charCount.put(alphabets.charAt(i), 0);
		}
		for(String word: dict)
		{
			for(int i=0;i<word.length();i++)
			{
				char letter = word.charAt(i);
				if(alphabets.contains(Character.toString(letter)))
				{
					int count = charCount.get(letter);
					charCount.put(letter, count+1);
				}
			}
		}
		for(Character c : charCount.keySet())
		{
			int count=charCount.get(c);
			if(count>commonCharCount){
				commonChar=c;
				commonCharCount=count;
			}
			//System.out.println(c+ " --> "+charCount.get(c));
		}
		return commonChar;
	}
	
	public String breakForAllLangs(String encrypted, HashMap<String, HashSet<String>> allDict)
	{
		HashSet<String> dict;
		int wordCount=0;
		String message=" ";
		String lan=" ";
		for(String language:allDict.keySet())
		{
			dict=allDict.get(language);
			System.out.println("###################################################################");
			System.out.println("Breaking for language "+language);
			String decrypted = breakForLanguage(encrypted, dict);
			int count = countWords(decrypted, dict);
			if(count>wordCount)
			{
				wordCount=count;
				message=decrypted;
				lan=language;
			}
			
		}
		System.out.println("\n\n*****************************************************************");
		System.out.println("Maximum words matched with "+lan+" language");
		return message;
	}
}
