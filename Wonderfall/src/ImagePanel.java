import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.util.ArrayList;

import javax.swing.JPanel;

class ImagePanel extends JPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Drawable> drawables;
	private int totalHeight;

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

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		totalHeight = getWidthOfAllDrawables(g);
		this.setPreferredSize(new Dimension(32, totalHeight));
		this.setMaximumSize(new Dimension(32, totalHeight));
		this.setMinimumSize(new Dimension(32, totalHeight));
		g.setColor(Color.WHITE);
		g.fillRect(this.getWidth()/2, 0, 32, 300);
		g.setColor(Color.BLACK);
		addEveryTo(g, this.getParent().getWidth());
		revalidate();
		// BufferedImage img = new BufferedImage(super.getWidth(),
		// super.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
		// Graphics
		// Raster raster = g2
		// this.getParent().getParent().repaint();
	}

	private void addEveryTo(Graphics g, int overallWidth)
	{
		g.setColor(Color.WHITE);
		g.fillRect(overallWidth/2, 0, 32, totalHeight);
		g.setColor(Color.BLACK);
		int whereWeAre = 0;
		Graphics2D g2 = (Graphics2D) g;
		AffineTransform orig = g2.getTransform();
		g2.rotate(-Math.PI / 2, totalHeight / 2, totalHeight / 2);
		g2.translate(0, this.getParent().getWidth() / 2);
		for (int i = 0; i < drawables.size(); i++)
		{
			int drawableWidth = drawables.get(i).getWidth(g);
			drawables.get(i).drawYourself(g, whereWeAre);
			whereWeAre = whereWeAre + drawableWidth;
		}

		g2.setTransform(orig);

	}

	private int getWidthOfAllDrawables(Graphics g)
	{
		int totalWidth = 0;
		for (int i = 0; i < drawables.size(); i++)
		{
			totalWidth = totalWidth + drawables.get(i).getWidth(g);
		}
		return totalWidth;
	}

	public boolean[][] getImageAsBits()
	{
		boolean[][] bits = new boolean[totalHeight][32];

		BufferedImage image = new BufferedImage(totalHeight, 36,
				BufferedImage.TYPE_USHORT_GRAY);
		
		int whereWeAre = 0;
		for (int i = 0; i < drawables.size(); i++)
		{
			int drawableWidth = drawables.get(i).getWidth(image.getGraphics());
			drawables.get(i).drawYourself(image.getGraphics(), whereWeAre);
			whereWeAre = whereWeAre + drawableWidth;
		}
//		File outputfile = new File("saved.png");
//	    try
//		{
//			ImageIO.write(image, "png", outputfile);
//		} catch (IOException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		Raster raster = image.getRaster();
		for (int col = 0; col < image.getWidth(); col++)
		{
			for (int row = 0; row < 32; row++)
			{
				int rgb = raster.getSample(col, row, 0);
				if (rgb != 0)
				{
					bits[col][31-row] = true;
					System.out.print("1");
				} else
				{
					System.out.print("0");
				}
			}
			System.out.println();
		}

		return bits;
	}

	public void clear() {
		drawables.clear();
		this.repaint();
	}

}
