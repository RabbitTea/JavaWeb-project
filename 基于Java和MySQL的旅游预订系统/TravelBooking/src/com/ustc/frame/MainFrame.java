package com.ustc.frame;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.SwingConstants;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainFrame extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
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
	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setTitle("SA18225018_曹力月_控制面板");
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("\u5F55\u5165\u4FE1\u606F");
		mnNewMenu.setHorizontalAlignment(SwingConstants.CENTER);
		menuBar.add(mnNewMenu);
		
		JMenuItem menuItem = new JMenuItem("\u5F55\u5165\u5BA2\u6237\u4FE1\u606F");
		menuItem.addActionListener(new ActionListener() {
			//弹出学生信息录入面板
			public void actionPerformed(ActionEvent e) {
				RegisterFrame frame = new RegisterFrame();
				frame.setVisible(true);
			}
		});
		mnNewMenu.add(menuItem);
		
		JMenuItem menuItem_1 = new JMenuItem("\u5F55\u5165\u822A\u73ED\u4FE1\u606F");
		menuItem_1.addActionListener(new ActionListener() {
			//显示航班录入面板
			public void actionPerformed(ActionEvent e) {
				AddFlightFrame frame = new AddFlightFrame();
				frame.setVisible(true);
			}
		});
		mnNewMenu.add(menuItem_1);
		
		JMenuItem menuItem_2 = new JMenuItem("\u5F55\u5165\u51FA\u79DF\u8F66\u4FE1\u606F");
		menuItem_2.addActionListener(new ActionListener() {
			//显示出租车录入面板
			public void actionPerformed(ActionEvent e) {
				AddCarFrame frame = new AddCarFrame();
				frame.setVisible(true);
			}
		});
		mnNewMenu.add(menuItem_2);
		
		JMenuItem menuItem_3 = new JMenuItem("\u5F55\u5165\u9152\u5E97\u4FE1\u606F");
		menuItem_3.addActionListener(new ActionListener() {
			//显示酒店录入面板
			public void actionPerformed(ActionEvent e) {
				AddHotelFrame frame = new AddHotelFrame();
				frame.setVisible(true);
			}
		});
		mnNewMenu.add(menuItem_3);
		
		JMenu mnNewMenu_1 = new JMenu("\u67E5\u8BE2\u4FE1\u606F");
		mnNewMenu_1.setHorizontalAlignment(SwingConstants.CENTER);
		menuBar.add(mnNewMenu_1);
		
		JMenuItem menuItem_4 = new JMenuItem("\u5168\u90E8\u5BA2\u6237\u4FE1\u606F");
		menuItem_4.addActionListener(new ActionListener() {
			//查询客户信息
			public void actionPerformed(ActionEvent arg0) {
				SearchCustomersFrame frame = new SearchCustomersFrame();
				frame.setVisible(true);
			}
		});
		mnNewMenu_1.add(menuItem_4);
		
		JMenuItem menuItem_5 = new JMenuItem("\u5168\u90E8\u822A\u73ED\u4FE1\u606F");
		menuItem_5.addActionListener(new ActionListener() {
			//查询航班信息
			public void actionPerformed(ActionEvent e) {
				SearchFlightsFrame frame = new SearchFlightsFrame();
				frame.setVisible(true);
			}
			
		});
		mnNewMenu_1.add(menuItem_5);
		
		JMenuItem menuItem_6 = new JMenuItem("\u5168\u90E8\u9152\u5E97\u4FE1\u606F");
		menuItem_6.addActionListener(new ActionListener() {
			//查询酒店信息
			public void actionPerformed(ActionEvent e) {
				SearchHotelsFrame frame = new SearchHotelsFrame();
				frame.setVisible(true);
			}
		});
		mnNewMenu_1.add(menuItem_6);
		
		JMenuItem menuItem_7 = new JMenuItem("\u5168\u90E8\u51FA\u79DF\u8F66\u4FE1\u606F");
		menuItem_7.addActionListener(new ActionListener() {
			//查询出租车信息
			public void actionPerformed(ActionEvent e) {
				SearchCarsFrame frame = new SearchCarsFrame();
				frame.setVisible(true);
			}
		});
		mnNewMenu_1.add(menuItem_7);
		
		JMenu menu = new JMenu("\u9884\u5B9A");
		menu.setHorizontalAlignment(SwingConstants.CENTER);
		menuBar.add(menu);
		
		JMenuItem menuItem_8 = new JMenuItem("\u9884\u5B9A\u822A\u73ED");
		menuItem_8.addActionListener(new ActionListener() {
			//预定航班
			public void actionPerformed(ActionEvent e) {
				BookFlight frame = new BookFlight();
				frame.setVisible(true);
			}
		});
		menu.add(menuItem_8);
		
		JMenuItem menuItem_9 = new JMenuItem("\u9884\u5B9A\u9152\u5E97");
		menuItem_9.addActionListener(new ActionListener() {
			//预定酒店
			public void actionPerformed(ActionEvent e) {
				BookHotel frame = new BookHotel();
				frame.setVisible(true);
			}
		});
		menu.add(menuItem_9);
		
		JMenuItem menuItem_10 = new JMenuItem("\u9884\u5B9A\u51FA\u79DF\u8F66");
		menuItem_10.addActionListener(new ActionListener() {
			//预定出租车
			public void actionPerformed(ActionEvent e) {
				BookCar frame = new BookCar();
				frame.setVisible(true);
			}
		});
		menu.add(menuItem_10);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}

}
