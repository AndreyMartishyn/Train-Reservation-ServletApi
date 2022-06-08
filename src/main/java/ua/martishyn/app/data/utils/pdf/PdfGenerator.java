package ua.martishyn.app.data.utils.pdf;


import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import ua.martishyn.app.data.dao.impl.TicketDaoImpl;
import ua.martishyn.app.data.dao.interfaces.TicketDao;
import ua.martishyn.app.data.entities.Ticket;
import ua.martishyn.app.data.service.TicketService;
import ua.martishyn.app.data.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet("/download")
public class PdfGenerator extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TicketDao ticketDao = new TicketDaoImpl();
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/pdf");

        response.setHeader(
                "Content-disposition",
                "inline; filename='Downloaded.pdf'");

        try {

            Document document = new Document();

            PdfWriter.getInstance(
                    document, response.getOutputStream());

            final Optional<List<Ticket>> allTickets = ticketDao.getAllTickets();
            final Ticket ticket =allTickets.get().get(0);
            String id = String.valueOf(ticket.getId());
            String trainId = String.valueOf(ticket.getTrain().getId());
            String departureStation = ticket.getDepartureStation().getName();
            String arrivalStation = ticket.getArrivalStation().getName();


            document.open();

            document.add(new Paragraph(
                    "This is a demo to write data to pdf\n using servlet\nThank You"));

            PdfPTable table = new PdfPTable(4);
            PdfPCell c1  = new PdfPCell(new Phrase("Ticket#"));
            table.addCell(c1);

            c1  = new PdfPCell(new Phrase("Train#"));
            table.addCell(c1);

            c1  = new PdfPCell(new Phrase("Departure"));
            table.addCell(c1);

            c1  = new PdfPCell(new Phrase("Arrival"));
            table.addCell(c1);
            table.setHeaderRows(1);

            table.addCell(id);
            table.addCell(trainId);
            table.addCell(departureStation);
            table.addCell(arrivalStation);




            document.add(table);
            document.close();
        } catch (DocumentException de) {
            throw new IOException(de.getMessage());
        }
    }
}

