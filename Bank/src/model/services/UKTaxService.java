package model.services;

public class UKTaxService implements TaxService{

	@Override
	public double tax(double amount) {
		double taxation = 0.012;
		amount = amount + amount * taxation;
		return amount;
	}

}
