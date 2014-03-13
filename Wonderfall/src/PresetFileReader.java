import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Reads files that contain preset image maps
 * @author Merlin
 *
 */
public class PresetFileReader
{

	/**
	 * Read the file with a given title and return a list of drawable icons it represents
	 * @param fileTitle the name of the file
	 * @return a list of DrawableIcons
	 * @throws FileFormatException if the file contains rows that are not 32 bits wide
	 * @throws IOException if the file can't be found
	 */
	public ArrayList<DrawableIcon> readFile(String fileTitle)
			throws FileFormatException, IOException
	{
		ArrayList<DrawableIcon> icons = new ArrayList<DrawableIcon>();
		Scanner scanner = new Scanner(new File(fileTitle));
		ArrayList<String> strings = new ArrayList<String>();
		while (scanner.hasNext())
		{
			String line = scanner.nextLine();
			while (line.charAt(0) != '*')
			{
				if (line.length() != 32)
				{
					scanner.close();
					throw new FileFormatException("Every line must be 32 characters long");
				}
				strings.add(line);
				line = scanner.nextLine();
			}
			icons.add(new DrawableIcon(strings));
			strings = new ArrayList<String>();
		}
		scanner.close();
		return icons;
	}

}
