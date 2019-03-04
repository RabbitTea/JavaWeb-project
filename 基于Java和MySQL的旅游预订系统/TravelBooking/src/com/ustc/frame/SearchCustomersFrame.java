package com.ustc.frame;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.JTable;

import com.ustc.bean.*;
import com.ustc.dao.*;

public class SearchCustomersFrame extends JFrame {

	Vector rowData, columnNames; //rowData������������ݣ�columnNames�������
	JTable jt = null;
	JScrollPane jsp = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SearchCustomersFrame frame = new SearchCustomersFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SearchCustomersFrame() {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
	    setTitle("ȫ���ͻ���Ϣ_SA18225018");
		
		columnNames = new Vector();
		// ��������
		columnNames.add("custName");
		columnNames.add("passWord");
		
		/*
		rowData = new Vector();
		//rowData�����ݣ����Դ�Ŷ���
		Vector row = new Vector();
		row.add("Tom");
		row.add("123");
		//���뵽һ������
		rowData.add(row);
		*/
		
		//ѭ��������ݿ��в�ѯ��������
		rowData = new Vector();
		jdbcHelper helper = new jdbcHelper();
		ArrayList<CustomersBean> custs = helper.searchCustomer();
		for(int i=0;i<custs.size();i++) {
			Vector row = new Vector();
			row.add(custs.get(i).getCustName());
			row.add(custs.get(i).getPassWord());
			rowData.add(row);
		}
		
		//��ʼ��Jtable
		jt = new JTable(rowData, columnNames);
		
		//��ʼ��JScrollPane
		jsp = new JScrollPane(jt);
		
		//��Jsp���뵽JFrame
		this.add(jsp);
		this.setSize(400,300);
		
		setTableColumnCenter(jt);
	}
	
	public void setTableColumnCenter(JTable table) {
		DefaultTableCellRenderer r = new DefaultTableCellRenderer();
		r.setHorizontalAlignment(JLabel.CENTER);
		table.setDefaultRenderer(Object.class, r);
	}

}
