
/**
 * An exception of problems with reading preset files
 * @author Merlin
 *
 */
public class FileFormatException extends Exception
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @param string a message describing the problem
	 */
	public FileFormatException(String string)
	{
		super(string);
	}

}
