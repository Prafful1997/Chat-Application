package Chat_Application;
import java.util.*;
import java.io.*;
import java.net.*;	
class Send1 implements Runnable
{
	private Socket skt;
	private int port;
	public Send1(Socket skt,int port)
	{
		this.skt=skt;
		this.port=port;
	}
	public void run()
	{
		try
		{
			String clientSentence;
			while(true)
			{
				BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
				clientSentence=in.readLine();
				DataOutputStream out=new DataOutputStream(skt.getOutputStream());
				ObjectOutputStream objout=new ObjectOutputStream(out);
				Node myObj=new Node(port,clientSentence);
				objout.writeObject(myObj);
				objout.flush();
			}
		}
		catch(Exception e)
		{}
	}	
}
class Receive1 implements Runnable
{
	private Socket skt;
	public Receive1(Socket skt)
	{
		this.skt=skt;
	}
	public void run()
	{
		try
		{
			String message;
			while(true)
			{				
                                                            BufferedReader in=new BufferedReader(new InputStreamReader(skt.getInputStream()));				
                                                            message=in.readLine();
				System.out.println(message);
			}
		}
		catch(Exception e)
		{}
	}
}
public class ChatClient1
{
	public static void main(String[]args)
	{
		try
		{
			
	                                                                                              
			Socket skt=new Socket("localhost",1234);
			System.out.println("My port="+skt.getLocalPort());
                                              Thread t2=new Thread(new Receive1(skt));t2.start();
                                              
                                               System.out.print("Enter another client port to send=");
			BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
			int port=Integer.parseInt(in.readLine());
			
				
			Thread t1=new Thread(new Send1(skt,port));
			t1.start();
		}
		catch(Exception e)
		{}
	}
}
