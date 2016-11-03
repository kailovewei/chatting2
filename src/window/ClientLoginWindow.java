package window;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Vector;

import javax.swing.*;

import business.ClientImpl;
import util.Constant;

public class ClientLoginWindow extends JFrame{
	private JButton butOK = new JButton("确定");
	private JButton butNO = new JButton("取消");
	private JTextField text = new JTextField();
	private JPasswordField pass = new JPasswordField();
	private JLabel l1 = new JLabel("用户名");
	private JLabel l2 = new JLabel("密码");
	private JPanel pan = new JPanel();
	private String userID = null;
	private String password = null;
	
	private static Socket client = null;
	//private Vector<Vector> info = null;//只是同一个进程中的不同对象可以共用static变量。不同进程的话，static变量还是会new的。
	
	public ClientLoginWindow(){
		setTitle("登录");
		pan.setLayout(new GridLayout(3, 2));
		pan.add(l1);
		pan.add(text);
		pan.add(l2);
		pan.add(pass);
		pan.add(butOK);
		pan.add(butNO);
		setLayout(new GridLayout(1, 1));
		add(pan);
		setSize(400,300);
		setVisible(true);
		addWindowListener( new WindowAdapter() {
			public void windowClosing(WindowEvent e){
				System.exit(1);
			}
		});
		butNO.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				System.exit(1);
			}
		});
		butOK.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				butOKAction();
			}
		});
	}
	
	private void butOKAction(){
		this.userID = text.getText();
		char tmp[] = pass.getPassword();
		this.password = new String(tmp);
		//hello
		try {
			client = new Socket(Constant.SERVER_HOST, Constant.LISTEN_PORT);
			ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
			ObjectInputStream in = new ObjectInputStream(client.getInputStream());
			out.writeObject(userID);
			out.writeObject(password);/////在服务器端完成验证。
			String message = (String)in.readObject();
			if(message.equals("yes")){
				this.dispose();
//				addInfo(client.getLocalAddress().getHostName(),client.getLocalAddress().getHostAddress());
//				System.out.println(info.size());
				
				new ClientImpl(client);
			}else{
				JOptionPane.showMessageDialog(this, "用户名或密码不正确");
				return;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}
	}
	
	public static void main(String args[]){
		new ClientLoginWindow();
	}
	
	public String getUserID(){
		return this.userID;
	}
	public String getPassword(){
		return this.password;
	}

	public static Socket getClient() {
		return client;
	}
	
//	private static void addInfo(String name,String IP){
//		if(info==null){
////			System.out.println("new");
//			info = new Vector<Vector>();
//		}
//		Vector<String> v = new Vector<String>();
//		v.add(name);
//		v.add(IP);
//		info.add(v);
//	}
}
