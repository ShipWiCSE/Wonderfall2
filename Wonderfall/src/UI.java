import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import backend.Invoker;
import commands.FullSetUp;

/**
 * 
 */
public class UI
{

	private static final int FONT_SIZE = 32;
	private static final String PRESETS_DIRECTORY = "presets";
	private static final String[] FONTS =
	{ "Arial", "Courier", "Helvetica", "Times New Roman" };
	protected static final int NUMBER_OF_VALVES = 32;
	protected static final int FONT_OVERSIZE = 10;
	private JTextField textField;
	private JComboBox<String> font;
	private ImagePanel imagePanel;
	private ImageIcon imageIcon;
	private DrawableIcon drawableIcon;
	private JComboBox<String> presetFilesBox;
	private JPanel presetIconsPanel;
	private static JSpinner speed;

	/**
	 * @throws FileFormatException
	 *             shouldn't
	 * @throws IOException
	 *             shouldn't
	 */
	public void createAndShowGUI() throws FileFormatException, IOException
	{
		imagePanel = new ImagePanel();
		setUpSpeedInput();

		JFrame frame = new JFrame("Wonderfall");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new GridLayout(0,3));
		frame.setSize(new Dimension(900, 400));

		JTabbedPane tabbedPanel = new JTabbedPane();
		JPanel textEditingPanel = makeTextEditingPanel();

		JPanel doodlePanel = new JPanel();
		JPanel presetPanel = makePresetPanel(tabbedPanel.getWidth(), tabbedPanel.getHeight());

		tabbedPanel.addTab("Text", textEditingPanel);
		tabbedPanel.addTab("Doodle", doodlePanel);
		JScrollPane scrollerPresets = new JScrollPane(presetPanel,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		tabbedPanel.addTab("Presets", scrollerPresets);
		frame.add(tabbedPanel);
		
		JPanel controlPanel = new JPanel();
		JButton sendButton = new JButton("Send");
		sendButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e)
			{
				boolean[][] bits = imagePanel.getImageAsBits();
				FullSetUp command= new FullSetUp(bits,150);
                Invoker.getSingleton().execute(command);
			}
			
		});
		controlPanel.add(sendButton);
		controlPanel.add(speed);
		
		frame.add(controlPanel);

		JScrollPane scroller = new JScrollPane(imagePanel,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		// scroller.setPreferredSize(new Dimension(0, 500));
		frame.add(scroller);

		frame.setVisible(true);
	}

	private void setUpSpeedInput()
	{
		Integer value = new Integer(100);
		 Integer min = new Integer(50);
		 Integer max = new Integer(250);
		 Integer step = new Integer(10);
		 SpinnerNumberModel model = new SpinnerNumberModel(value, min, max, step);
		speed = new JSpinner(model);
	}

	private JPanel makePresetPanel(int width, int height) throws FileFormatException, IOException
	{
		JPanel presetPanel = new JPanel();
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 0;
		presetPanel.setLayout(new GridBagLayout());
		
		presetFilesBox = new JComboBox<String>();
		
		File[] presetFiles = getPresetFiles();
		for (File f : presetFiles)
		{
			presetFilesBox.addItem(f.getName());
		}
		presetFilesBox.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				String presetFileTitle = PRESETS_DIRECTORY + "/" + (String) presetFilesBox.getSelectedItem();
				rebuildPresetButtonPanel(presetFileTitle);
			}
		});
		constraints.gridx = 0;
		constraints.gridy = 0;
		presetPanel.add(presetFilesBox, constraints);
		
		presetIconsPanel = new JPanel();
		presetIconsPanel.setLayout(new GridLayout(0,6));
		
//		scroller.setPreferredSize(new Dimension(width,height));
//		scroller.setMaximumSize(new Dimension(width,height));
//		scroller.setMinimumSize(new Dimension(width,height));
		constraints.gridx = 0;
		constraints.gridy = 1;
		presetPanel.add(presetIconsPanel, constraints);
		
		return presetPanel;
	}

	private void rebuildPresetButtonPanel(String fileTitle)
	{
		presetIconsPanel.removeAll();
		PresetFileReader fileReader = new PresetFileReader();
		ArrayList<DrawableIcon> presets;
		try
		{
			presets = fileReader.readFile(fileTitle);
			for (int i = 0; i < presets.size(); i++)
			{
				drawableIcon = presets.get(i);
				imageIcon = new ImageIcon(drawableIcon.getImage());
				JButton button = new JButton(imageIcon);
				button.addActionListener(new PresetListener(drawableIcon));
				presetIconsPanel.add(button);
			}
		} catch (FileFormatException | IOException e)
		{
			e.printStackTrace();
		}
		presetIconsPanel.revalidate();
	}

	private File[] getPresetFiles()
	{
		File folder = new File(PRESETS_DIRECTORY);
		File[] list = folder.listFiles();

		return list;
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
		textField = new JTextField(10);
		textField.setFont(new Font(FONTS[0], Font.PLAIN, FONT_SIZE));
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
				textField.setFont(new Font(fontName, Font.PLAIN, NUMBER_OF_VALVES));
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
								NUMBER_OF_VALVES + FONT_OVERSIZE)));
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
			imagePanel.addImage(icon);
		}
	}
}
