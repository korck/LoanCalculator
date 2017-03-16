package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/loan")
public class LoanForm extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
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
	/*
	public void generatePDF() {
		
		try (final PDDocument document = new PDDocument()) {
			final PDPage emptyPage = new PDPage();
			document.addPage(emptyPage);
		}
		
	}*/

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.getWriter().println("<h1>Harmonogram splaty kredytu</h1>");
		
		boolean isDecrementing;
		if (request.getParameter("type").equals("dec"))
			isDecrementing = true;
		else isDecrementing = false;
		
		Loan loan;
		try {
			loan = new Loan(
					Double.parseDouble(request.getParameter("amount")),
					Integer.parseInt(request.getParameter("noi")),
					Double.parseDouble(request.getParameter("interest"))/100,
					Double.parseDouble(request.getParameter("fixedfee")),
					isDecrementing
					);
		} catch(NumberFormatException n) {
			response.getWriter().println("<h1>Blad!</h1>"
					+ "W formularzu uzyto niewlasciwych znakow");
			return;
		}
		List<Installment> installments = loan.generateInstallments();
		
		response.getWriter().print(generateTable(installments, isDecrementing));
		
	}
}
