package window;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.Socket;
import java.util.Vector;

import javax.swing.*;

import business.ClientImpl;
import business.ClientThread;
import util.Constant;

public class ClientWindow extends JFrame {
	private JTextArea area = new JTextArea();
	private JScrollPane jsp = new JScrollPane(area, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
			JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	private JPanel pan = new JPanel();
	private JTextField text = new JTextField();
	private JButton butOK = new JButton("发送");
	private JButton butFind = new JButton("上线的人");
	private ObjectOutputStream out = null;
	private ObjectInputStream in = null;
	private Socket client = null;
	private Thread t = null;
	private ClientThread clientThread = null;
	private Vector<Vector> info = null;
	private OnlineClientWindow onlineClientWindow = null;
	private String localUserID = null;
	private String otherClientID = null;
	private String otherClientName = null;
	private String otherClientAddress = null;
	
	private OnlineClientWindow onlineClientWindow = null;
	
	public ClientWindow(Socket client) {
		this.client = client;
		setTitle("客户端");
		setLayout(new BorderLayout());
		area.setRows(11);
		area.setEditable(false);
		pan.setLayout(new FlowLayout());
		text.setPreferredSize(new Dimension(320, 25));
		pan.add(text);
		pan.add(butOK);
		pan.add(butFind);
		add(jsp, BorderLayout.NORTH);
		add(pan, BorderLayout.SOUTH);
		pack();
		setLocationRelativeTo(getOwner());
		// setVisible(true);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if (out != null) {
					try {
						out.writeObject(Constant.CONNECT_QUIT);
						out.writeObject(localUserID);
						out.flush();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					// ClientImpl.close();
					// System.exit(1);
				}
				if (t != null) {
					t.stop();
				}
				System.exit(1);
			}
		});
		butOK.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				butOKAction();
			}
		});
		butFind.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				butFindAction();
			}
		});
	}

	private void butOKAction() {
		if (out != null) {
			try {
<<<<<<< HEAD
				if(otherClientID == null && otherClientName == null && otherClientAddress == null){//向服务器发送
					out.writeObject(localUserID + " " +client.getLocalAddress().getHostName() + "-->" + text.getText());//getLocalAddress()是返回本地地址
					display(localUserID + " " +client.getLocalAddress().getHostName() + "-->" + text.getText());
					out.flush();
					text.setText("");
				}else{//发给其他用户，也由服务器中转，所以ClientThread接收不变（也许不要发quit）
					out.writeObject("发送消息给其他用户");
					out.writeObject(localUserID);
					out.writeObject(otherClientID);
					out.writeObject(text.getText());
					out.flush();
					display(localUserID + "-->" +text.getText());
					text.setText("");
				}
//				out.writeObject(client.getLocalAddress().getHostName() + "-->" + text.getText());//getLocalAddress()是返回本地地址
//				display(client.getLocalAddress().getHostName() + "-->" +text.getText());
//				out.flush();
//				text.setText("");
=======
				// out.writeObject(client.getInetAddress().getHostName() + "-->"
				// +text.getText());
				// display(client.getInetAddress().getHostName() + "-->"
				// +text.getText());
				out.writeObject(client.getLocalAddress().getHostName() + "-->" + text.getText());// getLocalAddress()是返回本地地址
				display(client.getLocalAddress().getHostName() + "-->" + text.getText());
				out.flush();
				text.setText("");
>>>>>>> origin/tryAndTest
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
<<<<<<< HEAD
	private void butFindAction(){
//		Vector<Vector> info = null;
		onlineClientWindow = new OnlineClientWindow(info,this);
		try {
			if(this.out!=null){
				this.out.writeObject("请求当前在线用户");
				out.flush();
			}
//			info = (Vector<Vector>) this.in.readObject();
//			info =  ClientThread.getInfo();/////////////////
=======

	private void butFindAction() {
		// Vector<Vector> info = null;
		onlineClientWindow = new OnlineClientWindow(info, this);
		try {
			this.out.writeObject("请求当前在线用户");
			out.flush();
			// info = (Vector<Vector>) this.in.readObject();
			// info = ClientThread.getInfo();/////////////////
>>>>>>> origin/tryAndTest
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
<<<<<<< HEAD
//		new OnlineClientWindow(info,this);//有问题，info最开始是null,所以第一次的table中什么也没有。解决方案，先让info显示出来，即使是null，后续再更新。
		this.setEnabled(false);
=======
//		this.setEnabled(false);
>>>>>>> origin/tryAndTest
	}

	public void setOut(ObjectOutputStream out) {
		this.out = out;
	}

	public void setIn(ObjectInputStream in) {
		this.in = in;
	}

	public void display(String mess) {
		area.append(mess + "\n");
		area.setCaretPosition(area.getText().length());
	}

	// public static void main(String args[]){
	// new ClientWindow(null);
	// }
	public void setT(Thread t) {
		this.t = t;
<<<<<<< HEAD
	}//
	public void setClientThread(ClientThread clientThread){
=======
	}

	public void setClientThread(ClientThread clientThread) {
>>>>>>> origin/tryAndTest
		this.clientThread = clientThread;
	}

	public Vector<Vector> getInfo() {
		return this.info;
	}
<<<<<<< HEAD
	public void setInfo(Vector<Vector> info){//收到info之后，让在线用户窗口去更新
		onlineClientWindow.updateInfo(info);
	}
	public void setOtherClientName(String otherClientName) {
		this.otherClientName = otherClientName;
	}
	public void setOtherClientAddress(String otherClientAddress) {
		this.otherClientAddress = otherClientAddress;
	}
	public void setOtherClientID(String otherClientID) {
		this.otherClientID = otherClientID;
	}
	public void setLocalUserID(String localUserID) {//
		this.localUserID = localUserID;
=======

	public void setInfo(Vector<Vector> info) {
		this.info = info;
		onlineClientWindow.updateInfo(info);
>>>>>>> origin/tryAndTest
	}
}
