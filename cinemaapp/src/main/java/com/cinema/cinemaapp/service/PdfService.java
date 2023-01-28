package com.cinema.cinemaapp.service;

import com.cinema.cinemaapp.model.Order;
import com.cinema.cinemaapp.model.Ticket;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import javax.activation.DataSource;
import javax.mail.util.ByteArrayDataSource;
import java.io.ByteArrayOutputStream;
import java.util.stream.Stream;

@Service
public class PdfService {


    public DataSource generateTicketPdf(Order order) throws DocumentException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document,  outputStream);

        document.open();
        Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);

        PdfPTable table = new PdfPTable(3);
        addTableHeader(table);

        for(Ticket ticket : order.getTicketList()){
            addRow(table, ticket);
        }
        Chunk chunk = new Chunk("Pret total: "+order.getTotalPrice(), font);

        document.add(table);
        document.add(chunk);
        document.close();

        byte[] bytes = outputStream.toByteArray();
        //construct the pdf body part
        return new ByteArrayDataSource(bytes, "application/pdf");
    }


    private void addTableHeader(PdfPTable table) {
        Stream.of("Rand", "Coloana", "Pret")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }

    private void addRow(PdfPTable table, Ticket ticket) {
        table.addCell(ticket.getSeat().getSeatRow().toString());
        table.addCell(ticket.getSeat().getSeatCol().toString());
        table.addCell(String.valueOf(ticket.getProjection().getMovie().getPrice() + ticket.getSeat().getExtraPrice()));
    }
}
