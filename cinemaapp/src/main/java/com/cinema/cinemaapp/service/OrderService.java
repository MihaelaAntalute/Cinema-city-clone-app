package com.cinema.cinemaapp.service;

        import com.cinema.cinemaapp.DTO.OrderDTO;
        import com.cinema.cinemaapp.DTO.SeatDTO;
        import com.cinema.cinemaapp.model.*;
        import com.cinema.cinemaapp.repository.*;
        import com.itextpdf.text.DocumentException;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.http.HttpStatus;
        import org.springframework.security.core.context.SecurityContextHolder;
        import org.springframework.security.core.userdetails.UserDetails;
        import org.springframework.stereotype.Service;
        import org.springframework.transaction.annotation.Transactional;
        import org.springframework.web.server.ResponseStatusException;

        import javax.mail.MessagingException;
        import java.util.Date;

@Service
@Transactional
public class OrderService {
    public static final String ORDER_MAIL_SUBJECT = "Your tickets to movie";

    private UserRepository userRepository;
    private CinemaRoomService cinemaRoomService;
    private MovieRepository movieRepository;
    private ProjectionRepository projectionRepository;
    private SeatRepository seatRepository;
    private TicketRepository ticketRepository;
    private OrderRepository orderRepository;

    private MailService mailService;

    @Autowired
    public OrderService(MailService mailService, OrderRepository orderRepository, TicketRepository ticketRepository, SeatRepository seatRepository, ProjectionRepository projectionRepository, UserRepository userRepository, CinemaRoomService cinemaRoomService, MovieRepository movieRepository) {
        this.mailService = mailService;
        this.orderRepository = orderRepository;
        this.ticketRepository = ticketRepository;
        this.seatRepository = seatRepository;
        this.projectionRepository = projectionRepository;
        this.userRepository = userRepository;
        this.cinemaRoomService = cinemaRoomService;
        this.movieRepository = movieRepository;
    }

    //1.caut filmul de la care vreau sa cumpar biletul
    //2.caut locul de la care vreau sa cumpar biletul,dupa rand si coloana
    //3.daca locul pe care il caut este available,pot cumpara biletul si il ocup
    //4.daca locul ales are extraPrice,calculam pretul + extraprice=totalPriceTicket pt locul ales
    //5.daca alegem mai multe locuri,pentru fiecare loc calculam pretultotalBilet
    // si il adaugam la pretul total al tuturor biletelor cumparate
    //6.putem returna atat pretul total,cat si numarul total de bilete cumparate
    public Order buyTicket(OrderDTO orderDTO) throws MessagingException, DocumentException {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User foundUser = userRepository.findUserByUsername(userDetails.getUsername()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found"));
        Order newOrder = new Order();
        newOrder.setCreatedDate(new Date());
        newOrder.setUser(foundUser);
        Double totalPriceOrder = 0.0;
        Projection foundProjection = projectionRepository.findById(orderDTO.getProjectionId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "the projection was not found"));
        for (SeatDTO seatDTo : orderDTO.getSeats()) {
            Seat foundSeat = seatRepository.findBySeatRowAndSeatColAndCinemaRoom(seatDTo.getRow(), seatDTo.getCol(),foundProjection.getMovie().getCinemaRoom());
            Ticket foundTicket = ticketRepository.findByProjectionAndSeat(foundProjection, foundSeat);
            if (!foundTicket.getAvailable()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "the seat at :" + foundSeat.getSeatRow() + "" + foundSeat.getSeatCol() + "" + "is not available");
            }
            totalPriceOrder += foundProjection.getMovie().getPrice() + foundSeat.getExtraPrice();
            foundTicket.setAvailable(false);
            newOrder.getTicketList().add(foundTicket);
            foundTicket.setOrder(newOrder);
        }
        newOrder.setTotalPrice(totalPriceOrder);
        mailService.sendOrderConfirmationMessage(foundUser.getEmail(), newOrder);
        return orderRepository.save(newOrder);
    }

}

