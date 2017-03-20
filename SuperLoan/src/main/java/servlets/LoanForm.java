package servlets;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@WebServlet("/loan")
public class LoanForm extends HttpServlet {

	private static final long serialVersionUID = 1L;

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
		
		if (request.getParameter("submit").equals("pdfgen")) {
			Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
			Document document = new Document();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			try {
				PdfWriter.getInstance(document, baos);
			} catch (DocumentException e) {
				e.printStackTrace();
			}
			document.open();
			
			PdfPTable table = new PdfPTable(5);
			Anchor anchor = new Anchor("", catFont);
			Chapter catPart = new Chapter(new Paragraph(anchor), 1);
			Section subCatPart = catPart.addSection("");
			
			String[] cellHeaders = {"Nr raty", "Kwota kapitalu", "Kwota odsetek", "Oplaty stale", "Calkowita kwota raty"};
			
			for (int i=0; i < 5; i++) {
				PdfPCell cell = new PdfPCell(new Phrase(cellHeaders[i]));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
			}
			
			table.setHeaderRows(1);
			for (int i = 0; i < installments.size(); i++) {
				table.addCell(Integer.toString(i+1));
				table.addCell(Double.toString(installments.get(i).getCapital()));
				table.addCell(Double.toString(installments.get(i).getInterest()));
				table.addCell(Double.toString(installments.get(i).getFixedfee()));
				table.addCell(Double.toString(installments.get(i).getTotal()));
			}
			subCatPart.add(table);
		}
		else response.getWriter().print(loan.generateTable(installments, isDecrementing));
		
	}

}
