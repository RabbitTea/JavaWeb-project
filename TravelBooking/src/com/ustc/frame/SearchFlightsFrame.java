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

public class SearchFlightsFrame extends JFrame {

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
					SearchFlightsFrame frame = new SearchFlightsFrame();
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
	public SearchFlightsFrame() {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setTitle("全部航班信息_SA18225018");
		
		columnNames = new Vector();
		columnNames.add("flightNum");
		columnNames.add("price");
		columnNames.add("numSeats");
		columnNames.add("numAvail");
		columnNames.add("FromCity");
		columnNames.add("ArivCity");
		
		rowData = new Vector();
		jdbcHelper helper = new jdbcHelper();
		ArrayList<FlightsBean> flights = helper.searchFlight();
		for(int i=0;i<flights.size();i++) {
			Vector row = new Vector();
			row.add(flights.get(i).getFlightNum());
			row.add(flights.get(i).getPrice());
			row.add(flights.get(i).getNumSeats());
			row.add(flights.get(i).getNumAvail());
			row.add(flights.get(i).getFromCity());
			row.add(flights.get(i).getArivCity());
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
