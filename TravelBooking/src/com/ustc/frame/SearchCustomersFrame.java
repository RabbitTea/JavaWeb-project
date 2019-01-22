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

	Vector rowData, columnNames; //rowData用来存放行数据，columnNames存放列名
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
	    setTitle("全部客户信息_SA18225018");
		
		columnNames = new Vector();
		// 设置列名
		columnNames.add("custName");
		columnNames.add("passWord");
		
		/*
		rowData = new Vector();
		//rowData行数据，可以存放多行
		Vector row = new Vector();
		row.add("Tom");
		row.add("123");
		//加入到一行数据
		rowData.add(row);
		*/
		
		//循环存放数据库中查询到的数据
		rowData = new Vector();
		jdbcHelper helper = new jdbcHelper();
		ArrayList<CustomersBean> custs = helper.searchCustomer();
		for(int i=0;i<custs.size();i++) {
			Vector row = new Vector();
			row.add(custs.get(i).getCustName());
			row.add(custs.get(i).getPassWord());
			rowData.add(row);
		}
		
		//初始化Jtable
		jt = new JTable(rowData, columnNames);
		
		//初始化JScrollPane
		jsp = new JScrollPane(jt);
		
		//把Jsp放入到JFrame
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
