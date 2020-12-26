package model.services;

public class PortugalTaxService implements TaxService{

	@Override
	public double tax(double amount) {
		double taxation = 0.02;
		amount = amount + amount * taxation;
		return amount;
	}
	
}
