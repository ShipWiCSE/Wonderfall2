import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
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

	public void createAndShowGUI()
	{
		imagePanel = new ImagePanel();

		JFrame frame = new JFrame("Wonderfall");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new GridLayout(0, 2));
		frame.setSize(new Dimension(900, 400));

		JTabbedPane tabbedPanel = new JTabbedPane();
		JPanel textEditingPanel = makeTextEditingPanel();

		JPanel doodlePanel = new JPanel();
		JPanel presetPanel = new JPanel();

		tabbedPanel.addTab("Text", textEditingPanel);
		tabbedPanel.addTab("Doodle", doodlePanel);
		tabbedPanel.addTab("Presets", presetPanel);
		frame.add(tabbedPanel);

		JScrollPane scroller = new JScrollPane(imagePanel,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroller.setPreferredSize(new Dimension(0, 500));
		frame.add(scroller);

		frame.setVisible(true);
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
				imagePanel.setFont(new Font((String) font.getSelectedItem(),Font.PLAIN, 32));
				imagePanel.setStringToPrint(textField.getText());
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

}
