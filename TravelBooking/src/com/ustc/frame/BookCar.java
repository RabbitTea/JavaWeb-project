package com.ustc.frame;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import com.ustc.bean.*;
import com.ustc.dao.*;

public class BookCar extends JFrame {

	private JPanel contentPane;
	private JTextField cOrderId;
	private JTextField custName;
	private JTextField carType;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BookCar frame = new BookCar();
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
	public BookCar() {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setTitle("预定出租车_SA18225018");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(0, 61, 424, 36);
		contentPane.add(panel_1);
		panel_1.setLayout(new GridLayout(1, 0, 0, 0));
		
		JLabel lblFlighttype = new JLabel("custName");
		lblFlighttype.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(lblFlighttype);
		
		custName = new JTextField();
		custName.setColumns(10);
		panel_1.add(custName);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(0, 113, 424, 36);
		contentPane.add(panel_2);
		panel_2.setLayout(new GridLayout(1, 0, 0, 0));
		
		JLabel lblCustname = new JLabel("carType");
		lblCustname.setHorizontalAlignment(SwingConstants.CENTER);
		panel_2.add(lblCustname);
		
		carType = new JTextField();
		carType.setColumns(10);
		panel_2.add(carType);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 10, 424, 36);
		contentPane.add(panel);
		panel.setLayout(new GridLayout(1, 0, 0, 0));
		
		JLabel lblNewLabel = new JLabel("cOrderId");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel);
		
		cOrderId = new JTextField();
		panel.add(cOrderId);
		cOrderId.setColumns(10);
		
		JButton button = new JButton("\u9884\u5B9A");
		button.addActionListener(new ActionListener() {
			//预定出租车
			public void actionPerformed(ActionEvent arg0) {
				CarsOrderBean carOrder = new CarsOrderBean();
				
				String scoId = cOrderId.getText();
				int coId = Integer.parseInt(scoId);
				String cn = custName.getText();
				String ct = carType.getText();
				
				carOrder.setcOrderId(coId);
				carOrder.setCustName(cn);
				carOrder.setCarType(ct);
				
				jdbcHelper helper = new jdbcHelper();
				helper.BookCar(carOrder);
			}
		});
		button.setBounds(173, 191, 93, 23);
		contentPane.add(button);
	}
}
