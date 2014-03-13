import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;


public class PresetFileReaderTests
{

	@Test
	public void justTwo() throws FileFormatException, IOException
	{
		PresetFileReader reader = new PresetFileReader();
		ArrayList<DrawableIcon> icons = reader.readFile("presetDiagonals.txt");
		assertEquals(2, icons.size());
	}

}
