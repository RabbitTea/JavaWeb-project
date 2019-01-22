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

public class BookHotel extends JFrame {

	private JPanel contentPane;
	private JTextField hOrderId;
	private JTextField hotelType;
	private JTextField custName;
	private JTextField hotelRoom;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BookHotel frame = new BookHotel();
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
	public BookHotel() {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setTitle("Ô¤¶¨¾Æµê_SA18225018");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBounds(0, 167, 424, 36);
		contentPane.add(panel_3);
		panel_3.setLayout(new GridLayout(1, 0, 0, 0));
		
		JLabel lblHotelroom = new JLabel("hotelRoom");
		lblHotelroom.setHorizontalAlignment(SwingConstants.CENTER);
		panel_3.add(lblHotelroom);
		
		hotelRoom = new JTextField();
		hotelRoom.setColumns(10);
		panel_3.add(hotelRoom);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(0, 61, 424, 36);
		contentPane.add(panel_1);
		panel_1.setLayout(new GridLayout(1, 0, 0, 0));
		
		JLabel lblFlighttype = new JLabel("hotelType");
		lblFlighttype.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(lblFlighttype);
		
		hotelType = new JTextField();
		hotelType.setColumns(10);
		panel_1.add(hotelType);
		
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
		
		JLabel lblNewLabel = new JLabel("hOrderId");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel);
		
		hOrderId = new JTextField();
		panel.add(hOrderId);
		hOrderId.setColumns(10);
		
		JButton button = new JButton("\u9884\u5B9A");
		button.addActionListener(new ActionListener() {
			//Ô¤¶¨¾Æµê
			public void actionPerformed(ActionEvent arg0) {
				HotelsOrderBean hotelOrder = new HotelsOrderBean();
				
				String shoId = hOrderId.getText();
				int hoId = Integer.parseInt(shoId);
				String ht = hotelType.getText();
				String cn = custName.getText();
				String hr = hotelRoom.getText();
				
				hotelOrder.sethOrderId(hoId);
				hotelOrder.setHotelType(ht);
				hotelOrder.setCustName(cn);
				hotelOrder.setHotelRoom(hr);
				
				jdbcHelper helper = new jdbcHelper();
				helper.BookHotel(hotelOrder);
			}
		});
		button.setBounds(175, 217, 93, 23);
		contentPane.add(button);
	}
}
