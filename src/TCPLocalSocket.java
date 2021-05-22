import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;


public class TCPLocalSocket {
	private String sentence;
	
	private Socket clientSocket;
	private DataOutputStream outToServer;
	private BufferedReader inFromServer;
	
	public Socket getClientSocket() {
		return clientSocket;
	}
	/*
	 * Constructor instantiates TCP socket objects
	 */
	public TCPLocalSocket() {
		
			try {
				clientSocket = new Socket("localhost", 50000);
				outToServer = new DataOutputStream(clientSocket.getOutputStream());
				inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				clientSocket = null;
			}
			
	}
	
	private String sendSocketCommand( String command, int returnedLines )
	{
		sentence = "";
		int readLines = 0;
		
		try {
			clientSocket.getInputStream().skip( clientSocket.getInputStream().available() );
			
			outToServer.writeBytes(command + "\r\n");
			
			while( readLines < returnedLines )
			{
				while( inFromServer.ready() != true );
				sentence += inFromServer.readLine();
				readLines++;
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			clientSocket = null;
		}
		
		return sentence;
	}
	
	public void SocketReconnect ()
	{
		System.out.println("Try");
		
		clientSocket = null;
		outToServer = null;
		inFromServer = null;
		
		try {
			clientSocket = new Socket("localhost", 50000);
			outToServer = new DataOutputStream(clientSocket.getOutputStream());
			inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			clientSocket = null;
		}

		
	}
	/*
	 * Returns current stream time of VLC instance
	 */
	public int getCurrentTime() {
		int CurrentTime = 0;
		int whereToSplit;
		sentence = "";
		
		sentence = sendSocketCommand("get_time", 1);
		
		System.out.println( "getCurrentTime: " + sentence );
	
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
	public void seekTime(int Time) {

		sendSocketCommand("seek " + Time, 0);
	
	}
	/*
	 * Sets volume
	 */
	public void setVolume(int Volume) {
		
		sendSocketCommand("volume " + Volume, 0);
		
	}
	/*
	 * Function gets current volume of VLC player
	 */
	public int getVolume()
	{
		int whereToSplit;
		int CurrentVolume = 0;
		sentence = "";

		sentence = sendSocketCommand("volume", 2);
		
		System.out.println("getVolume: " + sentence );
		
		sentence = sentence.replaceAll( Character.toString((char)13), " ");
		sentence = sentence.replaceAll( Character.toString((char)10), " ");
		sentence = sentence.trim();
		
		whereToSplit = sentence.lastIndexOf("audio volume:");


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
	public int getIsPlaying() {
		int CurrentTime = 0;
		int whereToSplit;
		sentence = "";
		
		sentence = sendSocketCommand("is_playing", 1);
		
		System.out.println("getIsPlaying: " + sentence);
	
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
