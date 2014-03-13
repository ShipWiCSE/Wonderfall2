import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class PresetFileReader
{

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
