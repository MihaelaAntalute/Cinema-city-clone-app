package com.cinema.cinemaapp.service;

import com.cinema.cinemaapp.model.Order;
import com.cinema.cinemaapp.model.Ticket;
import com.itextpdf.text.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import java.util.List;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailService {


    private JavaMailSender emailSender;
    private PdfService pdfService;

    @Autowired
    public MailService(JavaMailSender emailSender, PdfService pdfService) {
        this.emailSender = emailSender;
        this.pdfService = pdfService;
    }

    public void sendOrderConfirmationMessage(
            String recepientMail, Order order) throws MessagingException, DocumentException {

        MimeMessage message = emailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom("cursjustcodeit@gmail.com");
        helper.setTo(recepientMail);

        helper.setSubject("Rezervarea ta la filmul " +order.getTicketList().get(0).getProjection().getMovie().getMovieName());
        helper.setText("Ai atasate cele " +order.getTicketList().size()+ " bilete pentru fimul " +order.getTicketList().get(0).getProjection().getMovie().getMovieName());
        helper.addAttachment("ticket.pdf", pdfService.generateTicketPdf(order));
        emailSender.send(message);

    }

//    public void sendEmail(){
//        final String fromEmail = "myemailid@gmail.com"; //requires valid gmail id
//        final String password = "mypassword"; // correct password for gmail id
//        final String toEmail = "myemail@yahoo.com"; // can be any email id
//
//        System.out.println("TLSEmail Start");
//        Properties props = new Properties();
//        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
//        props.put("mail.smtp.port", "587"); //TLS Port
//        props.put("mail.smtp.auth", "true"); //enable authentication
//        props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS
//
//        //create Authenticator object to pass in Session.getInstance argument
//        Authenticator auth = new Authenticator() {
//            //override the getPasswordAuthentication method
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication(fromEmail, password);
//            }
//        };
//        Session session = Session.getInstance(props, auth);
//
//        EmailUtil.sendEmail(session, toEmail,"TLSEmail Testing Subject", "TLSEmail Testing Body");
//    }
}
