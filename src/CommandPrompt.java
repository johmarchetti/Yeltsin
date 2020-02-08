import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class CommandPrompt {

	ProcessBuilder builder;

	public CommandPrompt() {
		builder = new ProcessBuilder("cmd.exe", "/c",
				"cd \"C:\\Program Files\\vlc-3.0.8-win32\\vlc-3.0.8\" && vlc -I qt --extraintf rc --rc-host localhost:50000");
		builder.redirectErrorStream(true);
	}
	
	public void RunSocketVLC() throws IOException
	{
		builder.start();
}

}