package business;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;

import util.Constant;
import window.ClientWindow;

public class ClientImpl {
	private ClientWindow clientWindow = null;
	private Socket client = null;
	private ObjectOutputStream out = null;
	private ObjectInputStream in = null;
	private SleepThread sleepThread = null;
	private Thread t = null;
	private ClientThread clientThread = null;
	
	public ClientImpl(){
		try {
			client = new Socket(Constant.SERVER_HOST, Constant.LISTEN_PORT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		clientWindow = new ClientWindow(client);
		clientWindow.setVisible(true);
		clientWindow.display("连接至服务器：" + Constant.SERVER_HOST);
		clientWindow.display("------------");
		sleepThread = new SleepThread(2);
		try {
			out = new ObjectOutputStream(client.getOutputStream());
			clientWindow.setOut(out);
			in = new ObjectInputStream(client.getInputStream());
//			String message = null;
//			while(sleepThread.sleep()){
//				try {
//					message = (String)in.readObject();
//					if(!message.equals(Constant.CONNECT_QUIT)){
//						clientWindow.display(message);
//					}else{
//						clientWindow.setOut(null);
//						break;
//					}
//				} catch (ClassNotFoundException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//			clientWindow.display("服务器已断开");
			
			t = new Thread(new ClientThread(in,clientWindow));
			clientWindow.setT(t);
			
			Constant.T.add(t);//////////1
			
			t.start();
//			out.close();
//			in.close();
//			client.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new ClientImpl();
	}
//	public static Socket getClient(){
//		return client;
//	}
	public void close(){
//		try {
//			sleepThread.notify();
//			in.close();
//			out.close();
////			client.close();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		t.interrupt();
	}
	private boolean sleepSomeTime(int time){
		try{
			Thread.sleep(time*1000);
		}catch(Exception e){
			
		}
		return true;
	}
	
	class SleepThread implements Runnable{
		private int timeSecond = 0;
		private boolean flag = false;
		public SleepThread(int timeSecond){ 
			this.timeSecond = timeSecond;
		}

		@Override
		public void run() {
			try {
				flag = false;
				Thread.sleep(timeSecond*1000);
				flag = true;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				flag = false;
			}
			
		}
		
		public boolean sleep(){
			run();
			return flag;
		}
		
	}
	
}
