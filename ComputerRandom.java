/** 
 * @author Rudy Gonzalez
 * @date 02/3/2013
 * Title: ComputerRandom.java
 * Description: Implements ComputerPlayer interface and creates a purely random computer-generated guess
 * for the random computer player.
 **/

import java.util.Random;


public class ComputerRandom implements ComputerPlayer
{
	private char guess;
	WordsAndLetters wordLetterObject = new WordsAndLetters();
	HangmanLogic gameLogic = new HangmanLogic();
	
	/**
	 * Creates a purely random guess for the computer random player type
	 */
	@Override
	public void computerGuess()
	{
		int i = 0;
		Random generator = new Random();
		i = generator.nextInt(wordLetterObject.getLetterBank().size());
		guess = wordLetterObject.getLetterBank().get(i);
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
