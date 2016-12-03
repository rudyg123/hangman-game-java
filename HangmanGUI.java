/** 
 * @author Rudy Gonzalez
 * @date 02/3/2013
 * Title: HangmanGUI.java
 * Description: The graphic user interface that constructs the frame, panels, labels and buttons.
 * It includes choice buttons for human or computer player. It also accepts button inputs for letter guesses.
 * Hangman drawings provide a visual representation of the player's progress.
 **/

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class HangmanGUI extends JFrame
{

	private static final long serialVersionUID = 1L;
	public static final int ALPHABET_SIZE = 26;
	final char[] letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
	
	//frame and panel variables
	private int frameWidth = 750;
	private int frameHeight= 740;
	private JPanel mainPanel;
	private JPanel centerPanel;
	private JPanel phrasePanel;
	private JPanel letterPanel;
	private JPanel topMessagePanel;
	private JPanel lowerMessagePanel;
	private JPanel gameChoicePanel;
	private JPanel drawingPanel;
	
	//GUI label variables
	private JLabel phraseDisplay;
	private JLabel mainMessage;
	private JLabel hangman;
	
	//GUI initial button variables
	private JButton prisonerButton;
	private JButton hangmanButton;
	private JButton nextButton;
	
	//game variables
	private HangmanLogic gameLogic;
	private static char guess;
	int numberOfGuesses;
	String computerPlayerType;
	private boolean computerGameOver = false;
	private String nameIndex;
	private String drawingFileName;
	
	HangmanGame hangMain = new HangmanGame();
	WordsAndLetters wordLetterObject = new WordsAndLetters();
	ComputerPlayer pcPlayer;
	
	/**
	 * Constructor for game frame and panels
	 * @param keyPhrase, random keyphrase
	 * @param numberOfGuesses, number of guesses permitted
	 * @param computerPlayerType, computer player type ("r" for random, "c" for clever
	 */
	public HangmanGUI(String keyPhrase, int numberOfGuesses, String computerPlayerType)
	{
		gameLogic = new HangmanLogic(keyPhrase, numberOfGuesses);
		this.computerPlayerType = computerPlayerType;
		
		//creates computer player type object based on argument passed from command line
		if(computerPlayerType.equals("random"))
		{
			pcPlayer = new ComputerRandom();
		}
		else if(computerPlayerType.equals("clever"))
		{
			pcPlayer = new ComputerClever();
		}
				
		setSize(frameWidth,frameHeight);
		setTitle("Hangman Game");
	   setDefaultCloseOperation(EXIT_ON_CLOSE);	   
	   setLocationRelativeTo(null); //Centers the JFrame in the middle of the screen
	   
	   //Constructs game panels
		   mainPanel = new JPanel(new BorderLayout());
		   centerPanel = new JPanel(new GridLayout(3,1));
		   topMessagePanel = new JPanel();
		   lowerMessagePanel = new JPanel();
		   gameChoicePanel = new JPanel(new GridLayout(1,1));
		   phrasePanel = new JPanel();
		   letterPanel = new JPanel(new GridLayout(4,8));
		   drawingPanel = new JPanel();	
	   
		   //creates JLabels
	   //guessLetterLabel = new JLabel("Letters Guessed:\n");
	   mainMessage = new JLabel();
	   mainMessage.setFont(new Font("Comic Sans",20,20));
	   mainMessage.setForeground(Color.BLUE);
	   phraseDisplay = new JLabel(gameLogic.getKnownKeyPhrase());
	   phraseDisplay.setFont(new Font("Arial Bold",25,25));	   
	   hangman = new JLabel();
	   
	   drawHangman();

	   //adds panels and labels to build display
	   drawingPanel.add(hangman);
	   phrasePanel.add(phraseDisplay);
	   topMessagePanel.add(drawingPanel);
	   lowerMessagePanel.add(mainMessage);
	   centerPanel.add(phrasePanel);
	   //centerPanel.add(guessLetterLabel);
	   centerPanel.add(lowerMessagePanel);
	   this.add(mainPanel);
	   mainPanel.add(topMessagePanel, BorderLayout.NORTH);
	   mainPanel.add(centerPanel, BorderLayout.CENTER);
	   
	   createLetterButtons();//helper method to add letter buttons to letter panel and allow human player to input guesses
	   
	   playerGameChoice();
	       
   //makes sure the JFrame is visible
   setVisible(true);
	}
	
	/**
	 * Creates the letter buttons the human player uses for inputting guesses. Accepts the button clicks and assigns
	 * it to the guess variable. Two methods are called to check the guess and check the game over status.
	 */
   public void createLetterButtons()
   { 	
   	JButton[] guessButton = new JButton[ALPHABET_SIZE];
	   for(int i = 0; i < ALPHABET_SIZE; i++)
	   {
		final int idx = i;
	   	guessButton[i] = new JButton(Character.toString(letters[i]));
	   	guessButton[i].setActionCommand(Character.toString(letters[i]));
	   	guessButton[i].addActionListener(new ActionListener()
	   	{	   	
	   		@Override
	   		public void actionPerformed(ActionEvent e) 
	   		{
	   			String guessString = e.getActionCommand();
	   			guess = guessString.charAt(0);
	   			guessButton[idx].setBackground(Color.green);
	   		   
	   			checkGuess();
	   			checkGameOver();
	   		}
	   	});
	
	   	letterPanel.add(guessButton[i]);    
	   }
   }
   
   /**Compares player's guess and updates the keyphrase, as well as informs the player if the letter guessed is correct, incorrect
    *or has been guessed before. Also, the previously guessed letters are dynamically displayed.
    **/
   public void checkGuess()
   {
	   	try
			{			
				if(gameLogic.guessCharacter(guess))
				{
					phraseDisplay.setText(gameLogic.getKnownKeyPhrase());
					mainMessage.setText("Yes! There is the letter " + guess + "!");
					mainMessage.setForeground(Color.BLUE);
				}
				else
				{
					mainMessage.setText("No, sorry. There is no letter " + guess + ".");
					mainMessage.setForeground(Color.RED);
					drawHangman();
				}
				//guessLetterLabel.setText("Letters Guessed:\n " + gameLogic.getGuessedCharacters());
					
			}
			catch (InvalidInputException ex)
			{
				mainMessage.setText("Only letters are accepted as a valid guess.");
				mainMessage.setForeground(Color.RED);
			}
			catch (AlreadyGuessedException exc)
			{
				mainMessage.setText("You have already guessed " + guess + ". Try again.");
				mainMessage.setForeground(Color.RED);
			}	   
   }
   
   /**
    * Tracks if the human player's game is over by either the player correctly guessing the keyphrase or if the player is out of guesses.
    * Whether the player wins or loses, the appropriate message is displayed and no further play is possible
    */
   public void checkGameOver()
   {
		if(gameLogic.isGameOver())
		{
		   if(gameLogic.getNumGuessesLeft() <= 0)
			{
		   	phraseDisplay.setText(gameLogic.keyPhrase());
		   	phraseDisplay.setForeground(Color.RED);
		   	mainMessage.setText("HANGMAN! Sorry, you lose. Game Over");
		   	mainMessage.setForeground(Color.RED);
		   	letterPanel.setVisible(false);
			}
			else
			{
				mainMessage.setText("<html><b><center>CONGRATULATIONS!<br>You WIN!<br>The Hangman has been foiled!</center></b></html>");
				mainMessage.setForeground(Color.BLUE);
				letterPanel.setVisible(false);
			}
		}   
   }
   
   //Sets hangman drawing based on number of guesses
   public void drawHangman()
   {
   	nameIndex = String.valueOf((gameLogic.getNumberOfGuesses() - gameLogic.getNumGuessesLeft()));
   	drawingFileName = "D:\\Programming\\Eclipse\\workspace\\Hangman\\src\\" + "hang" + nameIndex + ".gif";
 
   	Icon hangmanIcon = new ImageIcon(drawingFileName);
   	
   	if(!nameIndex.equals(0))
   	{
   		hangman.setIcon(hangmanIcon);
   	}
   }
   
   /**
    * Displays a welcome message and choice of players with "prisoner" being the human player guessing and "hangman" being
    * the computer player guessing.
    * Whether the player wins or loses, the appropriate message is displayed and no further play is possible
    */
   public void playerGameChoice()
   {
   	mainPanel.add(gameChoicePanel, BorderLayout.SOUTH);
   	mainMessage.setText("<html><center><b>Welcome to the gallows!<br>Choose below if you want to be the Prisoner who guesses<br> or the Hangman who prepares the computer prisoner for execution.<b></center></html>");
   	
   	prisonerButton = new JButton("Play as PRISONER");

   	prisonerButton.addActionListener(new ActionListener()
   	{
   	   @Override
   		public void actionPerformed(ActionEvent event)
   		{
   		   gameChoicePanel.setVisible(false);	
   	   	mainPanel.add(letterPanel, BorderLayout.SOUTH);
   		   letterPanel.setVisible(true);
   		   mainMessage.setText("");
   		}
   	});
   	gameChoicePanel.add(prisonerButton);
   	
   	hangmanButton = new JButton("Play as HANGMAN");
   	
   	hangmanButton.addActionListener(new ActionListener()
   	{
   	   @Override
   		public void actionPerformed(ActionEvent event)
   		{
 
   	   	nextButton = new JButton("NEXT");
   	   	gameChoicePanel.add(nextButton);
   	   	prisonerButton.setVisible(false);
   	   	hangmanButton.setVisible(false);
   	   	//welcome.setVisible(false);
   	   	
   	   	if(computerPlayerType.equals("r"))
   	   	{
   	   		mainMessage.setText("<html><b><center>Hello, my name is Lucky. I'll be playing as the prisoner.<br>Since you're the hangman and my hands are tied,<br>please click NEXT to help me guess...</center></b></html>");
   	   	}
   	   	else
   	   	{
   	   		mainMessage.setText("<html><b><center>Hello, my name is Watson. I'll be playing as the prisoner.<br>Since you're the hangman and my hands are tied,<br>please click NEXT to help me guess...</center></b></html>"); 	   		
   	   	}
 
   	   	nextButton.addActionListener(new ActionListener()
   	   	{
   	   	   @Override
   	   		public void actionPerformed(ActionEvent event)
   	   		{
   	   	   	do
   	   	   	{
   	   	   		pcPlayer.computerGuess();
   	   	   		guess = Character.toUpperCase(pcPlayer.getComputerGuess());
   	   	   		
   	   	   	}while(gameLogic.getGuessedCharacters().contains(guess));//checks and loops if computer has previously guessed the same letter	
   	   	   		   	   	
   	   	   	checkGuess();
   	   	   	pcCheckGameOver();
   	   		}
   	   	});
   		}
   	});
   	gameChoicePanel.add(hangmanButton);
   }
   
   /**
    * Tracks if the computer player's game is over by either the player correctly guessing the keyphrase or if the player is out of guesses.
    * Whether the player wins or loses, the appropriate message is displayed and no further play is possible
    * @return computerGameOver, returns boolean value
    */
   public boolean pcCheckGameOver()
   {
		if(gameLogic.isGameOver())
		{
		   if(gameLogic.getNumGuessesLeft() <= 0)
			{
		   	mainMessage.setText("HANGMAN! Yikes, I lost so that means, YOU WIN!");
		   	phraseDisplay.setText(gameLogic.keyPhrase());
		   	phraseDisplay.setForeground(Color.RED);
		   	mainMessage.setForeground(Color.RED);
		   	letterPanel.setVisible(false);
		   	computerGameOver = true;
		   	nextButton.setVisible(false);
			}
			else
			{
				mainMessage.setText("<html><b><center>Whew! I guessed the phrase, so I win!<br>Better luck next time, Mr. Hangman!</center></b></html>");
				mainMessage.setForeground(Color.BLUE);
				letterPanel.setVisible(false);
				computerGameOver = true;
				nextButton.setVisible(false);
			}
		}
		return computerGameOver;
   }
}
