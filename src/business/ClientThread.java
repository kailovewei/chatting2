package business;

import java.io.IOException;
import java.io.ObjectInputStream;

import util.Constant;
import window.ClientWindow;

public class ClientThread implements Runnable{
	private ObjectInputStream in = null;
	private ClientWindow clientWindow = null;
//	private boolean flag = true;
	public ClientThread(ObjectInputStream in,ClientWindow clientWindow) {
		this.in = in;
		this.clientWindow = clientWindow;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		String message = null;
		while(true){
			try {
				message = (String)in.readObject();
				if(!message.equals(Constant.CONNECT_QUIT)){
					clientWindow.display(message);
				}else{
					clientWindow.setOut(null);
					break;
				}
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		clientWindow.display("服务器已断开");
	}
//	public void setFlag(boolean flag){
//		this.flag = flag;
//	}
}