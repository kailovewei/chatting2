package business;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;

import util.Constant;
import window.ServerWindow;

public class ServerThread implements Runnable{
	private Socket socket = null;
	private ObjectOutputStream out = null;
	private ObjectInputStream in = null;
//	private Socket client = null;
	private ServerWindow serverWindow = null;/////////1
	
	public ServerThread(Socket socket,ServerWindow serverWindow){///////////1
		this.socket = socket;
//		this.client = client;
		this.serverWindow = serverWindow;//////////1
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
//		ServerWindow.getInstance().display("已与客户端" + client.getInetAddress().getHostAddress() + "建立连接");
		
//		ServerWindow.getInstance().display("---------------------");/////////1
		serverWindow.display("已与客户端：" + socket.getRemoteSocketAddress() +  " "+socket.getInetAddress().getHostName() +" 建立连接！");//getInetAddress()返回socket的连接地址
		serverWindow.display("---------------------");
		try {
			out = new ObjectOutputStream(socket.getOutputStream());
//			ServerWindow.getInstance().setOut(out);/////////////?????这样的话导致服务器端只有一个输出流，就是只能想最后一个客户端输出
			serverWindow.setOut(out);///////////////1
			
			in = new ObjectInputStream(socket.getInputStream());
			String message = null;
			while(true){
				try {
					message = (String)in.readObject();
					if(!message.equals(Constant.CONNECT_QUIT)){
//						ServerWindow.getInstance().setOut(null);//////////////////1
						serverWindow.display(message);
					}else{
						serverWindow.setOut(null);
						break;
					}
//					ServerWindow.getInstance().display(message);////////////1
					
//					serverWindow.display(message);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
//			ServerWindow.getInstance().display("客户端" + ClientImpl.getClient().getInetAddress().getHostAddress() + "中断连接");
			serverWindow.display("客户端:" + socket.getInetAddress().getHostName() + "中断连接");
			
			out.close();
			in.close();
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
