import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;


public class TCPLocalSocket {
	private String sentence;
	
	private BufferedReader inFromUser;
	private Socket clientSocket;
	private DataOutputStream outToServer;
	private BufferedReader inFromServer;
	/*
	 * Constructor instantiates TCP socket objects
	 */
	public TCPLocalSocket() throws IOException, IOException{
		inFromUser = new BufferedReader(new InputStreamReader(System.in));
		clientSocket = new Socket("localhost", 50000);
		outToServer = new DataOutputStream(clientSocket.getOutputStream());
		inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	}
	/*
	 * Returns current stream time of VLC instance
	 */
	public int getCurrentTime() throws IOException{
		int CurrentTime = 0;
		int whereToSplit;
		int lastRead;
		sentence = "";
		
		outToServer.writeBytes("get_time" + '\n');
		
		while( !inFromServer.ready() );		// might need to wrap this with a timeout
		
		// new experiment
		do
		{
		lastRead = inFromServer.read();
		sentence += (char)lastRead;	
		}
		while( inFromServer.ready() );
		
	
		sentence = sentence.replaceAll( Character.toString((char)13), " ");
		sentence = sentence.replaceAll( Character.toString((char)10), " ");
		sentence = sentence.trim();

//		System.out.println("getCurrentTime: " + sentence);
		
		whereToSplit = sentence.lastIndexOf(" ");
		
		if( whereToSplit == -1)
		{
			whereToSplit = 0;
		}
		else
		{
			whereToSplit++;
		}

		sentence = sentence.substring(whereToSplit);
		sentence = sentence.trim();
		try
		{
			CurrentTime = Integer.parseInt(sentence);
		}
		catch( NumberFormatException e )
		{
			System.out.println("Error:getCurrentTime Exception");
		}
		
		if ( CurrentTime == 1 )
		{
			System.out.println("Error:getCurrentTime 1");
			return -1;
		}
		else
		{
			System.out.println(CurrentTime);
			return CurrentTime;
		}
	}
	/*
	 * Sets VLC player to time specified by argument
	 */
	public void seekTime(int Time) throws IOException{

		outToServer.writeBytes("seek " + Integer.toString(Time) + '\n');
	
	}
	/*
	 * Sets volume
	 */
	public void setVolume(int Volume) throws IOException{
		
		outToServer.writeBytes("volume " + Integer.toString(Volume) + '\n');
		
	}
	/*
	 * Function mutes or unmutes VLC
	 */
	public int getVolume() throws IOException
	{
		int lastRead;
		int whereToSplit;
		int CurrentVolume = 0;
		sentence = "";

		// read current volume from VLC
		outToServer.writeBytes("volume" + '\n');
		
		while( !inFromServer.ready() );		// might need to wrap this with a timeout
		
		// new experiment
		do
		{
		lastRead = inFromServer.read();
		sentence += (char)lastRead;	
		}
		while( inFromServer.ready() );
		
		sentence = sentence.replaceAll( Character.toString((char)13), " ");
		sentence = sentence.replaceAll( Character.toString((char)10), " ");
		sentence = sentence.trim();
		
		whereToSplit = sentence.lastIndexOf("audio volume:");

//		System.out.println("getVolume: " + sentence );

		if( whereToSplit != -1 )
		{
			sentence = sentence.substring(whereToSplit + 14);

			whereToSplit = sentence.indexOf(")");
			sentence = sentence.substring(0, whereToSplit);
			sentence = sentence.trim();
			
			try
			{
				CurrentVolume = Integer.parseInt(sentence);
			}
			catch( NumberFormatException e )
			{
				
			}
			
			return CurrentVolume;
		}
		else
		{
			return -1;
		}

	}
	/*
	 * Returns current stream time of VLC instance
	 */
	public int getIsPlaying() throws IOException{
		int CurrentTime = 0;
		int whereToSplit;
		int lastRead;
		sentence = "";
		
		outToServer.writeBytes("is_playing" + '\n');
		
		while( !inFromServer.ready() );		// might need to wrap this with a timeout
		
		// new experiment
		do
		{
		lastRead = inFromServer.read();
		sentence += (char)lastRead;	
		}
		while( inFromServer.ready() );
		
//		System.out.println("getIsPlaying: " + sentence);
	
		sentence = sentence.replaceAll( Character.toString((char)13), " ");
		sentence = sentence.replaceAll( Character.toString((char)10), " ");
		sentence = sentence.trim();
		
		whereToSplit = sentence.lastIndexOf(" ");
		
		if( whereToSplit == -1)
		{
			whereToSplit = 0;
		}
		else
		{
			whereToSplit++;
		}

		sentence = sentence.substring(whereToSplit);
		sentence = sentence.trim();
		try
		{
			CurrentTime = Integer.parseInt(sentence);
		}
		catch( NumberFormatException e )
		{
			
		}
		
		
		return CurrentTime;
	}
}
