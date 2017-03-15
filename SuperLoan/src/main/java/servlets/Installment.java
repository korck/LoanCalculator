package servlets;

public class Installment {
	private int id;
	private double capital;
	private double interest;
	private double fixedfee;
	private double total;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getCapital() {
		return capital;
	}
	public void setCapital(double capital) {
		this.capital = capital;
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
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
}
