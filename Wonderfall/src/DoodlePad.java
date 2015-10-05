import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

import javax.swing.JComponent;

class DoodlePad extends JComponent
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Image image;
	Graphics2D graphics2D;
	int currentX, currentY, oldX, oldY;
	ArrayList<StringBuffer> encoding;
	

	public DoodlePad()
	{
		encoding = new ArrayList<StringBuffer>(UI.NUMBER_OF_DOODLE_ROWS);
		for (int i=0;i<UI.NUMBER_OF_DOODLE_ROWS;i++)
		{
			encoding.add(new StringBuffer("11111111111111111111111111111111"));
		}
		setDoubleBuffered(false);
		addMouseListener(new MouseAdapter()
		{
			public void mousePressed(MouseEvent e)
			{
				oldX = convertToScreenPosition(e.getX());
				oldY = convertToScreenPosition(e.getY());
				System.out.println(" press:  ( " + oldX + ", " + oldY + ")");

			}
		});
		addMouseMotionListener(new MouseMotionAdapter()
		{
			public void mouseDragged(MouseEvent e)
			{
				currentX = convertToScreenPosition(e.getX());
				currentY = convertToScreenPosition(e.getY());
				System.out.println(" drag:  ( " + currentX + ", " + currentY + ")");
				if (graphics2D != null)
				{
					int startX = Math.min(currentX, oldX);
					int endX = Math.max(currentX, oldX);
					int startY = Math.min(currentY, oldY);
					int endY = Math.max(currentY, oldY);
					// graphics2D.drawLine(oldX, oldY, currentX, currentY);

					int changeInX = startX - endX;
					int changeInY = startY - endY;

					if (Math.abs(changeInX) > Math.abs(startY - endY))
					{
						for (int i = Math.min(currentX, oldX); i != Math.max(currentX, oldX); i++)
						{
							if (startY == endY)
							{
								fillSquare(i, startY);
							} else
							{
								double percentThruX = Math.abs((double) (i - currentX) / (currentX - oldX));
								if (currentY < oldY)
								{
									percentThruX = -1 * percentThruX;
								}
								System.out.println(percentThruX);
								fillSquare(i, currentY + (int) (percentThruX * changeInY));
							}
						}
					} else
					{
						for (int i = Math.min(currentY, oldY); i != Math.max(currentY, oldY); i++)
						{
							if (startX == endX)
							{
								fillSquare(startX, i);
							} else
							{
								double percentThruY = Math.abs((double) (i - currentY) / (currentY - oldY));
								if (currentX < oldX)
								{
									percentThruY = -1 * percentThruY;
								}
								System.out.println(percentThruY);
								fillSquare(currentX + (int) (percentThruY * changeInX), i);
							}
						}
					}

				}
				repaint();
				oldX = currentX;
				oldY = currentY;
			}

			/**
			 * x and y are in our screen units - draw a square on the screen
			 * 
			 * @param x
			 * @param y
			 */
			private void fillSquare(int x, int y)
			{
				graphics2D.fillRect(x * UI.DOODLE_WIDTH_MULTIPLIER, y * UI.DOODLE_WIDTH_MULTIPLIER,
						UI.DOODLE_WIDTH_MULTIPLIER, UI.DOODLE_WIDTH_MULTIPLIER);
				StringBuffer row = encoding.get(y);
				row.setCharAt(x, '0');
			}
		});
	}

	int convertToScreenPosition(int x)
	{
		return x / UI.DOODLE_WIDTH_MULTIPLIER;
	}

	public void paintComponent(Graphics g)
	{
		if (image == null)
		{
			image = createImage(getSize().width, getSize().height);
			graphics2D = (Graphics2D) image.getGraphics();
			graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			clear();
		}
		g.drawImage(image, 0, 0, null);
	}

	public void clear()
	{
		graphics2D.setPaint(Color.white);
		graphics2D.fillRect(0, 0, getSize().width, getSize().height);
		graphics2D.setPaint(Color.black);
		repaint();
	}

	public DrawableIcon getDrawableIcon()
	{
		
		ArrayList<String> strings = new ArrayList<String>();
		for (int i=0;i<encoding.size();i++)
		{
			strings.add(encoding.get(i).toString());
		}
		return new DrawableIcon(strings,90);
	}
}