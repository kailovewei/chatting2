package window;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import util.Constant;

public class OnlineClientWindow extends JFrame{
	private JPanel pan = new JPanel();
	private Vector<String> head = new Vector<String>();
	private Vector<Vector> info = null;
	private JTable table = null;
	private DefaultTableModel model = null;
	private JScrollPane jsp = null;
	private JButton butOK = new JButton("确定");
	private JButton butServer = new JButton("选择服务器");
	private ClientWindow clientWindow = null;
	
	public OnlineClientWindow(Vector<Vector> info,ClientWindow clientWindow){
		this.clientWindow = clientWindow;
		this.info = info;
		head.add("用户名");
		head.add("主机名");
		head.add("IP地址");
//		if(model==null){
//			model = new DefaultTableModel(info, head);
//		}else{
//			model.setDataVector(info, head);
//		}
		model = new DefaultTableModel(info, head);
//		if(table == null){
//			table = new JTable(model);
//		}else{
//			table.setModel(model);
//		}
		table = new JTable(model);
		jsp = new JScrollPane(table,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		pan.setLayout(new GridLayout(2, 1));
		pan.add(jsp);
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(1, 2));
		p.add(butOK);
		p.add(butServer);
		pan.add(p);
		setLayout(new GridLayout(1, 1));
		add(pan);
		pack();
		setVisible(true);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e){
				closeAction();
			}
		});
		butOK.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				butOKAction();
			}
		});
		butServer.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				butServerAction();
			}
		});
	}
	private void closeAction(){
		this.dispose();
		this.clientWindow.setEnabled(true);
	}
	public void updateInfo(Vector<Vector> info){
		this.info = info;
		model.setDataVector(this.info, head);
		table.setModel(model);
	}
	private void butOKAction(){
		int row = table.getSelectedRow();
		if(row<0){
			JOptionPane.showMessageDialog(this, "请在下表中选择要聊天的对象！");
			return;
		}
		String otherClientID = (String)table.getValueAt(row, 0);
		String otherClientName = (String)table.getValueAt(row, 1);
		String otherClientAddress = (String)table.getValueAt(row, 2);
		clientWindow.setOtherClientID(otherClientID);
		clientWindow.setOtherClientName(otherClientName);
		clientWindow.setOtherClientAddress(otherClientAddress);
		closeAction();
	}
	private void butServerAction(){
		clientWindow.setOtherClientID(null);
		clientWindow.setOtherClientName(null);
		clientWindow.setOtherClientAddress(null);//与服务器对话，其他用户是null
		closeAction();
	}
//	public static void main(String args[]){
<<<<<<< HEAD
//		new OnlineClientWindow(null,null);
//	}//
=======
//		new OnlineClientWindow(null);
//	}
	public void updateInfo(Vector<Vector> newInfo) {
		this.info = newInfo;
		model.setDataVector(info, head);
		table.setModel(model);
	}
>>>>>>> origin/tryAndTest
}
