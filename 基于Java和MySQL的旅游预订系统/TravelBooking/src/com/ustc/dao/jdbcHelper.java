package com.ustc.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.ustc.bean.*;

public class jdbcHelper {
	// �����������ݿ�����Ҫ�Ķ���
	private Connection conn = null;
	private PreparedStatement ps = null;
    private ResultSet rs = null;
    
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/trip_book";
    private static final String USER = "root";
    private static final String PASS = "";
    
    //�޲ι��캯��
    public jdbcHelper() {
    	this.init();
    }
    
    // ������ݿ������
    private void init() {
    	try {
			//ע��JDBC����
			Class.forName(JDBC_DRIVER);
			//�������ݿ�
			System.out.println("�������ݿ�...");
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    
    /*
     *  ����ģ�飺������Ϣ
     */
    //��ӹ˿���Ϣ
    public boolean cusRegister(CustomersBean cust) {
    	boolean result = true;
    	
    	try {
			String sql = "insert into customers(custName,passWord) values(?,?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, cust.getCustName());
			ps.setString(2, cust.getPassWord());
			if(ps.executeUpdate() != 1) {  // ִ��sql���
				result = false;
				System.out.println("ע��ʧ��...");
			}
			System.out.println("ע��ɹ�...");
		} catch (SQLException e) {
			result = false;
			e.printStackTrace();
		}
    	return result;
    }
    
    //��Ӻ�����Ϣ
    public boolean flightInsert(FlightsBean flight) {
    	boolean result = true;
    	
    	try {
			String sql = "insert into flights(flightNum,price,numSeats,numAvail,FromCity,ArivCity) values(?,?,?,?,?,?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, flight.getFlightNum());
			ps.setInt(2, flight.getPrice());
			ps.setInt(3, flight.getNumSeats());
			ps.setInt(4, flight.getNumAvail());
			ps.setString(5, flight.getFromCity());
			ps.setString(6, flight.getArivCity());
			if(ps.executeUpdate() != 1) {  // ִ��sql���
				result = false;
				System.out.println("���뺽����Ϣʧ��...");
			}
			System.out.println("���뺽����Ϣ�ɹ�...");
		} catch (SQLException e) {
			result = false;
			e.printStackTrace();
		}
    	return result;
    }
    
    //��ӳ��⳵��Ϣ
    public boolean carInsert(CarsBean car) {
    	boolean result = true;
    	
    	try {
			String sql = "insert into cars(type,location,price,numCars,numAvail) values(?,?,?,?,?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, car.getType());
			ps.setString(2, car.getLocation());
			ps.setInt(3, car.getPrice());
			ps.setInt(4, car.getNumCars());
			ps.setInt(5, car.getNumAvail());
			if(ps.executeUpdate() != 1) {  //ִ��sql���
				result = false;
				System.out.println("������⳵��Ϣʧ��...");
			}
			System.out.println("������⳵��Ϣ�ɹ�...");
		} catch (SQLException e) {
			result = false;
			e.printStackTrace();
		}
    	return result;
    }
    
    //��ӾƵ���Ϣ
    public boolean hotelInsert(HotelsBean hotel) {
    	boolean result = true;
    	
    	try {
			String sql = "insert into hotels(name,location,price,numRooms,numAvail) values(?,?,?,?,?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, hotel.getName());
			ps.setString(2, hotel.getLocation());
			ps.setInt(3, hotel.getPrice());
			ps.setInt(4, hotel.getNumRooms());
			ps.setInt(5, hotel.getNumAvail());
			if(ps.executeUpdate() != 1) {  //ִ��sql���
				result = false;
				System.out.println("����Ƶ���Ϣʧ��...");
			}
			System.out.println("����Ƶ���Ϣ�ɹ�...");
		} catch (SQLException e) {
			result = false;
			e.printStackTrace();
		}
    	return result;
    }
    
    
    /*
     *  Ԥ�����࣬���⳵������
     */
    //Ԥ������
    public boolean BookFlight(FlightsOrderBean flightOrder) {
    	boolean result = true;
    	try {
			String sql = "insert into flightorder(fOrderId,flightType,custName) values(?,?,?)";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, flightOrder.getfOrderId());
			ps.setString(2, flightOrder.getFlightType());
			ps.setString(3, flightOrder.getCustName());
			if(ps.executeUpdate() != 1) {
				result = false;
				System.out.println("Ԥ������ʧ��...");
			}
			System.out.println("Ԥ������ɹ�...");
		} catch (SQLException e) {
			result = false;
			e.printStackTrace();
		}
    	return result;
    }
    
  //Ԥ�����⳵
    public boolean BookCar(CarsOrderBean carOrder) {
    	boolean result = true;
    	
    	try {
			String sql = "insert into carsorder(cOrderId,custName,carType) values(?,?,?)";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, carOrder.getcOrderId());
			ps.setString(2, carOrder.getCustName());
			ps.setString(3, carOrder.getCarType());
			if(ps.executeUpdate() != 1) {
				result = false;
				System.out.println("Ԥ�����⳵ʧ��...");
			}
			System.out.println("Ԥ�����⳵�ɹ�...");
		} catch (SQLException e) {
			result = false;
			e.printStackTrace();
		}
    	return result;
    }
    
