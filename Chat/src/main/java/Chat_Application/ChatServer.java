package Chat_Application;
import java.util.*;
import java.io.*;
import java.net.*;
import java.lang.Thread;
class Node implements Serializable
{                               
	private int port;
	private String message;
	public Node(int port,String message)
	{
		this.port=port;
		this.message=message;
	}
	public int getPort()
	{
                              return port;
	}
	public String getMessage()
	{
		return message;
	}
}
class ReceiveAndSend implements Runnable
{		
	private Socket skt;
               private Map<Integer,Socket>mp;
	public ReceiveAndSend(Socket skt,Map<Integer,Socket>mp)
	{		
		this.skt=skt;
                               this.mp=mp;
	}
	public void run()
	{
		String message;
		int port;
                              Socket skt1;
		try
		{     
			while(true)
			{                                                         
				Node myObj;
				DataInputStream in=new DataInputStream(skt.getInputStream());
				ObjectInputStream objin=new ObjectInputStream(in);
				myObj=(Node)objin.readObject();
				port=myObj.getPort();
				message=myObj.getMessage();				                                                                                                                                                                                 
                                                             skt1=mp.get(port);                                                                  
                                                             DataOutputStream out=new DataOutputStream(skt1.getOutputStream());                                                                                                                                                                                                    
                                                             out.writeBytes(message+'\n');
                                                             out.flush();                                                                                                                                                                                                                                                                                                                                         
			}
		}
		catch(Exception e)
		{
		}		
	}
}
public class ChatServer
{	        		
	public static void main(String[] args)
	{	
		try
		{			
			Map<Integer,Socket>mp=new HashMap<Integer,Socket>();
			ServerSocket ss=new ServerSocket(1234);		                                               
			while(true)
			{                                                            
				Socket skt=ss.accept();
				synchronized(mp)
				{
                                                                    mp.put(skt.getPort(),skt);
				}
				ReceiveAndSend receive=new ReceiveAndSend(skt,mp);				
                                                             Thread t2=new Thread(receive);
				t2.start();
			}                                                                                     
		}
		catch(Exception e)
		{
			System.out.println("Exception is="+e);
		}	
	}                             
}	                       	