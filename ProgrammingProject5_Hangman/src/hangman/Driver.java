//Matthew Wheeler, Intro 3, Professor Kaitlyn Reinan

package hangman;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;


public class Driver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Map<Integer, Set<String>> lengthToWords = new HashMap<Integer, Set<String>>();
		Set<String> words;
		String word;
		Scanner file=null;
		Scanner keyboard = new Scanner(System.in);
		String cont;
		int length=1;
		int guesses=1;
		String guess;
		MaliciousHangman game;
		
		
		//the hashmap has the size of the word as the key and sets for the values
		//if no word has been entered of x size, then make a new set and input it for x size
		//if a set has been entered for x size, then grab the set and add to it
		try {
			file = new Scanner(new FileInputStream("dictionary.txt"));
			while(file.hasNext()) {
				word=file.nextLine();
				words = lengthToWords.get(word.length());
				if(words == null) {
					  words = new TreeSet<String>();
					  lengthToWords.put(word.length(), words);
				}
				words.add(word);
			}
		}catch(FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
		
		
		
		while(true) {
			
			//initializes game
			System.out.println("Enter word length");
			try {
			    length = Integer.parseInt(keyboard.nextLine());
			} catch (NumberFormatException e) {
			    e.printStackTrace();
			}
			
			System.out.println("Enter guess amount");
			try {
			    guesses = Integer.parseInt(keyboard.nextLine());
			} catch (NumberFormatException e) {
			    e.printStackTrace();
			}
			
			
			game=new MaliciousHangman(lengthToWords.get(length), length, guesses);
			
			//continue making guesses until the game is won or lost
			while(!game.isGameOver()) {
				//runs game and repeatedly asks for guesses until a win on loss is determined
				System.out.println(game.getGuessesRemaining() + " guesses remaining... " + game.toString());
				System.out.println("Enter a guess: ");
				guess = keyboard.nextLine();
				
				game.makeGuess(guess.charAt(0));
			}
			
			//endings
			if(game.isGameWon()) System.out.println("You won! The word was: " + game.getWord());
			if(game.isGameLost()) System.out.println("You lost ;-;, the word was: " + game.getWord());
			
			//if they want to stop playing then type in n
			//otherwise I give the player the illusion it mattered that they typed y
			System.out.println("Would you like to continue Y/N");
			cont="";
			cont=keyboard.nextLine();
			if(cont.contains("n") || cont.contains("N")) break;
		}

	}

}
