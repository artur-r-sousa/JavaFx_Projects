package model.dao;

import java.util.List;

import model.entities.User;

public interface UserDao {	
	void insert(User obj);
	void update(User obj);
	void deleteById(Integer id);
	void deposit(double deposit, Long creditCard);
	void withdraw(double withdraw, Long creditCard);
	User findById(Integer id);
	User findByCCV(Long creditCard);
	List<User> findAll();

}
