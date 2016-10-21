package window;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;

import javax.swing.*;

import business.ServerImpl;
import util.Constant;

public class ServerWindow extends JFrame{
	private JTextArea area = new JTextArea();
	private JScrollPane jsp = new JScrollPane(area,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	private JPanel pan = new JPanel();
	private JTextField text = new JTextField();
	private JButton butOK = new JButton("发送");
	private ObjectOutputStream out = null;
//	private static ServerWindow serverWindow = null;///////1
	private Thread t = null;
	
	public ServerWindow(){
		setTitle("服务器端");
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
				if(out!= null){
					try {
						out.writeObject(Constant.CONNECT_QUIT);
						out.flush();
//						ServerImpl.close();
//						System.exit(1);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
//				for(int i=0;i<Constant.T.size();i++){///////////////////1
//					Constant.T.get(i).stop();
//				}
				t.stop();
//				ServerImpl.close();
//				System.exit(1);
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
	
	public void setOut(ObjectOutputStream out){
		this.out = out;
	}
	private void butOKAction(){
		if(out!=null){
			try {
				out.writeObject("服务器端--> " + text.getText());
				display("服务器端--> " + text.getText());
				out.flush();
				text.setText("");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void display(String mess){
		area.append(mess + "\n");
		area.setCaretPosition(area.getText().length());
	}
//	public static ServerWindow getInstance(){///////////1
//		if(serverWindow == null){
//			serverWindow = new ServerWindow();
//		}
//		return serverWindow;
//	}
//	public static void main(String args[]){
//		ServerWindow.getInstance();
//	}
	public void setT(Thread t){
		this.t = t;
		this.t.start();
	}
}
