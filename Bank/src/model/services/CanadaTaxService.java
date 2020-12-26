package model.services;

public class CanadaTaxService implements TaxService{

	@Override
	public double tax(double amount) {
		double taxation = 0.01;
		amount = amount + amount * taxation;
		return amount;
	}
	
}
