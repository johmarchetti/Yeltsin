import java.io.IOException;


public class CommandPrompt {

	ProcessBuilder builder;

	public CommandPrompt() {
		builder = new ProcessBuilder("cmd.exe", "/c",
				"cd \"C:\\Program Files (x86)\\VideoLAN\\VLC\" && vlc -I qt --extraintf rc --rc-host=localhost:50000 --rc-quiet --no-rc-show-pos");
		builder.redirectErrorStream(true);
	}

	public void RunSocketVLC() throws IOException
	{
		builder.start();
}

}