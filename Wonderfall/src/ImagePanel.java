import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

class ImagePanel extends JPanel
{

	private String stringToPrint;
	public void setStringToPrint(String stringToPrint)
	{
		this.stringToPrint = stringToPrint;
		repaint();
	}

	public void setFont(Font font)
	{
		this.font = font;
		repaint();
	}

	private Font font;

	public ImagePanel()
	{
		stringToPrint = "";
		this.setPreferredSize(new Dimension(200,500));
	}

	// need to do this also to a buffered image to get the raster bit map
	public void paintComponent(Graphics g)
	{

		super.paintComponent(g);
		FontMetrics metrics = g.getFontMetrics(font);
		int stringWidth = metrics.stringWidth(stringToPrint);
		this.setPreferredSize(new Dimension(32,stringWidth));
		this.setMaximumSize(new Dimension(32,stringWidth));
		this.setMinimumSize(new Dimension(32,stringWidth));
		Graphics2D g2 = (Graphics2D)g;
		AffineTransform orig = g2.getTransform();
		g2.rotate(-Math.PI/2,stringWidth/2,stringWidth/2);
		g2.translate(0,this.getParent().getWidth()/2);
		g2.setFont(font);
		g2.drawString(stringToPrint, 0, 32);


		g2.setTransform(orig);
		revalidate();
//		this.getParent().getParent().repaint();
	}

}
