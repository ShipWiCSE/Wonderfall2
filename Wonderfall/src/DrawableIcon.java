import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferInt;
import java.awt.image.Raster;
import java.awt.image.SinglePixelPackedSampleModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.Icon;

public class DrawableIcon implements Drawable
{
	private static final int[] BLACK =
	{ 0, 0, 0};
	private static final int[] WHITE =
	{ 255, 255, 255 };
	BufferedImage image;
	BufferedImage imageRotated;

	public DrawableIcon(ArrayList<String> strings) throws IOException
	{
		image = new BufferedImage( 32, strings.size(),BufferedImage.TYPE_INT_RGB);
		imageRotated = new BufferedImage( strings.size(), 32, BufferedImage.TYPE_INT_RGB);
		 
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
					rasterRotated.setPixel( imageRotated.getWidth() - 1 -row, col, WHITE);
				} else
				{
					raster.setPixel(col, row, BLACK);
					rasterRotated.setPixel( imageRotated.getWidth() - 1 -row,col, BLACK);
				}
			}
		}
		ImageIO.write(image, "jpeg", new File("silly" + Math.random() + "jpg"));

	}

	@Override
	public int getWidth(Graphics g)
	{
		return image.getHeight();
	}

	@Override
	public void drawYourself(Graphics g, int whereWeAre)
	{
		g.drawImage(imageRotated, whereWeAre, 0, null);
	}

	public Image getImage()
	{
		return image;
	}

}
