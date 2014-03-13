import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;


public class DrawableString implements Drawable
{
	private Font font;
	private String string;
	
	public DrawableString(String string, Font font)
	{
		this.font = font;
		this.string = string;
	}

	public Font getFont()
	{
		return font;
	}

	public String getString()
	{
		return string;
	}

	@Override
	public int getWidth(Graphics g)
	{
		FontMetrics metrics = g.getFontMetrics(font);
		return metrics.stringWidth(string);
	}

	@Override
	public void drawYourself(Graphics g, int whereWeAre)
	{
		g.setFont(font);
		g.drawString(string, whereWeAre, 32);
	}

}
