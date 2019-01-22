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

public class BookFlight extends JFrame {

	private JPanel contentPane;
	private JTextField fOrderId;
	private JTextField flightType;
	private JTextField custName;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BookFlight frame = new BookFlight();
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
	public BookFlight() {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setTitle("Ô¤¶¨º½°à_SA18225018");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(0, 61, 424, 36);
		contentPane.add(panel_1);
		panel_1.setLayout(new GridLayout(1, 0, 0, 0));
		
		JLabel lblFlighttype = new JLabel("flightType");
		lblFlighttype.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(lblFlighttype);
		
		flightType = new JTextField();
		flightType.setColumns(10);
		panel_1.add(flightType);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(0, 113, 424, 36);
		contentPane.add(panel_2);
		panel_2.setLayout(new GridLayout(1, 0, 0, 0));
		
		JLabel lblCustname = new JLabel("custName");
		lblCustname.setHorizontalAlignment(SwingConstants.CENTER);
		panel_2.add(lblCustname);
		
		custName = new JTextField();
		custName.setColumns(10);
		panel_2.add(custName);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 10, 424, 36);
		contentPane.add(panel);
		panel.setLayout(new GridLayout(1, 0, 0, 0));
		
		JLabel lblNewLabel = new JLabel("fOrderId");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel);
		
		fOrderId = new JTextField();
		panel.add(fOrderId);
		fOrderId.setColumns(10);
		
		JButton button = new JButton("\u9884\u5B9A");
		button.addActionListener(new ActionListener() {
			//Ô¤¶¨º½°à
			public void actionPerformed(ActionEvent e) {
				FlightsOrderBean flightOrder = new FlightsOrderBean();
				
				String sfoId = fOrderId.getText();
				int foId = Integer.parseInt(sfoId);
				String ft = flightType.getText();
				String cn = custName.getText();
				
				flightOrder.setfOrderId(foId);
				flightOrder.setFlightType(ft);
				flightOrder.setCustName(cn);
				jdbcHelper helper = new jdbcHelper();
				helper.BookFlight(flightOrder);
			}
		});
		button.setBounds(173, 191, 93, 23);
		contentPane.add(button);
	}
}
