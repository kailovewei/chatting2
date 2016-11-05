package business;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.Vector;

import db.BaseDao;
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
		serverWindow.display("已与客户端：" + serverWindow.getLocalUserID() + " " + socket.getRemoteSocketAddress() +  " "+socket.getInetAddress().getHostName() +" 建立连接！");//getInetAddress()返回socket的连接地址
		serverWindow.display("---------------------");
		try {
			out = new ObjectOutputStream(socket.getOutputStream());
//			ServerWindow.getInstance().setOut(out);/////////////?????这样的话导致服务器端只有一个输出流，就是只能想最后一个客户端输出
			serverWindow.setOut(out);///////////////1
			serverWindow.setSocket(socket);
			///
			in = new ObjectInputStream(socket.getInputStream());
			String message = null;
			while(true){
				try {
					message = (String)in.readObject();
					if(message.equals(Constant.CONNECT_QUIT)){
//						ServerWindow.getInstance().setOut(null);//////////////////1
						String userID = (String)in.readObject();
						Vector<String> v = new Vector<String>();
						v.add(userID);
						v.add(socket.getInetAddress().getHostName());
						v.add(socket.getInetAddress().getHostAddress());
						if(ServerImpl.getInfo().contains(v)){
							ServerImpl.getInfo().remove(v); //用户下线后，从用户列表中删除
						}
						serverWindow.setOut(null);
						String sql = "update user set isOnline = 0 where userID = '" + userID + "'";
						int i = BaseDao.executeUpdate(sql);
						break;
					}
					
					if(message.equals("请求当前在线用户")){
						out.writeObject(ServerImpl.getInfo());//读到“请求在线用户”这个命令后，直接发送info这个表格
						out.flush();
<<<<<<< HEAD
						out.reset();//因为每次发的Vector名字都叫info，所以需要reset一下，否则以后发的只是info这个名字，内容还是原来的
					}else if(message.equals("发送消息给其他用户")){
						String localUserID = (String)in.readObject();
						String otherClientID = (String)in.readObject();
						String content = (String)in.readObject();
						ServerThread st = ServerImpl.getServerMap().get(otherClientID);
						st.receive(localUserID, content);
					}else{
=======
						out.reset();
					}
					
//					ServerWindow.getInstance().display(message);////////////1
					else{
>>>>>>> origin/tryAndTest
						serverWindow.display(message);
					}
//					serverWindow.display(message);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
//			ServerWindow.getInstance().display("客户端" + ClientImpl.getClient().getInetAddress().getHostAddress() + "中断连接");
//			ServerWindow.getInstance().display("客户端" + client.getInetAddress().getHostAddress() + "中断连接");
			serverWindow.display("客户端:" + serverWindow.getLocalUserID() + " " +socket.getInetAddress().getHostName() + "中断连接");
			out.close();
			in.close();
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void receive(String localUserID,String content){
		try {
			this.out.writeObject(localUserID + ":--> " + content);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
