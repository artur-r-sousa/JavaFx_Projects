package model.services;

public class ArgentinaTaxService implements TaxService {

	@Override
	public double tax(double amount) {
		double taxation = 0.015;
		amount = amount + amount * taxation;
		return amount;
	}

}
