import java.io.FileNotFoundException;
import java.util.*;
import java.io.*;
import java.util.Map;
import java.util.Iterator;
import java.util.Set;
import java.util.Random;

public class countWords {

    public static int countUnique1A(WordStream words) {
	// Put code for question 1A here: Count distinct words using a HashSet
		HashSet hs = new HashSet();
		for (String word : words) {
			hs.add(word);
		}
		return hs.size();
    }
    
    public static int countUnique1B(WordStream words) {
	// Put code for question 1B here: Count distinct words using a TreeSet
		words.reset();
		TreeSet ts = new TreeSet();
		for (String word : words) {
			ts.add(word);
		}
		return ts.size(); // Change this
    }

    public static int countUnique1C(WordStream words) {
	// Put code for question 1C here: Count distinct words in a slow (naive)
	// way, using no data structure more advanced than a standard ArrayList.
	ArrayList<String> al = new ArrayList<String>();
	for (String word : words) {
		if (!al.contains(word)) {
			al.add(word);
		}
	}
	return al.size(); // Change this
    }

    // Example function: Summing up the length of all words in the file
    public static int countLength(WordStream words) {
	int length = 0;
	for (String word : words) {
	    length = length + word.length();
	}
	return length;
    }
    
    public static void addToFile(ArrayList<String> list, String filename) {
    	
		BufferedWriter bw = null;
    	FileWriter fw = null;
    	
    	try {
			fw = new FileWriter(filename);
			bw = new BufferedWriter(fw);
			
	    	for (String s: list) {
	    		bw.write(s);
	    		bw.newLine();
	    	}
	    	
	    	bw.close(); 
	    	fw.close();
	    	
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    	
    }
    
    public static void printResults(ArrayList<String> list) {
    	for (String s: list) {
    		System.out.println(s);
    	}
    }
    
    //returns number of words that only occur once
    public static int countWordFreq(WordStream words) {
    	HashMap<String, Integer> hm = new HashMap<String, Integer>();
    	int wordCount = 0;
    	
    	for (String word: words) {
    		
    		//if word doesnt exist in hm yet
    		if (!(hm.containsKey(word))) {
    			hm.put(word, 1);
    		} else {
    			int currentFreq = hm.get(word); 
    			hm.put(word, currentFreq + 1);
    		}
    	}
    	
    	Set<String> keys = hm.keySet();
    	for (String key: keys) {
    		if (hm.get(key) == 1) {
    			wordCount++;
    		}
    	}
    	
    	return wordCount;
    }

    // Add functions for tasks 2 and 3 here
    public static String question2B(WordStream words) throws FileNotFoundException {
    	
    	//stores the word, with the value of a hash set that contains all possible next words
    	HashMap<String, HashSet> hm = new HashMap<String, HashSet>();
    	String result = "";
    	WordStream temp = new WordStream("mobydick.txt");
    	
    	String currWord = null; //current word
    	String nextWord = temp.nextWord(); //word that comes after the current word
    	
    	while (words.hasNextWord()) {
    		currWord = words.nextWord();
    		nextWord = temp.nextWord();
    		
    		//if the word has not been recorded yet
    		//make new hash set and add to hash map
    		if(!hm.containsKey(currWord)) {
    			HashSet hs = new HashSet();
    			hs.add(nextWord);
    			hm.put(currWord, hs);
    		} 
    		
    		//if word recorded, add the next word to the hash set
    		else {
    			hm.get(currWord).add(nextWord);
    		}
    	}
	    
    	Set<String> set = hm.keySet(); //set of all the words
    	String largestKey = "";
    	int largestKeyValue = 0;
    	
    	for (String key: set) {
    		String currKey = key;
    		int currValue = hm.get(key).size();
    		
    		//if the current word has a larger number of unique next words, replace current highest
    		if (currValue > largestKeyValue) {
    			largestKeyValue = currValue;
    			largestKey = currKey;
    		}
    	}
    
    	//transform results into returnable string
    	result = "Key " + largestKey + " has " + largestKeyValue + " unique next words.";
    	
    	words.reset();
    	
    	return result;
    }
    
    //Creates hash map of a string -> integer, to represent how many times that string follows the parameter word
    public static HashMap<String, Integer> question2C(WordStream words, String search) throws FileNotFoundException {
    	HashMap<String, Integer> hm = new HashMap<String, Integer>();
    	WordStream temp = new WordStream("mobydick.txt");
    	
    	String currWord; //current word
    	String nextWord = temp.nextWord(); //word that comes after the current word
    	
    	while (temp.hasNextWord()) {
    		currWord = words.nextWord();
    		nextWord = temp.nextWord();
    		
    		//if the current word we are looking at is one that we are analysing with the method
    		if (currWord.equals(search)) {
    			//if word has not been recorded
    			if (!hm.containsKey(nextWord)) {
    				hm.put(nextWord, 1);
    			} else { //if word has been recorded
    				hm.put(nextWord, hm.get(nextWord) + 1);
    			}
    		}
    	}


    	words.reset();
    	return hm;
    }

	public static String randomNextWord(WordStream words, String word1) throws FileNotFoundException {
		HashMap<String, Integer> hm = question2C(words, word1);
		ArrayList<String> randomWords = new ArrayList<String>();
		Random rand = new Random();
		
		/*
		Adds each occurance of the next word to an arraylist
		For example, if the key 'of' has a value of '2' in the hashmap,
		It will be added twice to the arraylist randomWords.
		*/
		Set<String> keys = hm.keySet();
		for (String key: keys) {
			int occurances = hm.get(key);
			for (int i = 0; i < occurances; i++) {
				randomWords.add(key);
			}
		}

		//gets the random word
		String randomWord = randomWords.get(rand.nextInt(randomWords.size()));
		words.reset();
		return randomWord;
	}
	
	public static String sequence(WordStream words, int k, String start) throws FileNotFoundException{
		String result = start;
		String previous = start;
		for (int i = 0; i < k; i++) {
			String toAdd = randomNextWord(words, previous);
			result = result + " " + toAdd;
			previous = toAdd;
		}
		
		words.reset();
		return result;
	}
	
    public static void main(String[] args) throws FileNotFoundException {
		WordStream words = new WordStream("mobydick.txt");
		ArrayList<String> results = new ArrayList<String>();
		
		//first analysis
		long startTime = System.nanoTime();
		int length = countLength(words);
		long elapsedTime = System.nanoTime() - startTime;
		results.add("Counted total length " + length + " in " + elapsedTime + " nanoseconds.");

		// Calling 1a
		startTime = System.nanoTime();
		length = countUnique1A(words);
		elapsedTime = System.nanoTime() - startTime;
		results.add("Function 1A found " + length + " unique words in " + elapsedTime + " nanoseconds.");
		// Calling 1b
		startTime = System.nanoTime();
		length = countUnique1B(words);
		elapsedTime = System.nanoTime() - startTime;
		results.add("Function 1B found " + length + " unique words in " + elapsedTime + " nanoseconds.");
		// Calling 1c
		startTime = System.nanoTime();
		length = countUnique1C(words);
		elapsedTime = System.nanoTime() - startTime;
		results.add("Function 1C found " + length + " unique words in " + elapsedTime + " nanoseconds.");

		//Q2
		results.add("Function 2A found " + countWordFreq(words) + " words that occur once.");
		
		//outputing results to file
		addToFile(results, "output.txt");
		
		//printing results
		//printResults(results);
		
		//Q2B
		//System.out.println(question2B(words));
		
		//Q3A
		//randomNextWord(words, "cascade");
		
		//Q3B
		System.out.println(sequence(words, 3, "cascade"));
		
    }

}
