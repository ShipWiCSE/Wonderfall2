import java.awt.BorderLayout;
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

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.TitledBorder;

import backend.Invoker;
import commands.Flood;
import commands.FullSetUp;
import commands.SetSpeed;
import commands.Stop;

/**
 * 
 */
public class UI
{

	private static final int FONT_SIZE = 32;
	private static final String PRESETS_DIRECTORY = "presets";
	 static final int DOODLE_WIDTH_MULTIPLIER = 5;
	private static final String[] FONTS =
	{ "Arial", "Courier", "Helvetica", "Times New Roman", "Agency FB" };
	protected static final int NUMBER_OF_VALVES = 32;
	protected static final int FONT_OVERSIZE = 10;
	static final int NUMBER_OF_DOODLE_ROWS = 100;
	private JTextField textField;
	private JComboBox<String> font;
	private ImagePanel imagePanel;
	private ImageIcon imageIcon;
	private DrawableIcon drawableIcon;
	private JComboBox<String> presetFilesBox;
	private JPanel presetIconsPanel;
	private JPanel presetPanel;
	private static JSpinner speed;
	DoodlePad doodlePad;

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
		frame.setLayout(new GridLayout(0, 3));
		frame.setSize(new Dimension(1000, 600));

		JTabbedPane tabbedPanel = new JTabbedPane();
		JPanel textEditingPanel = makeTextEditingPanel();

		JPanel doodlePanel = new JPanel();
		doodlePad = new DoodlePad();
		doodlePad.setSize(150,150);
		doodlePad.setPreferredSize(new Dimension(DOODLE_WIDTH_MULTIPLIER * NUMBER_OF_VALVES,NUMBER_OF_DOODLE_ROWS * DOODLE_WIDTH_MULTIPLIER));
		
		doodlePanel.add(doodlePad, BorderLayout.CENTER);
		JButton clearButton = new JButton("Clear");
		clearButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				doodlePad.clearEverything();
			}
		});
		doodlePanel.add(clearButton, BorderLayout.SOUTH);
		JButton doodleAddButton = new JButton("Add");
		doodleAddButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				drawableIcon = doodlePad.getDrawableIcon();
				imagePanel.addImage(drawableIcon);
			}
		});
		doodlePanel.add(doodleAddButton, BorderLayout.SOUTH);
		
		presetPanel = makePresetPanel(tabbedPanel.getWidth(),
				tabbedPanel.getHeight());

		tabbedPanel.addTab("Text", textEditingPanel);

		tabbedPanel.addTab("Doodle", doodlePanel);
		JScrollPane scrollerPresets = new JScrollPane(presetPanel,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		tabbedPanel.addTab("Presets", scrollerPresets);
		frame.add(tabbedPanel);

		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout());
		JPanel controlPanel = new JPanel();
		controlPanel.setBorder(new TitledBorder("Talk to the device"));
		controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
		controlPanel.setPreferredSize(new Dimension(400, 400));
		JButton sendButton = new JButton("Send");
		sendButton.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				Flood flood = new Flood();
				Invoker.getSingleton().execute(flood);

				boolean[][] bits = imagePanel.getImageAsBits();
				FullSetUp command = new FullSetUp(bits, (Integer) speed
						.getValue());
				Invoker.getSingleton().execute(command);
			}

		});
		controlPanel.add(sendButton);

		JButton floodButton = new JButton("Flood");
		floodButton.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				Flood flood = new Flood();
				Invoker.getSingleton().execute(flood);
			}
		});
		controlPanel.add(floodButton);
		
		JButton stopButton = new JButton("Stop");
		stopButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				Stop stop = new Stop();
				Invoker.getSingleton().execute(stop);
			}
		});
		controlPanel.add(stopButton);

		JPanel speedPanel = new JPanel();
		speedPanel.add(speed);
		JButton speedSetButton = new JButton("Set Timeout");
		speedSetButton.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				SetSpeed command = new SetSpeed((Integer) speed.getValue());
				Invoker.getSingleton().execute(command);
			}

		});
		speedPanel.add(speedSetButton);
		speedPanel.setBorder(new TitledBorder("Speed"));
		JButton clear = new JButton("Clear Image");
		clear.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				imagePanel.clear();
			}

		});

		controlPanel.add(speedPanel);
		controlPanel.add(clear);
		centerPanel.add(controlPanel);
		frame.add(centerPanel);

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
		Integer min = new Integer(10);
		Integer max = new Integer(250);
		Integer step = new Integer(10);
		SpinnerNumberModel model = new SpinnerNumberModel(value, min, max, step);
		speed = new JSpinner(model);
	}

	private JPanel makePresetPanel(int width, int height)
			throws FileFormatException, IOException
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
				String presetFileTitle = PRESETS_DIRECTORY + "/"
						+ (String) presetFilesBox.getSelectedItem();
				rebuildPresetButtonPanel(presetFileTitle);
			}
		});
		constraints.gridx = 0;
		constraints.gridy = 0;
		presetPanel.add(presetFilesBox, constraints);

		presetIconsPanel = new JPanel();
		presetIconsPanel.setLayout(new GridLayout(0, 4));

		// scroller.setPreferredSize(new Dimension(width,height));
		// scroller.setMaximumSize(new Dimension(width,height));
		// scroller.setMinimumSize(new Dimension(width,height));
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
				textField.setFont(new Font(fontName, Font.PLAIN,
						NUMBER_OF_VALVES));
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
			JOptionPane.showMessageDialog(
					presetPanel,
					"Optimal speed for that preset is "
							+ icon.getOptimalSpeed());
			imagePanel.addImage(icon);
		}
	}
}
