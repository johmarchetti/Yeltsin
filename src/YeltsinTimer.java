import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.Toolkit;
import java.io.IOException;

public class YeltsinTimer {
	private Toolkit toolkit;
	private Timer timer;
	private TCPLocalSocket LocalSocket;
	private RemindTask YeltsinTimerTask;

	private int Volume;
	private int NonMutedVolume;
	private int GoodCurrentTime;
	private boolean muted;
	private ArrayList<Integer> movieActionTimeStart;
	private ArrayList<Integer> movieActionTimeStop;
	private ArrayList<String> movieAction;
	private String ActionNeeded;
	private Integer SkipToTime;

	public YeltsinTimer() {

		try {
			LocalSocket = new TCPLocalSocket();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		toolkit = Toolkit.getDefaultToolkit();
		timer = new Timer();
		YeltsinTimerTask = new RemindTask();
	}

	public void StartTimer() {
		timer.schedule(YeltsinTimerTask, 0, // initial delay
				1 * 100); // subsequent rate 250 for 250 ms
	}

	public void SetMovieTimeStartList(ArrayList<Integer> TimeStart) {
		movieActionTimeStart = TimeStart;
	}

	public void SetMovieTimeStopList(ArrayList<Integer> TimeStop) {
		movieActionTimeStop = TimeStop;
	}

	public void SetMovieActionList(ArrayList<String> Action) {
		movieAction = Action;
	}

	class RemindTask extends TimerTask {
		int CurrentTime;

		public void run() {
			try {
				if( LocalSocket.getIsPlaying() == 1 )
				{
					/*
					 * Read current vlc time
					 */
					try {
						CurrentTime = LocalSocket.getCurrentTime();
						
						if( CurrentTime != -1 )
						{
							GoodCurrentTime = CurrentTime;
						}
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					// poll the volume
					try {
						Volume = LocalSocket.getVolume();
						
						if( !muted && ( Volume != -1 ) )
						{
							NonMutedVolume = Volume;
						}

					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					/*
					 * if we're in an important range, do the thing!
					 */
					if( movieActionTimeStart != null )
					{
						
						ActionNeeded = "NONE";
						
						for (int i = 0; i < movieActionTimeStart.size(); i++) {
							if (GoodCurrentTime >= movieActionTimeStart.get(i)
							&& GoodCurrentTime < movieActionTimeStop.get(i)) {	
								
								ActionNeeded = movieAction.get(i);
								SkipToTime = movieActionTimeStop.get(i);
							}
						}
							
							// Skip action
							if (ActionNeeded.substring(0, 4).equals("skip")) {
								try {
									LocalSocket.seekTime(SkipToTime);
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							// Mute action
							else if (ActionNeeded.substring(0, 4)
									.equals("mute") ) {
								try {
									LocalSocket.setVolume(0);
									muted = true;
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							else if( (ActionNeeded.substring(0, 4)
									.equals("NONE")) && muted )
							{
							try {
								LocalSocket.setVolume( NonMutedVolume );
								muted = false;
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							}
							
						}
					}
			}
			catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}

		}
	}
}
