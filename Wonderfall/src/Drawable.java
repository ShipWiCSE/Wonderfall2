import java.awt.Graphics;

/**
 * Required of things that will be displayed in the image panel
 * @author Merlin
 *
 */
public interface Drawable
{
	/**
	 * Get the number of rows wide this item is (before it is rotated into the stream)
	 * @param g the graphics object it will be displayed on
	 * @return number of pixels
	 */
	public int getWidth(Graphics g);

	/**
	 * Draw yourself on a given graphics object with a y value of whereWeAre
	 * @param g the graphics object
	 * @param whereWeAre the bottom y value this object should be drawn at
	 */
	public void drawYourself(Graphics g, int whereWeAre);

}
