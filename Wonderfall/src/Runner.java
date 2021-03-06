import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Runnable class that creates and runs the UI
 * @author Merlin
 *
 */
public class Runner
{

	private static UI ui;

	/**
	 * 
	 * @param args none are used now
	 */
	public static void main(String[] args)
	{
		ui = new UI();
		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					ui.createAndShowGUI();
				} catch (FileNotFoundException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (FileFormatException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

}
