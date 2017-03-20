package servlets;

import java.util.ArrayList;
import java.util.List;

public class Loan {
	private double amount;
	private int noi; //(Number of installments)
	private double interest;
	private double fixedfee;
	private boolean decreasing = true;
	
	public String generateTable(List<Installment> installments, boolean isDecrementing) {
		String siteContent = "";
		siteContent = siteContent.concat("<table>"
				+ "<tr>"
				+ "<th>Nr raty</th>"
				+ "<th>Kwota kapitalu</th>"
				+ "<th>Kwota odsetek</th>"
				+ "<th>Oplaty stale</th>"
				+ "<th>Calkowita kwota raty</th>"
				+ "</tr>"
				);
		String money = "%.2f";
		int index;
		for (int i = 0; i < installments.size(); i++) {
			index = i;
			if(!isDecrementing)
				index = installments.size() - i - 1;
			siteContent = siteContent.concat(
				  "<tr>"
				+ "<td>"+ (i + 1) +"</td>"
				+ "<td>"+ String.format(money, installments.get(index).getCapital()) +" PLN</td>"
				+ "<td>"+ String.format(money, installments.get(index).getInterest()) +" PLN</td>"
				+ "<td>"+ String.format(money, installments.get(index).getFixedfee()) +" PLN</td>"
				+ "<td>"+ String.format(money, installments.get(index).getTotal()) +" PLN</td>"
				+ "</tr>"
				);
		}
		siteContent = siteContent.concat("</table>");
		
		return siteContent;
	}
	
	public List<Installment> generateInstallments() {
		List<Installment> list = new ArrayList<Installment>();
		double interest, capital = this.amount / this.noi;
		
		for (int i = 0; i < this.noi; i++) {
			Installment installment = new Installment();
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
