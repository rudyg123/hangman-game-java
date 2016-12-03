/**
 * @author Rudy Gonzalez
 * @date: 2/3/13
 * Title: HangmanLogic.java
 * Description: This class contains the logic elements for the Hangman game. It uses a constructor and public methods to store
 * information about the letters in the keyphrase, evaluate user guesses, track the number of guesses, and returns
 * game over when there are no guesses left.
 **/

import java.util.ArrayList;


public class HangmanLogic 
{
	private String keyPhrase;
	private int numberOfGuesses;
	private int numberOfGuessesLeft;
	public static ArrayList<Character> guessedCharacters;
	
	public HangmanLogic()
	{
		//default constructor for use in other classes that need access to class variables and methods
		guessedCharacters = new ArrayList<Character>();
	}
	/**
	 * This is the main logic constructor used in the game. It checks the keyphrase to ensure no special characters
	 * or numbers are contained. It also accepts the variable containing the number of guesses allowed.
	 * @param keyPhrase, the word or phrase that will be guessed
	 * @param numberOfGuesses, the maximum amount of guesses permitted
	 * @throw IllegalArgumentException, occurs when the keyphrase contains special characters or numbers
	 **/
	public HangmanLogic(String keyPhrase, int numberOfGuesses)
	{		
		this.keyPhrase = keyPhrase.toUpperCase();
		this.numberOfGuesses = numberOfGuesses;
		this.numberOfGuessesLeft = numberOfGuesses;
		guessedCharacters = new ArrayList<Character>();
		
		//Checks to ensure only letters and whitespace is in the keyphrase. Special characters or numbers throw exception. 
		for(int i = 0; i < keyPhrase.length(); i++)
		{
			if(!Character.isLetter(keyPhrase.charAt(i)) && !Character.isWhitespace(keyPhrase.charAt(i)))
			{
				throw new IllegalArgumentException();//handled by calling method in driver class
			}
		}
	}
	
	/**
	 * This method returns the word or phrase being guessed.
	 * @return keyPhrase, the word or phrase
	 **/
	public String keyPhrase()
	{
		return keyPhrase;
	}
	
	/**
	 * method returns the keyPhrase field in an encoded manner with asterisks representing the still unknown letters and letters for the known letters
	 * @return  returns the encoded keyPhrase of this object (letters known and dashes)
	 */
	public String getKnownKeyPhrase()
	{
		//char[] encodedKeyPhrase = new char[keyPhrase.length()];
		
		//create StringBuilder sb object
		StringBuilder sb = new StringBuilder();
		
		for(int i = 0; i < keyPhrase.length(); i++)
		{
			char cur = keyPhrase.charAt(i);
			if(guessedCharacters.contains(cur))
				sb.append(cur);
				//encodedKeyPhrase[i] = cur;
			else
				sb.append(" __ ");
				//encodedKeyPhrase[i] = '*';
		}
		//return (new String(encodedKeyPhrase));
		return sb.toString();
	}
	/**
	 * This method returns the contents of the guessedCharacters array list
	 * @return guessedCharacters
	 */
	public ArrayList<Character> getGuessedCharacters()
	{
		return guessedCharacters;	
	}
	
	/**
	 * This method returns the number of guesses allowed before failure.
	 * @return numberOfGuesses, maximum guesses allowed
	 **/
	public int getNumberOfGuesses()
	{
		return numberOfGuesses;
	}
	
	/**
	 * This method calculates the number of user guesses that are left.
	 * @return numberOfGuessesLeft, guesses remaining
	 **/
	public int getNumGuessesLeft()
	{
		return numberOfGuessesLeft;
	}
	
	/**
	 * This method returns true if the numberOfGuessesLeft variable is 0 and false otherwise.
	 * @return gameOver, boolean value whether game is over
	 **/
	public boolean isGameOver()
	{
		boolean guessed = (getKnownKeyPhrase().equals(keyPhrase.toUpperCase()));		
		return( numberOfGuessesLeft == 0 || guessed);		
	}
	
	/**
	 * This method returns whether a guessed character is a part of the keyphrase. If guessed
	 * character has at least one match, then true is returned, otherwise, false is returned.
	 * If a letter has been guessed before, an exception is thrown and handled by the calling method.
	 * @param guess, guessed letter
	 * @return guessCorrect, boolean value returns true if guess is correct, false if incorrect
	 * @throws AlreadyGuessedException, occurs if the guessed character has been guessed before
	 **/
	public boolean guessCharacter(char guess) throws InvalidInputException, AlreadyGuessedException
	{
		if(isValidCharacter(guess))
		{
			//force into one case
			guess = Character.toUpperCase(guess);
			
			if(guessedCharacters.contains(guess))
			{
				throw new AlreadyGuessedException("" + guess);
			}
			else
			{
				guessedCharacters.add(guess);
				if(keyPhrase.contains("" + guess))
					return true;
				else
				{
					numberOfGuessesLeft--;
					return false;
				}
			}		
		}
		else
		{
			throw new InvalidInputException("" + guess);
		}
	}
	
	/**
	 * Returns whether the character parameter is considered valid in this game
	 * @param guess  the character being checked for validity
	 * @return  returns whether the character parameter is considered valid in this game
	 */
	private boolean isValidCharacter(char guess)
	{
		return Character.isLetter(guess);
	}
		

	
}
