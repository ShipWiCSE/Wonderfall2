import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

public class UI
{

	private static final String[] FONTS =
	{ "Arial", "Courier", "Helvetica", "Times New Roman" };
	private JTextField textField;
	private JComboBox<String> font;
	private ImagePanel imagePanel;
	private ImageIcon imageIcon;
	private DrawableIcon drawableIcon;

	public void createAndShowGUI() throws FileFormatException, IOException
	{
		imagePanel = new ImagePanel();

		JFrame frame = new JFrame("Wonderfall");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new GridLayout(0, 2));
		frame.setSize(new Dimension(900, 400));

		JTabbedPane tabbedPanel = new JTabbedPane();
		JPanel textEditingPanel = makeTextEditingPanel();

		JPanel doodlePanel = new JPanel();
		JPanel presetPanel = makePresetPanel();

		tabbedPanel.addTab("Text", textEditingPanel);
		tabbedPanel.addTab("Doodle", doodlePanel);
		tabbedPanel.addTab("Presets", presetPanel);
		frame.add(tabbedPanel);

		JScrollPane scroller = new JScrollPane(imagePanel,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		// scroller.setPreferredSize(new Dimension(0, 500));
		frame.add(scroller);

		frame.setVisible(true);
	}

	private JPanel makePresetPanel() throws FileFormatException, IOException
	{
		JPanel presetPanel = new JPanel();
		presetPanel.setLayout(new FlowLayout());

		PresetFileReader fileReader = new PresetFileReader();
		ArrayList<DrawableIcon> presets = fileReader
				.readFile("presetDiagonals.txt");

		for (int i = 0; i < presets.size(); i++)
		{
			drawableIcon = presets.get(i);
			imageIcon = new ImageIcon(drawableIcon
					.getImage());
			JButton button = new JButton(imageIcon);
			button.addActionListener(new PresetListener(drawableIcon));
			presetPanel.add(button);
		}
		return presetPanel;
	}

	private JPanel makeTextEditingPanel()
	{
		JPanel textEditingPanel = new JPanel();

		textEditingPanel.setLayout(new FlowLayout());
		makeFontDropDown();
		textEditingPanel.add(font);
		JButton add = makeAddButton();
		JButton clear = makeClearButton();
		JButton save = new JButton("Save");
		textField = new JTextField(15);
		textField.setFont(new Font(FONTS[0], Font.PLAIN, 32));
		textEditingPanel.add(textField);
		textEditingPanel.add(add);
		textEditingPanel.add(clear);
		textEditingPanel.add(save);
		return textEditingPanel;
	}

	private void makeFontDropDown()
	{
		font = new JComboBox<String>();
		for (String f : FONTS)
		{
			font.addItem(f);
		}
		font.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				String fontName = (String) font.getSelectedItem();
				textField.setFont(new Font(fontName, Font.PLAIN, 32));
			}
		});
	}

	private JButton makeAddButton()
	{
		JButton add = new JButton("Add");
		add.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				imagePanel.addText(textField.getText(),
						(new Font((String) font.getSelectedItem(), Font.PLAIN,
								32)));
			}

		});
		return add;
	}

	private JButton makeClearButton()
	{
		JButton clear = new JButton("Clear");
		clear.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				textField.setText("");
			}
		});
		return clear;
	}

	private class PresetListener implements ActionListener
	{

		private DrawableIcon icon;
		public PresetListener(DrawableIcon icon)
		{
			this.icon = icon;
		}
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			System.out.println("Trying to add the image");
			imagePanel.addImage(icon);
		}
	}
}
