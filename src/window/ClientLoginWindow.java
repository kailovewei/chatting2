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

import javax.swing.*;

import business.ClientImpl;
import util.Constant;

public class ClientLoginWindow extends JFrame{
	private JButton butOK = new JButton("ȷ��");
	private JButton butNO = new JButton("ȡ��");
	private JTextField text = new JTextField();
	private JPasswordField pass = new JPasswordField();
	private JLabel l1 = new JLabel("�û���");
	private JLabel l2 = new JLabel("����");
	private JPanel pan = new JPanel();
	private String userID = null;
	private String password = null;
	
	private static Socket client = null;
	
	public ClientLoginWindow(){
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
		
		try {
			client = new Socket(Constant.SERVER_HOST, Constant.LISTEN_PORT);
			ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
			ObjectInputStream in = new ObjectInputStream(client.getInputStream());
			out.writeObject(userID);
			out.writeObject(password);/////�ڷ������������֤��
			String message = (String)in.readObject();
			if(message.equals("yes")){
				this.dispose();
				new ClientImpl();
			}else{
				JOptionPane.showMessageDialog(this, "�û��������벻��ȷ");
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
	
}