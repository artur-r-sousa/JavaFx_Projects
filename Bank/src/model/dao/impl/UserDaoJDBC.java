package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.UserDao;
import model.entities.User;

public class UserDaoJDBC implements UserDao {
	
	private Connection conn;
	
	public UserDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(User obj) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO user " + 
					"(Name, AccountBalance, Email, creditcard, Country, createdin) "
					+ "VALUES (?, ?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS
					);
			
			st.setString(1, obj.getName());
			st.setDouble(2, obj.getAccountBalance());
			st.setString(3, obj.getEmail());
			st.setLong(4, obj.getCreditCard());
			st.setString(5, obj.getCountry());
			st.setDate(6, new java.sql.Date(obj.getCreatedIn().getTime()));
			
			int affectedRows = st.executeUpdate();
			
			if (affectedRows > 0) {
				rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
			}
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public void update(User obj) {

		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE user " + 
					"SET Name = ?, accountBalance = ?, email = ?, creditCard = ?, country = ?, createdIn = ? "
					+"WHERE " + "(idUser=?)");

			st.setString(1, obj.getName());
			st.setDouble(2, obj.getAccountBalance());
			st.setString(3, obj.getEmail());
			st.setLong(4, obj.getCreditCard());
			st.setString(5, obj.getCountry());
			st.setDate(6, new java.sql.Date(obj.getCreatedIn().getTime()));
			st.setInt(7, obj.getId());

			int rowsAffected = st.executeUpdate();

			if (rowsAffected > 0) {
				System.out.println("Done, rows affected: " + rowsAffected);
			}

		} catch(SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(st);

		}
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;

		try {

			st = conn.prepareStatement(
					"DELETE FROM user "
					+ "WHERE "
					+ "(idUser=?)");
			
			st.setInt(1, id);

			int rowsAffected = st.executeUpdate();
			
			if (rowsAffected > 0) {
				System.out.println("Done, rows affected: " + rowsAffected);
			}
			
		}catch (SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(st);
		}
		
	}
	private User instantiateUser(ResultSet rs) throws SQLException {
		User obj = new User();
		obj.setId(rs.getInt("idUser"));
		obj.setName(rs.getString("Name"));
		obj.setEmail(rs.getString("Email"));
		obj.setAccountBalance(rs.getDouble("accountBalance"));
		obj.setCreatedIn(rs.getDate("createdIn"));
		obj.setCountry(rs.getString("country"));
		obj.setCreditCard(rs.getLong("creditCard"));
			
		return obj;
	}

	@Override
	public User findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {

			st = conn.prepareStatement("SELECT * FROM user " + "WHERE idUser = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			User obj = new User();
			if (rs.next()) {
				obj = instantiateUser(rs);
				
			}
			return obj;

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}


	@Override
	public List<User> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
	
		try {
			conn = DB.getConnection();
			st = conn.prepareStatement(
					"SELECT * FROM user");

			rs = st.executeQuery();
			List<User> userList = new ArrayList<>();
			Map<Integer, User> map = new HashMap<>();
			while(rs.next()) {
				User user = map.get(rs.getInt("IdUser"));
				if (user == null) {
					user = instantiateUser(rs);
				}
				userList.add(user);			
			}
			return userList;
		}catch (SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		} 

	}
	
	@Override
	public void withdraw(double withdraw, Long creditcardToUpdate) {
		PreparedStatement st = null;
		try {
			conn = DB.getConnection();
			
			st = conn.prepareStatement(
					"UPDATE user "
					+ "SET AccountBalance = AccountBalance - ? "
					+ "WHERE "
					+ "(creditcard=?)");
			
			st.setDouble(1, withdraw);
			st.setLong(2, creditcardToUpdate);
			
			
			if (withdraw <= findByCCV(creditcardToUpdate).getAccountBalance()) {
				int rowsAffected = st.executeUpdate();
				if (rowsAffected > 0) {
					System.out.println("Withdraw Sucessfull!");
				}
			} else {
				System.out.println("Insufficient funds for this Transaction");
			}
			
			
		}catch (SQLException e) {
			e.printStackTrace(); 
		} 
	}
	
	@Override
	public void deposit(double deposit, Long creditcardToUpdate) {
		Connection conn =  null;
		PreparedStatement st = null;
		try {
			conn = DB.getConnection();
			
			st = conn.prepareStatement(
					"UPDATE user "
					+ "SET AccountBalance = AccountBalance + ? "
					+ "WHERE "
					+ "(creditcard=?)");
			
			st.setDouble(1, deposit);
			st.setLong(2, creditcardToUpdate);
			

			int rowsAffected = st.executeUpdate();
			if (rowsAffected > 0) {
				System.out.println("Deposit Sucessfull!");
			}
			
		}catch (SQLException e) {
			e.printStackTrace(); 
		} 
	}

	@Override
	public User findByCCV(Long creditCard) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {

			st = conn.prepareStatement("SELECT * FROM user " + "WHERE creditCard = ?");
			st.setLong(1, creditCard);
			rs = st.executeQuery();

			if (rs.next()) {
				User obj = instantiateUser(rs);
				return obj;
			}
			return null;

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
	
	

}
