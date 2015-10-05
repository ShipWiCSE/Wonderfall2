import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class StupidRunner
{
	public static void main(String[] args)
	{
		JFrame frame = new JFrame();
		final DoodlePad drawPad = new DoodlePad();
		frame.add(drawPad, BorderLayout.CENTER);
		JButton clearButton = new JButton("Clear");
		clearButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				drawPad.clear();
			}
		});
		frame.add(clearButton, BorderLayout.SOUTH);
		frame.setSize(280, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

}