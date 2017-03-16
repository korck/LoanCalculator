package servlets;

import java.util.ArrayList;
import java.util.List;

public class Loan {
	private double amount;
	private int noi; //(Number of installments)
	private double interest;
	private double fixedfee;
	private boolean decreasing = true;
	//private List<Installment> installments;
	
	public List<Installment> generateInstallments() {
		List<Installment> list = new ArrayList<Installment>();
		double interest, capital = this.amount / this.noi;
		
		for (int i = 0; i < this.noi; i++) {
			Installment installment = new Installment();
			if (decreasing)
				installment.setId(i);
			else
				installment.setId(this.noi - i);
			
			installment.setCapital(capital);
			interest = 0;
			for (int j = 0; j < i; j++)
				interest += installment.getCapital();

			installment.setFixedfee(this.getFixedfee());
			installment.setInterest(
					(this.amount - interest)
					*
					(this.getInterest())
					/ 12
					);
			installment.setTotal(
					installment.getCapital() + installment.getInterest() + installment.getFixedfee()
					);
		list.add(installment);
		}
		return list;
	}
	
	public Loan(double amount, int noi, double interest, double fixedfee,
			boolean decreasing) {
		super();
		this.amount = amount;
		this.noi = noi;
		this.interest = interest;
		this.fixedfee = fixedfee;
		this.decreasing = decreasing;
	}

	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public int getNoi() {
		return noi;
	}
	public void setNoi(int noi) {
		this.noi = noi;
	}
	public double getInterest() {
		return interest;
	}
	public void setInterest(double interest) {
		this.interest = interest;
	}
	public double getFixedfee() {
		return fixedfee;
	}
	public void setFixedfee(double fixedfee) {
		this.fixedfee = fixedfee;
	}
	public boolean isDecreasing() {
		return decreasing;
	}
	public void setDecreasing(boolean decreasing) {
		this.decreasing = decreasing;
	}
}
