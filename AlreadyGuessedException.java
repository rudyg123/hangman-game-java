/** 
 *  This is an exception intended for being thrown when a new guess has already been made
 **/
public class AlreadyGuessedException extends Exception 
{	
    public AlreadyGuessedException(String message)
    {
		super(message);
    } 
}