    //Ԥ���Ƶ�
    public boolean BookHotel(HotelsOrderBean hotelOrder) {
    	boolean result = true;
    	
    	try {
			String sql = "insert into hotelsorder(hOrderId,hotelType,custName,hotelRoom) values(?,?,?,?)";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, hotelOrder.gethOrderId());
			ps.setString(2, hotelOrder.getHotelType());
			ps.setString(3, hotelOrder.getCustName());
			ps.setString(4, hotelOrder.getHotelRoom());
			if(ps.executeUpdate() != 1) {
				result = false;
				System.out.println("Ԥ���Ƶ�ʧ��...");
			}
			System.out.println("Ԥ���Ƶ�ɹ�...");
		} catch (SQLException e) {
			result = false;
			e.printStackTrace();
		}
    	return result;
    }
    
    
    /*
     *   ����ģ�飺��ѯ��Ϣ
     */
    // ��ѯ���к�����Ϣ
    public ArrayList<FlightsBean> searchFlight() {
    	ArrayList<FlightsBean> flights = new ArrayList();
    	
    	try {
			String sql = "select * from flights";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				FlightsBean flight = new FlightsBean();
				flight.setFlightNum(rs.getString(1));
				flight.setPrice(rs.getInt(2));
				flight.setNumSeats(rs.getInt(3));
				flight.setNumAvail(rs.getInt(4));
				flight.setFromCity(rs.getString(5));
				flight.setArivCity(rs.getString(6));
				flights.add(flight);
			}
			//System.out.println(flight.getFlightNum());
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return flights;
    }
    
    // ��ѯ���г��⳵��Ϣ
    public ArrayList<CarsBean> searchCar() {
    	ArrayList<CarsBean> cars = new ArrayList();
    	
    	try {
			String sql = "select * from cars";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				CarsBean car = new CarsBean();
				car.setType(rs.getString(1));
				car.setLocation(rs.getString(2));
				car.setPrice(rs.getInt(3));
				car.setNumCars(rs.getInt(4));
				car.setNumAvail(rs.getInt(5));
				cars.add(car);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return cars;
    }
    
    // ��ѯ���оƵ���Ϣ
    public ArrayList<HotelsBean> searchHotel() {
    	ArrayList<HotelsBean> hotels = new ArrayList();
    	
    	try {
			String sql = "select * from hotels";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				HotelsBean hotel = new HotelsBean();
				hotel.setName(rs.getString(1));
				hotel.setLocation(rs.getString(2));
				hotel.setPrice(rs.getInt(3));
				hotel.setNumRooms(rs.getInt(4));
				hotel.setNumAvail(rs.getInt(5));
				hotels.add(hotel);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return hotels;
    }
    
    //��ѯ���пͻ���Ϣ
    public ArrayList<CustomersBean> searchCustomer() {
    	ArrayList<CustomersBean> custs = new ArrayList();
    	
    	try {
			String sql = "select * from customers";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				CustomersBean cust = new CustomersBean(null,null);
				cust.setCustName(rs.getString(1));
				cust.setPassWord(rs.getString(2));
				custs.add(cust);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return custs;
    }
    
}
