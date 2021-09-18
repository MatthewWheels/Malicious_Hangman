//Matthew Wheeler, Intro 3, Professor Kaitlyn Reinan

package hangman;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class MaliciousHangman extends Hangman{
	private Set<String> set;
	private Map<String, Set<String>> scoreboard;
	private Set<String> words;
	private Set<String> keys;
	private String bestKeyOption;
	private char[] previousState;
	
	
	//constructor
	public MaliciousHangman(Set<String> dictionary, int length, int guesses) {
		super(length, guesses);
		set=dictionary;
		previousState=new char[length];
		
	}
	
	//This populates the hashMap scoreboard
	//Goes through each word in the main set
	//Finds the key for the word using the endResult helper function
	//if its a new key make a new set and add it to the hashMap
	//if it already exists add the word to the existing set
	private void populateScoreboard(char c) {
		scoreboard = new HashMap<String, Set<String>>();
		keys=new TreeSet<String>();
		String word;
		Iterator<String> it  = set.iterator();
		while(it.hasNext()) {
			word=it.next();
			words = scoreboard.get(endResult(c, word));
			if(words == null) {
				  words = new TreeSet<String>();
				  scoreboard.put(endResult(c, word), words);
				  keys.add(endResult(c, word));
			}
			words.add(word);
		}
	}
	
	
	//finds the resulting char array if the word were the answer
	//the result will be used as a key for the word and to fill in the state array
	private String endResult(char c, String word) {
		char[] charArray = new char[word.length()];
		String charString="";
		for(int i=0; i<word.length(); i++) {
			if(word.charAt(i)==c) {
				charArray[i]=c;
			} else {
				charArray[i]=BLANK;
			}
		}
		
		for(int i=0; i<word.length(); i++) {
			charString+=charArray[i];
		}
		return charString;
	}
	
	//find which key has the most options in the hashmap of sets
	//does so by comparing the size of each set in the hashmap
	private String findBestKey() {
		Iterator<String> keyIt = keys.iterator();
		String bestKey="";
		int bestKeySize=0;
		String currentKey;
		
		while(keyIt.hasNext()) {
			currentKey=keyIt.next();
			if(scoreboard.get(currentKey).size()>bestKeySize) {
				bestKey=currentKey;
				bestKeySize=scoreboard.get(currentKey).size();
			}
		}
		return bestKey;
	}
	
	//checks if the previous state is different from the current state
	private boolean checkForChange() {
		for(int i=0; i<previousState.length; i++) {
			if(previousState[i]!=state[i])return true;
		}
		return false;
	}

	@Override
	protected boolean makeNewGuess(char c) {
//Create a hashmap of sets of end states
	//--- would be a key, --a could be another, and a-a could be another
		populateScoreboard(c);
//Compare the size of each key's set of the hashmap to determine which result has the most options
		bestKeyOption=findBestKey();
//Set the main set to the set selected by the previous step
		set=scoreboard.get(bestKeyOption);
//store the state of the answer then reveal the result
		for(int i=0; i<state.length; i++) {
		previousState[i]=state[i];
		}
		
		for(int i=0; i<bestKeyOption.length(); i++) {
			if(bestKeyOption.charAt(i)!='-')state[i]=bestKeyOption.charAt(i);
		}
//if any letters were revealed return true
		return checkForChange();
	}

	@Override
	public String getWord() {
		Iterator<String> it = set.iterator();
		return it.next();
	}

}
