package com.ustc.frame;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import com.ustc.bean.*;
import com.ustc.dao.*;

public class AddFlightFrame extends JFrame {

	private JPanel contentPane;
	private JTextField flightNum;
	private JTextField price;
	private JTextField numSeats;
	private JTextField numAvail;
	private JTextField FromCity;
	private JTextField ArivCity;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddFlightFrame frame = new AddFlightFrame();
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
	public AddFlightFrame() {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setTitle("航班信息录入_SA18225018");
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(1, 0, 0, 0));
		
		JPanel panel = new JPanel();
		contentPane.add(panel);
		panel.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 10, 404, 31);
		panel.add(panel_1);
		panel_1.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel lblNewLabel = new JLabel("flightNum");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(lblNewLabel);
		
		flightNum = new JTextField();
		panel_1.add(flightNum);
		flightNum.setColumns(10);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(10, 43, 404, 31);
		panel.add(panel_2);
		panel_2.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel lblNewLabel_1 = new JLabel("price");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel_2.add(lblNewLabel_1);
		
		price = new JTextField();
		panel_2.add(price);
		price.setColumns(10);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBounds(10, 78, 404, 31);
		panel.add(panel_3);
		panel_3.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel lblNumseats = new JLabel("numSeats");
		lblNumseats.setHorizontalAlignment(SwingConstants.CENTER);
		panel_3.add(lblNumseats);
		
		numSeats = new JTextField();
		numSeats.setColumns(10);
		panel_3.add(numSeats);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBounds(10, 114, 404, 31);
		panel.add(panel_4);
		panel_4.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel lblNumavail = new JLabel("numAvail");
		lblNumavail.setHorizontalAlignment(SwingConstants.CENTER);
		panel_4.add(lblNumavail);
		
		numAvail = new JTextField();
		numAvail.setColumns(10);
		panel_4.add(numAvail);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBounds(10, 147, 404, 31);
		panel.add(panel_5);
		panel_5.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel lblFromcity = new JLabel("FromCity");
		lblFromcity.setHorizontalAlignment(SwingConstants.CENTER);
		panel_5.add(lblFromcity);
		
		FromCity = new JTextField();
		FromCity.setColumns(10);
		panel_5.add(FromCity);
		
		JPanel panel_6 = new JPanel();
		panel_6.setBounds(10, 181, 404, 31);
		panel.add(panel_6);
		panel_6.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel lblArivcity = new JLabel("ArivCity");
		lblArivcity.setHorizontalAlignment(SwingConstants.CENTER);
		panel_6.add(lblArivcity);
		
		ArivCity = new JTextField();
		ArivCity.setColumns(10);
		panel_6.add(ArivCity);
		
		JButton button = new JButton("\u5F55\u5165");
		button.addActionListener(new ActionListener() {
			//监听事件，录入航班信息
			public void actionPerformed(ActionEvent e) {
				String fn = flightNum.getText();
				String pris = price.getText();
				int pri = Integer.parseInt(pris);
				String nss = numSeats.getText();
				int ns = Integer.parseInt(nss);
				String nas = numAvail.getText();
				int na = Integer.parseInt(nas);
				String fc = FromCity.getText();
				String ac = ArivCity.getText();
				
				FlightsBean flight = new FlightsBean(fn, pri, ns, na, fc, ac);
				
				jdbcHelper helper = new jdbcHelper();
				helper.flightInsert(flight);
			}
		});
		button.setBounds(168, 218, 93, 23);
		panel.add(button);
	}

}
