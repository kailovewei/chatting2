package business;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Vector;

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
//		String message = null;
		Object message = null;
		while(true){
			try {
//				message = (String)in.readObject();
				message = in.readObject();
				if(message instanceof String){
					if(!message.equals(Constant.CONNECT_QUIT)){//
						clientWindow.display((String)message);
					}else{
						clientWindow.setOut(null);
						break;
					}
				}else{
//					if(clientWindow.getInfo()==null){
//						clientWindow.getInfo()= new Vector<Vector>();//??????
//					}
//					clientWindow.getInfo() = (Vector<Vector>) message;
					clientWindow.setInfo((Vector<Vector>) message);
				}
//				if(!message.equals(Constant.CONNECT_QUIT)){
//					clientWindow.display(message);
//				}else{
//					clientWindow.setOut(null);
//					break;
//				}
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		clientWindow.display("服务器已断开");
	}
//	public static Vector<Vector> getInfo(){
//		return info;
//	}
//	public void setFlag(boolean flag){
//		this.flag = flag;
//	}
}