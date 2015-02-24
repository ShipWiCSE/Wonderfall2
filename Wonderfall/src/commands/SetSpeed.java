package commands;


/**
 * This command changes the timeout between rows of water
 * @author Merlin
 *
 * Created:  Aug 8, 2011
 */
public class SetSpeed implements Command
{
	
	private int speed;

	/**
	 * @param speed the rate at which each row of the pattern should be displayed
	 */
	public SetSpeed(int speed)
	{
		this.speed = speed;
	}

	/**
	 * Nope - all is lost with this one
	 * @see commands.Command#getUndoable()
	 */
	@Override
	public boolean getUndoable()
	{
		return false;
	}

	/**
	 * @see commands.Command#buildCommandString()
	 */
	public String buildCommandString()
	{
		String result = "";
		result = result + "SET TIMEOUT " + speed + "\n";
		result = result + "RUN\n";
		return result;
	}

	/**
	 * @see commands.Command#undo()
	 */
	@Override
	public void undo()
	{
		
	}

	protected int getSpeed()
	{
		return speed;
	}

}
