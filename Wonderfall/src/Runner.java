public class Runner
{

	private static UI ui;

	public static void main(String[] args)
	{
		ui = new UI();
		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				ui.createAndShowGUI();
			}
		});
	}

}
