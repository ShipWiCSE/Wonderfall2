import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

/**
 * Tests for reading the files that contain presets
 * @author Merlin
 *
 */
public class PresetFileReaderTests
{

	/**
	 * Make sure we can read one file - just check how many we get
	 * @throws FileFormatException shouldn't
	 * @throws IOException shouldn't
	 */
	@Test
	public void justTwo() throws FileFormatException, IOException
	{
		PresetFileReader reader = new PresetFileReader();
		ArrayList<DrawableIcon> icons = reader.readFile("presetDiagonals.txt");
		assertEquals(3, icons.size());
	}

}
