package commands;

/**
 * This command opens all valves to appear uniform while changing viewing objects. 
 * @author Olivia & Bhavini
 *
 * Created: 2/24/15
 */
public class Flood implements Command
{

	public Flood()
	{
		
	}
	
	@Override
	public boolean getUndoable()
	{
		return false;
	}

	@Override
	public void undo()
	{		
	}

	/**
	 * @see commands.Command#buildCommandString()
	 */
	@Override
	public String buildCommandString()
	{
		String result = "debug 0xffffffff\n";
		return result;
	}

}
