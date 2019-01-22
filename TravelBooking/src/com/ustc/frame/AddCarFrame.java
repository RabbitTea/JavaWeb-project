package com.ustc.frame;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import com.ustc.bean.*;
import com.ustc.dao.*;

public class AddCarFrame extends JFrame {

	private JPanel contentPane;
	private JTextField type;
	private JPanel panel_1;
	private JLabel lblLocation;
	private JTextField location;
	private JPanel panel_2;
	private JLabel lblPrice;
	private JTextField price;
	private JPanel panel_3;
	private JLabel lblNumrooms;
	private JTextField numCars;
	private JPanel panel_4;
	private JLabel lblNumavail;
	private JTextField numAvail;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddCarFrame frame = new AddCarFrame();
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
	public AddCarFrame() {
		setTitle("\u5F55\u5165\u51FA\u79DF\u8F66\u4FE1\u606F_SA18225018");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setTitle("录入出租车信息_SA18225018");
		
		panel_1 = new JPanel();
		panel_1.setBounds(10, 50, 414, 35);
		contentPane.add(panel_1);
		panel_1.setLayout(new GridLayout(1, 0, 0, 0));
		
		lblLocation = new JLabel("location");
		lblLocation.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(lblLocation);
		
		location = new JTextField();
		location.setColumns(10);
		panel_1.add(location);
		
		panel_2 = new JPanel();
		panel_2.setBounds(10, 92, 414, 35);
		contentPane.add(panel_2);
		panel_2.setLayout(new GridLayout(1, 0, 0, 0));
		
		lblPrice = new JLabel("price");
		lblPrice.setHorizontalAlignment(SwingConstants.CENTER);
		panel_2.add(lblPrice);
		
		price = new JTextField();
		price.setColumns(10);
		panel_2.add(price);
		
		panel_3 = new JPanel();
		panel_3.setBounds(10, 133, 414, 35);
		contentPane.add(panel_3);
		panel_3.setLayout(new GridLayout(1, 0, 0, 0));
		
		lblNumrooms = new JLabel("numCars");
		lblNumrooms.setHorizontalAlignment(SwingConstants.CENTER);
		panel_3.add(lblNumrooms);
		
		numCars = new JTextField();
		numCars.setColumns(10);
		panel_3.add(numCars);
		
		panel_4 = new JPanel();
		panel_4.setBounds(10, 176, 414, 35);
		contentPane.add(panel_4);
		panel_4.setLayout(new GridLayout(1, 0, 0, 0));
		
		lblNumavail = new JLabel("numAvail");
		lblNumavail.setHorizontalAlignment(SwingConstants.CENTER);
		panel_4.add(lblNumavail);
		
		numAvail = new JTextField();
		numAvail.setColumns(10);
		panel_4.add(numAvail);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 10, 414, 35);
		contentPane.add(panel);
		panel.setLayout(new GridLayout(1, 0, 0, 0));
		
		JLabel lblNewLabel = new JLabel("type");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel);
		
		type = new JTextField();
		panel.add(type);
		type.setColumns(10);
		
		JButton btnNewButton = new JButton("\u5F55\u5165");
		btnNewButton.addActionListener(new ActionListener() {
			//插入出租车信息到数据库
			public void actionPerformed(ActionEvent arg0) {
				String tp = type.getText();
				String lct = location.getText();
				String pris = price.getText();
				int pri = Integer.parseInt(pris);
				String ncs = numCars.getText();
				int nc = Integer.parseInt(ncs);
				String nas = numAvail.getText();
				int na = Integer.parseInt(nas);
				
				CarsBean car = new CarsBean(tp, lct, pri, nc, na);
				jdbcHelper helper = new jdbcHelper();
				helper.carInsert(car);
			}
		});
		btnNewButton.setBounds(172, 228, 93, 23);
		contentPane.add(btnNewButton);
	}
}
