/**
 * @author Rudy Gonzalez
 * @date: 2/3/13
 * Title: WordsAndLetters.java
 * Description: This class takes care of reading in the data from the letters.txt and words.txt files.
 **/

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JOptionPane;


public class WordsAndLetters 
{
	public static ArrayList<Character> letterBank = new ArrayList<Character>();
	public static ArrayList<String> wordBank = new ArrayList<String>();
	private String keyPhrase;
	
	/**
	 * Loads the letters from the letters.txt file. Includes error-handling routines to catch
	 * file and data input related errors.
	 */
	public void loadLetters()
	{	
		try
		{
			URL lettersUrl = getClass().getResource("letters.txt");
			File lettersFile = new File(lettersUrl.getPath());
			Scanner lettersFileReader = new Scanner(lettersFile);
			
			if(lettersFileReader.hasNextLine())
			{
				while(lettersFileReader.hasNext())
				{
					char temp = lettersFileReader.next().charAt(0);
					letterBank.add(temp);
				}				
			}
			
			lettersFileReader.close();
		}
		catch(FileNotFoundException e)
		{
			JOptionPane.showMessageDialog(null, "There was a file error involving the letters.txt file. Check the file name or path and try again.");
		}	
	}
	
	/**
	 * Getter method to returns the values of the letterBank arraylist.
	 * @return letterBank  
	 */
	public ArrayList<Character> getLetterBank()
	{
		return letterBank;
	}

	/**
	 * Loads the words from the words.txt file. Includes error-handling routines to catch
	 * file and data input related errors.
	 */
	public void loadWords()
	{		
		try
		{
			URL wordsUrl = getClass().getResource("words.txt");
			File wordsFile = new File(wordsUrl.getPath());
			Scanner wordsFileReader = new Scanner(wordsFile);
			
			if(wordsFileReader.hasNextLine())
			{
				while(wordsFileReader.hasNext())
				{
					wordBank.add(wordsFileReader.nextLine());
				}				
			}

			wordsFileReader.close();
		}
		catch(FileNotFoundException e)
		{
			JOptionPane.showMessageDialog(null, "There was a file error involving the words.txt file. Check the file name or path and try again.");
		}
	}
	
	/**
	 * Generates the random keyphrase used in the game
	 * @return keyPhrase
	 */
	public String randomPhrase()
	{
		Random generator = new Random();
		int i = generator.nextInt(wordBank.size());
		keyPhrase = wordBank.get(i);

		return keyPhrase;
	}
	

}