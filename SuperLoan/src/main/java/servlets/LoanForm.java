package servlets;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.Anchor;
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
		
		if (request.getParameter("generate").equals("Wygeneruj PDF")) {
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
			String money = "%.2f";
			for (int i = 0; i < installments.size(); i++) {
				table.addCell(Integer.toString(i+1));
				table.addCell(String.format(money, Double.toString(installments.get(i).getCapital())));
				table.addCell(String.format(money, Double.toString(installments.get(i).getInterest())));
				table.addCell(String.format(money, Double.toString(installments.get(i).getFixedfee())));
				table.addCell(String.format(money, Double.toString(installments.get(i).getTotal())));
			}
			subCatPart.add(table);
			try {
				document.add(subCatPart);
			} catch (DocumentException e) {
				e.printStackTrace();
			}
			document.close();
			
			response.setHeader("Expires", "0");
			response.setHeader("Cache-control", "must-revalidate, post-check=0, pre-check=0");
			response.setHeader("Pragma", "public");
			response.setContentType("aplication/pdf");
			response.setContentLength(baos.size());
			ServletOutputStream os = response.getOutputStream();
			baos.writeTo(os);
			os.flush();
			os.close();
		}
		
		else {
			response.getWriter().println("<h1>Harmonogram splaty kredytu</h1>");
			response.getWriter().print(loan.generateTable(installments, isDecrementing));
		}
	}

}
