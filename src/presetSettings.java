import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;

public class presetSettings {
	
	//Resource Declaration
	Reader reader;
	BufferedReader read;
	FileInputStream input;
	Writer writer;
	BufferedWriter write;
	FileOutputStream output;
	
	File settingsFile = new File("settings.ini");
	public int presetDelay, presetPos;
	public String presetFrom, presetTo; 
	String s = "";
	//End of Resource Declaration

	//Get preset settings from file "settings.ini"
	public void getSettings() throws IOException
	{
		input = new FileInputStream(settingsFile);
		reader = new InputStreamReader(input);
		read = new BufferedReader(reader);
		s = read.readLine();
		while (s != null)
		{
			if (s.equals("[FROM]")) presetFrom = read.readLine();
			if (s.equals("[TO]")) presetTo = read.readLine();
			if (s.equals("[DELAY]")) { s = read.readLine(); presetDelay = (int)(Double.parseDouble(s)*1000); }
			if (s.equals("[LAST-POSITION]")) { s = read.readLine(); presetPos = Integer.parseInt(s); }
			s = read.readLine();
		}
		read.close();
	}
	//End of getting preset from settings.ini file
	
	//Save settings (from, to, delay, current position) to file. Replace existing file.
	public void saveSettings(int from, int to, int delay, int pos) throws IOException
	{
		output = new FileOutputStream(settingsFile, false);
		writer = new OutputStreamWriter(output, "UTF-8");
		write = new BufferedWriter(writer);
		String temp = "";
		
		temp = "[FROM]"; write.write(temp); write.newLine(); write.write(Integer.toString(from+1)); write.newLine(); write.newLine();
		temp = "[TO]"; write.write(temp); write.newLine(); write.write(Integer.toString(to+1)); write.newLine(); write.newLine();
		temp = "[DELAY]"; write.write(temp); write.newLine(); write.write(Double.toString((double)delay/1000)); write.newLine(); write.newLine();
		temp = "[LAST-POSITION]"; write.write(temp); write.newLine(); write.write(Integer.toString(pos));
				
		write.close();
	}
	//End of saving settings to settings.ini file. 
	
	public boolean fileExists() { if (settingsFile.isFile()) return true;	else return false; } //Check if settings.ini exists.
}
