package model.entities;

import java.util.Date;
import model.services.TaxService;

public class User{
	
	private Integer id;
	private String name;
	private Double accountBalance;
	private String email;
	private Long creditCard;
	private String country;
	private Date createdIn;
	
	private TaxService tax;
	
	public User() {
		
	}
	

	public User(Integer id, String name, Double accountBalance, String email, Long creditCard, String country, Date createdIn) {
		
		this.id = id;
		this.name = name;
		this.accountBalance = accountBalance;
		this.email = email;
		this.creditCard = creditCard;
		this.country = country;
		this.createdIn = createdIn;
	}

	public TaxService getTax() {
		return tax;
	}
	
	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public void setCreatedIn(Date createdIn) {
		this.createdIn = createdIn;
	}
	public void setAccountBalance(Double accountBalance) {
		this.accountBalance = accountBalance;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getAccountBalance() {
		return accountBalance;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(Long creditCard) {
		this.creditCard = creditCard;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Date getCreatedIn() {
		return createdIn;
	}
	
	public void setInterest(double accountNewBalance) {
		this.accountBalance += accountNewBalance;
	}
	public String toString() {
		return "User Info: " + "\n" + "Id: " + getId() + "\n" + "Name: " + getName() + "\n" + "AccountBalance: " + "$"
				+ String.format("%.2f", getAccountBalance()) + "\n" + "CC Number: " + getCreditCard() + "\n" + "Email: "
				+ getEmail() + "\n" + "Country: " + getCountry() + "\n" + "Date Account Created: " + getCreatedIn() + "\n";
	}


}
