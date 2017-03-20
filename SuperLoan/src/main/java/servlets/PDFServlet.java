package servlets;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@WebServlet("/pdf")
public class PDFServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 
		
	Document document = new Document();
	PdfPTable table = new PdfPTable(5);
	
	ByteArrayOutputStream baos = new ByteArrayOutputStream();
	
	try {
		PdfWriter.getInstance(document, baos);
	} catch (DocumentException e) {
		e.printStackTrace();
	}
	document.open();
	
	
	
	}
}
