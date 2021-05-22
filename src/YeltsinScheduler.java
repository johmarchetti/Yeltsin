import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.Toolkit;

public class YeltsinScheduler {
	private Timer timer;
	private TCPLocalSocket LocalSocket;
	private RemindTask YeltsinTimerTask;

	private int Volume;
	private int NonMutedVolume;
	private int GoodCurrentTime;
	private int CurrentTime;
	private int IsPlaying;
	private boolean muted;
	private ArrayList<Integer> movieActionTimeStart;
	private ArrayList<Integer> movieActionTimeStop;
	private ArrayList<String> movieAction;
	private String ActionNeeded;
	private Integer SkipToTime;

	public YeltsinScheduler() {

		LocalSocket = new TCPLocalSocket();
		Toolkit.getDefaultToolkit();
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

	public int getCurrentTime() {
		if( LocalSocket.getClientSocket() != null )
		{
			if( IsPlaying == 1 )
			{
				return CurrentTime;
			}
			else
			{
				return -1;
			}
		}
		else
		{
			return -1;
		}
	}

	class RemindTask extends TimerTask {

		public void run() {
			if( LocalSocket.getClientSocket() != null )
			{
				if( (IsPlaying = LocalSocket.getIsPlaying()) == 1 )
				{
					CurrentTime = LocalSocket.getCurrentTime();
					
					if( CurrentTime != -1 )
					{
						GoodCurrentTime = CurrentTime;
					}
					Volume = LocalSocket.getVolume();
					
					if( !muted && ( Volume != -1 ) )
					{
						NonMutedVolume = Volume;
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
							LocalSocket.seekTime(SkipToTime);
						}
						// Mute action
						else if (ActionNeeded.substring(0, 4)
								.equals("mute") ) {
							LocalSocket.setVolume(0);
							muted = true;
						}
						else if( (ActionNeeded.substring(0, 4)
								.equals("NONE")) && muted )
						{
							LocalSocket.setVolume( NonMutedVolume );
							muted = false;
						}
					}
				}
			}
			else
			{
				System.out.println( LocalSocket.getClientSocket() );
	
				LocalSocket.SocketReconnect();
			}

			
			
		}
	}
}
