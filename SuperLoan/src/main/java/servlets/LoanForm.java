package servlets;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/loan")
public class LoanForm extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.getWriter().println("<h1>Witaj pożyczko</h1>");
		
		boolean isDecrementing;
		if (request.getParameter("type").equals("dec"))
			isDecrementing = true;
		else isDecrementing = false;
		
		Loan loan = new Loan(
				Double.parseDouble(request.getParameter("amount")),
				Integer.parseInt(request.getParameter("noi")),
				Double.parseDouble(request.getParameter("interest")),
				Double.parseDouble(request.getParameter("fixedfee")),
				isDecrementing
				);
		response.getWriter().println("<br>" + loan.getAmount() + loan.getNoi());
	}
}
