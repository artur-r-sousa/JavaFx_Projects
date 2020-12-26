package model.entities;

import java.util.Date;

import db.DB;
import model.dao.DaoFactory;
import model.dao.UserDao;

public class Operations {
	
	private User user;
	private Date currentDate;
	

	
	public Operations() {
		
	}
	
	public Operations(User user,  Date currentDate) {
		super();
		this.user = user;
		this.currentDate = currentDate;
	}
	
	DB db = new DB();

	public User getUser() {
		return user;
	}
	
	public double getInterest(User user) {
		switch (user.getCountry()) {
			case("Argentina"):
				return 0.01;

			case("Canada"):
				return 0.012;

			case("New Zealand"):
				return 0.013;
				
			case("Portugal"):
				return 0.014;
	
			case("United Kingdom"):
				return 0.015;
			
			default:
				return 1;
		
		}				
	}
	
	
	public Date getCurrentDate() {
		return currentDate;
	}

	public void simpleInterest(User user, Date date) {
		UserDao userDao = DaoFactory.createUserDao();
		double increase = getInterest(user);
		Date startDate = user.getCreatedIn();
		Long dif = (date.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24 );
		if (dif > 0) {
			double multi = Double.parseDouble(String.valueOf(dif)) / 30;	
			user.setInterest(user.getAccountBalance() * (multi * increase));
			userDao.update(user);
		}
		
	}

}
