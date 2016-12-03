/** 
 * @author Rudy Gonzalez
 * @date 02/3/2013
 * Hangman - Simple GUI game
 * Description: Plays the game of hangman with the choice of a human or computer player.
 * It includes 9 classes and uses the following files: words.txt, letters.txt and 11 gifs
 * that are used to depict the hangman drawings
 **/

/**
 * @author Rudy Gonzalez
 * Title: HangmanGame.java
 * This is the main driver class that instantiates the GUI and WordAndLetters classes. 
 */
public class HangmanGame 
{
	/**
	 * @param lettersPath
	 * @param wordsPath
	 * @param computerPlayerType
	 */
	public static void main(String[] args) 
	{
		//You can set the computer player as "clever" or "random" with "clever" making smarter letter picks
		String computerPlayerType = "clever";
					
		String keyPhrase;
		int numberOfGuesses = 10;
		
		WordsAndLetters wordLetterObject = new WordsAndLetters();
		wordLetterObject.loadLetters();
		
		wordLetterObject.loadWords();
		keyPhrase = wordLetterObject.randomPhrase();
		boolean badPhrase = false;
		HangmanGUI frame = null;
		
		/**
		 * Instantiates the HangmanGUI class and passes the random keyphrase, number of guesses allowed and computer player type.
		 * Checks for a "bad" keyphrase with invalid characters.
		 */
		do
		{	
			try
			{
				frame = new HangmanGUI(keyPhrase, numberOfGuesses, computerPlayerType);
				badPhrase = false;
			}
			
			catch (IllegalArgumentException e)
			{
				keyPhrase = wordLetterObject.randomPhrase();
				badPhrase = true;
			}
		}while(badPhrase);
		
		frame.setVisible(true);
	}	
}
