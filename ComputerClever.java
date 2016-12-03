/** 
 * @author Rudy Gonzalez
 * @date 02/3/2013
 * Title: ComputerClever.java
 * Description: Implements ComputerPlayer interface and creates an algorithm that combines systematic and random routines
 * to improve the computer's probability of guessing a correct letter and keyphrase.
 **/
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JOptionPane;


public class ComputerClever extends HangmanLogic implements ComputerPlayer 
{
	private char guess;
	private Random generator; 
	public static ArrayList<Character> mostCommonLetters = new ArrayList<Character>();
	public static ArrayList<Character> secondCommonLetters = new ArrayList<Character>();
	public static ArrayList<Character> leastCommonLetters = new ArrayList<Character>();
	
	WordsAndLetters wordLetterObject = new WordsAndLetters();
	HangmanLogic gameLogic = new HangmanLogic();
	
	//default constructor
	public ComputerClever()
	{
		loadLetterArrays();
		generator = new Random();
	}

	/**
	 * Creates letter arrays consisting of three groups: the most frequently occuring letters, 
	 * the second most frequently occuring letters and the least frequently occurring letters.
	 */
	public void loadLetterArrays()
	{
		String firstLetters = "ETAOINSHRDLCU";
		for(int i = 0; i < firstLetters.length(); i++)
		{
			//verifies that letters used by the computer from the mostCommonLetters list are also contained within the letters.txt file 
			if(wordLetterObject.getLetterBank().contains(firstLetters.charAt(i)))
			{
				mostCommonLetters.add(firstLetters.charAt(i));
			}
			else
			{
				JOptionPane.showMessageDialog(null, "There is a problem involving the letters.txt file. Check the file to make sure it contains only valid letters a to z.");
			}
		}
		
		String secondLetters = "MWFGYPB";
		for(int i = 0; i < secondLetters.length(); i++)
		{
			//verifies that letters used by the computer from the secondCommonLetters list are also contained within the letters.txt file 
			if(wordLetterObject.getLetterBank().contains(secondLetters.charAt(i)))
			{
				secondCommonLetters.add(secondLetters.charAt(i));
			}
			else
			{
				JOptionPane.showMessageDialog(null, "There is a problem involving the letters.txt file. Check the file to make sure it contains only valid letters a to z.");
			}
		}
		
		String leastLetters = "VKJXQZ";
		for(int i = 0; i < leastLetters.length(); i++)
		{
			//verifies that letters used by the computer from the secondCommonLetters list are also contained within the letters.txt file 
			if(wordLetterObject.getLetterBank().contains(leastLetters.charAt(i)))
			{
				leastCommonLetters.add(leastLetters.charAt(i));
			}
			else
			{
				JOptionPane.showMessageDialog(null, "There is a problem involving the letters.txt file. Check the file to make sure it contains only valid letters a to z.");
			}
		}	
	}
	
	/**
	 * Creates an algorithm to improve the computer's probability of guessing a correct letter.
	 * Incorporates partial systematic, random and comparison routines.
	 */
	@Override
	public void computerGuess()
	{		
		int i = 0;

		//first picks a random letter from a pool of the most common letters
		if(!gameLogic.getGuessedCharacters().containsAll(mostCommonLetters))
		{
			i = generator.nextInt(mostCommonLetters.size());
			guess = mostCommonLetters.get(i);
		}
			
		//after using all the letters from the first group, then picks a random letter from a pool of the second most group of letters
		if(gameLogic.getGuessedCharacters().containsAll(mostCommonLetters) && !gameLogic.getGuessedCharacters().containsAll(secondCommonLetters))
		{
			i = generator.nextInt(secondCommonLetters.size());
			guess = secondCommonLetters.get(i);
		}
	
		//finally picks a random letter from a pool of the least commont group of letters
		if(gameLogic.getGuessedCharacters().containsAll(mostCommonLetters) && gameLogic.getGuessedCharacters().containsAll(secondCommonLetters) && !gameLogic.getGuessedCharacters().containsAll(leastCommonLetters))	
		{
			i = generator.nextInt(leastCommonLetters.size());
			guess = leastCommonLetters.get(i);
		}
	}
	
	/**
	 * Getter method to return computer generated guess
	 * @return guess
	 */
	public char getComputerGuess()
	{
		return guess;
	}

}
