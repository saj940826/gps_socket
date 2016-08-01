package view;

import controller.SocketThread;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;
import javax.swing.text.DefaultCaret;
import javax.swing.text.html.ObjectView;
import java.awt.*;

/**
 * Created by Administrator on 2016/7/18 0018.
 */
public class ClientMonitor{
	public static final int NORMAL = 3;
	public static final int GPS_ERROR = 2;
	public static final int BLOCKING = 1;
	public static final int OFFLINE = 0;
	private DefaultTableModel model;
	private DefaultTableModel locationModel;

	public ClientMonitor() {
		String[] clientColumnNames = {" ","ID",
				"IP",
				"Received",
				"Status"
				};
		model = new DefaultTableModel(new Object[][]{}, clientColumnNames);
		JTable table = new JTable(model);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setSize(600,800);
		scrollPane.setBorder(new BevelBorder(BevelBorder.RAISED));
		table.setPreferredScrollableViewportSize(new Dimension(580,800));
		table.setDefaultRenderer(Object.class,new StatusRenderer());
		TableColumn statusColumn = table.getColumnModel().getColumn(0);
		statusColumn.setMaxWidth(1);

		String[] locationColumnName = {"ID",
				"Longitude",
				"Latitude",
				"Date&Time"
		};
		locationModel = new DefaultTableModel(new Object[][]{}, locationColumnName);
		JTable locationTable = new JTable(locationModel);
		JScrollPane scrollPanel2 = new JScrollPane(locationTable);
		scrollPanel2.setSize(800,800);
		scrollPanel2.setBorder(new BevelBorder(BevelBorder.RAISED));
		locationTable.setPreferredScrollableViewportSize(new Dimension(800,800));

		JSplitPane contentPane = new JSplitPane();
		contentPane.setLeftComponent(scrollPane);
		contentPane.setRightComponent(scrollPanel2);
		contentPane.setDividerSize(3);
		contentPane.setDividerLocation(600);
		contentPane.setBorder(BorderFactory.createEtchedBorder());

		JFrame frame = new JFrame("GPS Server");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1410,810);
		frame.setResizable(false);
		frame.getContentPane().setSize(1400,800);
		frame.getContentPane().setLayout(new GridLayout(1,2));
		frame.getContentPane().add(contentPane);
		frame.setLocationByPlatform(true);
		frame.setVisible(true);
	}

	public void addClient(SocketThread socketThread) {
		Object[] row = new Object[]{socketThread.getStatus(),socketThread.getID(), socketThread.getIP(),socketThread.getReceivedNumber(), socketThread.getStatusString()};
		model.addRow(row);
	}

	public void updateClient(SocketThread socketThread) {
		for (int i = 0; i < model.getRowCount(); i++)
			if (model.getValueAt(i, 1).equals(socketThread.getID())){
				model.setValueAt(socketThread.getStatus(), i, 0);
				model.setValueAt(socketThread.getIP(), i, 2);
				model.setValueAt(socketThread.getReceivedNumber(), i, 3);
				model.setValueAt(socketThread.getStatusString(), i, 4);
			}
	}

	public void addLocation(double[] location) {

	}

	class StatusRenderer implements TableCellRenderer {
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			JTextField editor = new JTextField();
			if (value != null && column != 0) {
				editor.setText(value.toString());
			}
			if(column == 0){
				switch ((int) value){
					case NORMAL:
						editor.setBackground(Color.green);
						break;
					case OFFLINE:
						editor.setBackground(Color.LIGHT_GRAY);
						break;
					case BLOCKING:
						editor.setBackground(Color.ORANGE);
						break;
				}
			}
			return editor;
		}
	}
}
