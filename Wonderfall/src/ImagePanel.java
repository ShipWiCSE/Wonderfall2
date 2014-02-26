import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

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
		Graphics2D g2 = (Graphics2D)g;
		AffineTransform orig = g2.getTransform();
		int panelHeight  = this.getHeight();
		int panelWidth = this.getWidth();
		g2.rotate(-Math.PI/2,panelWidth/2,panelHeight/2);
		g2.setFont(font);
		g2.drawString(stringToPrint, 100, 200);
		g2.setTransform(orig);
		
	}

}
