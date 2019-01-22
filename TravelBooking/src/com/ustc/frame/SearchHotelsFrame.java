package com.ustc.frame;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;

import com.ustc.bean.*;
import com.ustc.dao.*;

public class SearchHotelsFrame extends JFrame {

	private Vector rowData, columnNames;
	private JTable jt = null;
	private JScrollPane jsp = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SearchHotelsFrame frame = new SearchHotelsFrame();
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
	public SearchHotelsFrame() {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setTitle("全部酒店信息_SA18225018");
		
		columnNames = new Vector();
		columnNames.add("name");
		columnNames.add("location");
		columnNames.add("price");
		columnNames.add("numRooms");
		columnNames.add("numAvail");
		
		rowData = new Vector();
		jdbcHelper helper = new jdbcHelper();
		ArrayList<HotelsBean> hotels = new ArrayList();
		hotels = helper.searchHotel();
		for(int i=0;i<hotels.size();i++) {
			Vector row = new Vector();
			row.add(hotels.get(i).getName());
			row.add(hotels.get(i).getLocation());
			row.add(hotels.get(i).getPrice());
			row.add(hotels.get(i).getNumRooms());
			row.add(hotels.get(i).getNumAvail());
			rowData.add(row);
		}
		
		//初始化JTable
		jt = new JTable(rowData, columnNames);
		
		//初始化jsp
		jsp = new JScrollPane(jt);
		
		//把jsp放到JFrame
		this.add(jsp);
		
		this.setSize(400,300);
		
		setTableColumnCenter(jt);
	}
	
	//设置表格中文字居中
	public void setTableColumnCenter(JTable table) {
		DefaultTableCellRenderer r = new DefaultTableCellRenderer();
		r.setHorizontalAlignment(JLabel.CENTER);
		table.setDefaultRenderer(Object.class, r);
	}


}
