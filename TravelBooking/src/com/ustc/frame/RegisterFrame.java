package com.ustc.frame;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.GridLayout;
import javax.swing.SwingConstants;
import java.awt.Color;

import com.ustc.bean.*;
import com.ustc.dao.*;
import javax.swing.JMenuBar;

public class RegisterFrame extends JFrame {

	private JPanel contentPane;
	private JTextField custName;
	private JTextField passWord;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegisterFrame frame = new RegisterFrame();
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
	
	@SuppressWarnings("unused")
	public RegisterFrame() {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		setTitle("客户注册_SA18225018");
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(0, 20, 424, 50);
		panel.add(panel_1);
		panel_1.setLayout(new GridLayout(1, 0, 0, 0));
		
		JLabel name = new JLabel("\u540D\u79F0");
		name.setBackground(Color.WHITE);
		name.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(name);
		
		custName = new JTextField();
		panel_1.add(custName);
		custName.setColumns(10);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(0, 98, 424, 50);
		panel.add(panel_2);
		panel_2.setLayout(new GridLayout(1, 0, 0, 0));
		
		JLabel pass = new JLabel("\u5BC6\u7801");
		pass.setHorizontalAlignment(SwingConstants.CENTER);
		panel_2.add(pass);
		
		passWord = new JTextField();
		panel_2.add(passWord);
		passWord.setColumns(10);
		
		JButton register = new JButton("\u6CE8\u518C");
		
		CustomersBean cust = null;
		register.addActionListener(new ActionListener() {
			//按钮监听事件
			public void actionPerformed(ActionEvent e) { 
				String cname = custName.getText();
				String psd = passWord.getText();
				CustomersBean cust = new CustomersBean(cname, psd);
				//调用dao类的插入方法
				jdbcHelper helper = new jdbcHelper();
				helper.cusRegister(cust);
			}
		});

		if(cust != null) {
			JOptionPane.showMessageDialog(this, "注册成功！", "alert", JOptionPane.INFORMATION_MESSAGE);
		}
		register.setBackground(Color.LIGHT_GRAY);
		register.setForeground(Color.BLACK);
		register.setBounds(165, 183, 93, 23);
		panel.add(register);
	}
	
}
