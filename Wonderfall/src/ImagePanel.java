import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import javax.swing.JPanel;

class ImagePanel extends JPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Drawable> drawables;

	public ImagePanel()
	{
		drawables = new ArrayList<Drawable>();
		this.setPreferredSize(new Dimension(200, 500));
	}

	public void addText(String stringToPrint, Font font)
	{
		stringToPrint = stringToPrint + " ";
		drawables.add(new DrawableString(stringToPrint, font));
		this.repaint();
	}
	
	public void addImage(DrawableIcon img)
	{
		drawables.add(img);
		this.repaint();
	}

	// need to do this also to a buffered image to get the raster bit map
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		int totalWidth = getWidthOfAllDrawables(g);
		this.setPreferredSize(new Dimension(32, totalWidth));
		this.setMaximumSize(new Dimension(32, totalWidth));
		this.setMinimumSize(new Dimension(32, totalWidth));
		int whereWeAre = 0;
		Graphics2D g2 = (Graphics2D) g;
		AffineTransform orig = g2.getTransform();
		g2.rotate(-Math.PI / 2, totalWidth / 2, totalWidth / 2);
		g2.translate(0, this.getParent().getWidth() / 2);
		for(int i=0;i<drawables.size();i++)
		{	
			int drawableWidth = drawables.get(i).getWidth(g);
			drawables.get(i).drawYourself(g, whereWeAre);	
			whereWeAre = whereWeAre + drawableWidth;
		}
		g2.setTransform(orig);
		revalidate();
		// BufferedImage img = new BufferedImage(super.getWidth(),
		// super.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
		// Graphics
		// Raster raster = g2
		// this.getParent().getParent().repaint();
	}

	private int getWidthOfAllDrawables(Graphics g)
	{
		int totalWidth = 0;
		for(int i=0;i<drawables.size();i++)
		{
			totalWidth = totalWidth + drawables.get(i).getWidth(g);
		}
		return totalWidth;
	}

	public void getImageAsBits()
	{
		// TODO Auto-generated method stub
		
	}

}
