import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.ArrayList;

/**
 * A drawable image
 * 
 * @author Merlin
 * 
 */
public class DrawableIcon implements Drawable
{
	private static final int[] BLACK =
	{ 0, 0, 0 };
	private static final int[] WHITE =
	{ 255, 255, 255 };
	BufferedImage image;
	BufferedImage imageRotated;
	private int speed;

	/**
	 * Build the image from a list of strings of 1s and 0s. 0 will be black
	 * (where water should be) and 1 will be white (where water shouldn't be)
	 * 
	 * @param strings the data we should make the image from
	 * @param speed the speed at which it should be displayed
	 */
	public DrawableIcon(ArrayList<String> strings, int speed) 
	{
		this.speed = speed;
		image = new BufferedImage(32, strings.size(),
				BufferedImage.TYPE_INT_RGB);
		imageRotated = new BufferedImage(strings.size(), 32,
				BufferedImage.TYPE_INT_RGB);

		WritableRaster raster = image.getRaster();
		WritableRaster rasterRotated = imageRotated.getRaster();
		for (int row = 0; row < strings.size(); row++)
		{
			String thisRowsData = strings.get(row);
			for (int col = 0; col < 32; col++)
			{
				if (thisRowsData.charAt(col) == '1')
				{
					raster.setPixel(col, row, WHITE);
					rasterRotated.setPixel(imageRotated.getWidth() - 1 - row,
							col, BLACK);
				} else
				{
					raster.setPixel(col, row, BLACK);
					rasterRotated.setPixel(imageRotated.getWidth() - 1 - row,
							col, WHITE);
				}
			}
		}

	}


	/**
	 * @see Drawable#getWidth(java.awt.Graphics)
	 */
	@Override
	public int getWidth(Graphics g)
	{
		return image.getHeight();
	}

	/**
	 * @see Drawable#drawYourself(java.awt.Graphics, int)
	 */
	@Override
	public void drawYourself(Graphics g, int whereWeAre)
	{
		g.drawImage(imageRotated, whereWeAre, 0, null);
	}

	/**
	 * Get the image that this DrawableImage will add to the stream
	 * @return the image
	 */
	public Image getImage()
	{
		return image;
	}


	public int getOptimalSpeed()
	{
		return speed;
	}

}
