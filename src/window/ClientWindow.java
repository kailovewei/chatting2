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

import javax.swing.*;

import business.ClientImpl;
import business.ClientThread;
import util.Constant;

public class ClientWindow extends JFrame{
	private JTextArea area = new JTextArea();
	private JScrollPane jsp = new JScrollPane(area,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	private JPanel pan = new JPanel();
	private JTextField text = new JTextField();
	private JButton butOK = new JButton("·¢ËÍ");
	private ObjectOutputStream out = null;
	private Socket client = null;
	private Thread t = null;
	private ClientThread clientThread = null;
	
	public ClientWindow(Socket client){
		this.client = client;
		setTitle("¿Í»§¶Ë");
		setLayout(new BorderLayout());
		area.setRows(11);
		area.setEditable(false);
		pan.setLayout(new FlowLayout());
		text.setPreferredSize(new Dimension(320, 25));
		pan.add(text);
		pan.add(butOK);
		add(jsp,BorderLayout.NORTH);
		add(pan,BorderLayout.SOUTH);
		pack();
		setLocationRelativeTo(getOwner());
//		setVisible(true);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e){
				if(out!=null){
					try {
						out.writeObject(Constant.CONNECT_QUIT);
						out.flush();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
//					ClientImpl.close();
//					System.exit(1);
				}
				if(t!=null){
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
	}
	private void butOKAction(){
		if(out!=null){
			try {
				out.writeObject(client.getInetAddress().getHostName() + "-->" +text.getText());
				display(client.getInetAddress().getHostName() + "-->" +text.getText());
				out.flush();
				text.setText("");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void setOut(ObjectOutputStream out){
		this.out = out;
	}
	public void display(String mess){
		area.append(mess+ "\n");
		area.setCaretPosition(area.getText().length());
	}
//	public static void main(String args[]){
//		new ClientWindow(null);
//	}
	public void setT(Thread t){
		this.t = t;
	}
	public void setClientThread(ClientThread clientThread){
		this.clientThread = clientThread;
	}
}
