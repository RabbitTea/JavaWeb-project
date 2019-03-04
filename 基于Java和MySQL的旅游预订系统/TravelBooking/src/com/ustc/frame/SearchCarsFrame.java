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

public class SearchCarsFrame extends JFrame {

	private Vector rowData, columnNames;
	JTable jt = null;
	JScrollPane jsp = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SearchCarsFrame frame = new SearchCarsFrame();
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
	public SearchCarsFrame() {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setTitle("全部出租车信息_SA18225018");
		
		columnNames = new Vector();
		columnNames.add("type");
		columnNames.add("location");
		columnNames.add("price");
		columnNames.add("numCars");
		columnNames.add("numAvail");
		
		rowData = new Vector();
		jdbcHelper helper = new jdbcHelper();
		ArrayList<CarsBean> cars = helper.searchCar();
		for(int i=0;i<cars.size();i++) {
			Vector row = new Vector();
			row.add(cars.get(i).getType());
			row.add(cars.get(i).getLocation());
			row.add(cars.get(i).getPrice());
			row.add(cars.get(i).getNumCars());
			row.add(cars.get(i).getNumAvail());
			rowData.add(row);
		}
		
		jt = new JTable(rowData, columnNames);
		
		jsp = new JScrollPane(jt);
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
