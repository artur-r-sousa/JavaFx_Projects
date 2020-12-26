package model.services;

public class NZTaxService implements TaxService{

	@Override
	public double tax(double amount) {
		double taxation = 0.018;
		amount = amount + amount * taxation;
		return amount;
	}

}
