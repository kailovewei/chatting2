package window;

import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

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
	}
	private void closeAction(){
		this.dispose();
		this.clientWindow.setEnabled(true);
	}
//	public static void main(String args[]){
//		new OnlineClientWindow(null);
//	}
}
