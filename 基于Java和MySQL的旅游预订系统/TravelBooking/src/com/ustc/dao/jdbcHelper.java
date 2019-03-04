package com.ustc.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.ustc.bean.*;

public class jdbcHelper {
	// 定义连接数据库所需要的对象
	private Connection conn = null;
	private PreparedStatement ps = null;
    private ResultSet rs = null;
    
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/trip_book";
    private static final String USER = "root";
    private static final String PASS = "";
    
    //无参构造函数
    public jdbcHelper() {
    	this.init();
    }
    
    // 获得数据库的连接
    private void init() {
    	try {
			//注册JDBC驱动
			Class.forName(JDBC_DRIVER);
			//连接数据库
			System.out.println("连接数据库...");
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    
    /*
     *  功能模块：插入信息
     */
    //添加顾客信息
    public boolean cusRegister(CustomersBean cust) {
    	boolean result = true;
    	
    	try {
			String sql = "insert into customers(custName,passWord) values(?,?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, cust.getCustName());
			ps.setString(2, cust.getPassWord());
			if(ps.executeUpdate() != 1) {  // 执行sql语句
				result = false;
				System.out.println("注册失败...");
			}
			System.out.println("注册成功...");
		} catch (SQLException e) {
			result = false;
			e.printStackTrace();
		}
    	return result;
    }
    
    //添加航班信息
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
			if(ps.executeUpdate() != 1) {  // 执行sql语句
				result = false;
				System.out.println("插入航班信息失败...");
			}
			System.out.println("插入航班信息成功...");
		} catch (SQLException e) {
			result = false;
			e.printStackTrace();
		}
    	return result;
    }
    
    //添加出租车信息
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
			if(ps.executeUpdate() != 1) {  //执行sql语句
				result = false;
				System.out.println("插入出租车信息失败...");
			}
			System.out.println("插入出租车信息成功...");
		} catch (SQLException e) {
			result = false;
			e.printStackTrace();
		}
    	return result;
    }
    
    //添加酒店信息
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
			if(ps.executeUpdate() != 1) {  //执行sql语句
				result = false;
				System.out.println("插入酒店信息失败...");
			}
			System.out.println("插入酒店信息成功...");
		} catch (SQLException e) {
			result = false;
			e.printStackTrace();
		}
    	return result;
    }
    
    
    /*
     *  预定航班，出租车，宾馆
     */
    //预定航班
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
				System.out.println("预定航班失败...");
			}
			System.out.println("预定航班成功...");
		} catch (SQLException e) {
			result = false;
			e.printStackTrace();
		}
    	return result;
    }
    
  //预定出租车
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
				System.out.println("预定出租车失败...");
			}
			System.out.println("预定出租车成功...");
		} catch (SQLException e) {
			result = false;
			e.printStackTrace();
		}
    	return result;
    }
    
    //预定酒店
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
				System.out.println("预定酒店失败...");
			}
			System.out.println("预定酒店成功...");
		} catch (SQLException e) {
			result = false;
			e.printStackTrace();
		}
    	return result;
    }
    
    
    /*
     *   功能模块：查询信息
     */
    // 查询所有航班信息
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
    
    // 查询所有出租车信息
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
    
    // 查询所有酒店信息
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
    
    //查询所有客户信息
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
