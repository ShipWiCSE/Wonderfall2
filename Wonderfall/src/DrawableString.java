import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;


/**
 * A String that is ready to be added to the stream
 * @author Merlin
 *
 */
public class DrawableString implements Drawable
{
	private Font font;
	private String string;
	
	/**
	 * Create one
	 * @param string the string that should be displayed
	 * @param font the font in which it should be displayed
	 */
	public DrawableString(String string, Font font)
	{
		this.font = font;
		this.string = string;
	}

	/**
	 * Get this object's font
	 * @return the font
	 */
	public Font getFont()
	{
		return font;
	}

	/**
	 * Get this object's string
	 * @return the string
	 */
	public String getString()
	{
		return string;
	}

	/**
	 * @see Drawable#getWidth(java.awt.Graphics)
	 */
	@Override
	public int getWidth(Graphics g)
	{
		FontMetrics metrics = g.getFontMetrics(font);
		return metrics.stringWidth(string);
	}

	/**
	 * @see Drawable#drawYourself(java.awt.Graphics, int)
	 */
	@Override
	public void drawYourself(Graphics g, int whereWeAre)
	{
		g.setFont(font);
		g.drawString(string, whereWeAre, font.getSize()-UI.FONT_OVERSIZE);
	}

}
