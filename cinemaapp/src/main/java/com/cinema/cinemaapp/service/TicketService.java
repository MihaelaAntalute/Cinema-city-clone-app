package com.cinema.cinemaapp.service;

import com.cinema.cinemaapp.model.*;
import com.cinema.cinemaapp.repository.CinemaRoomRepository;
import com.cinema.cinemaapp.repository.MovieRepository;
import com.cinema.cinemaapp.repository.ProjectionRepository;
import com.cinema.cinemaapp.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TicketService {

    private CinemaRoomRepository cinemaRoomRepository;

    private MovieRepository movieRepository;

    private ProjectionRepository projectionRepository;

    @Autowired
    public TicketService(CinemaRoomRepository cinemaRoomRepository, MovieRepository movieRepository, ProjectionRepository projectionRepository) {
        this.cinemaRoomRepository = cinemaRoomRepository;
        this.movieRepository = movieRepository;
        this.projectionRepository = projectionRepository;
    }

    public Long getValueOfTicketsSoldByDay(LocalDate certainDay) {
        List<Projection> allProjections = projectionRepository.findAll();
//        Long totalPriceOfTickets = 0L;
//        for (Projection projection : allProjections) {
//            if (projection.getStartTime().toLocalDate().equals(certainDay)) {
//                for (Ticket ticket : projection.getTicketList()) {
//                    if (!ticket.getAvailable()) {
//                        totalPriceOfTickets += ticket.getSeat().getExtraPrice() + projection.getMovie().getPrice();
//                    }
//                }
//            }
//        }
//       return totalPriceOfTickets;

        Optional<Integer> valueOfTicketsSoldByDay = computeProjectionsTotalPriceByDay(certainDay, allProjections);

        if (valueOfTicketsSoldByDay.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "price could not be computed");
        }

        return valueOfTicketsSoldByDay.get().longValue();

    }

    private Optional<Integer> computeProjectionsTotalPriceByDay(LocalDate certainDay, List<Projection> projectionList) {
        List<Ticket> soldTickets = getSoldTicketsForProjectionsByDay(certainDay, projectionList);
        return soldTickets.stream()
                .map(ticket -> computeTicketPrice(ticket))
                .reduce(Integer::sum);
    }

    private List<Ticket> getSoldTicketsForProjectionsByDay(LocalDate certainDay, List<Projection> projectionList) {
        return projectionList.stream()
                .filter(projection -> projection.getStartTime().toLocalDate().equals(certainDay))
                .flatMap(projection -> projection.getTicketList().stream())
                .filter(ticket -> !ticket.getAvailable()).collect(Collectors.toList());
    }

    private Long computeProjectionsTotalTicketsByDay(LocalDate certainDay, List<Projection> allProjections) {
        return getSoldTicketsForProjectionsByDay(certainDay, allProjections).stream().count();
    }

    private int computeTicketPrice(Ticket ticket) {
        return ticket.getSeat().getExtraPrice() + ticket.getProjection().getMovie().getPrice();
    }

    public Long getValueOfTicketsSoldByDayAndMovieName(LocalDate certainDay, String movieName) {
        Movie foundMovie = movieRepository.findByMovieName(movieName).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "The movie was not found"));
        Optional<Integer> valueOfTicketsSoldByDayAndMovieName = computeProjectionsTotalPriceByDay(certainDay, foundMovie.getProjectionList());
        if (valueOfTicketsSoldByDayAndMovieName.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "price could not be computed");
        }
        return valueOfTicketsSoldByDayAndMovieName.get().longValue();

//        Long totalPriceOfTickets = 0L;
//        for (Projection projection : foundMovie.getProjectionList()) {
//            if (projection.getStartTime().toLocalDate().equals(certainDay)) {
//                for (Ticket ticket : projection.getTicketList()) {
//                    if (!ticket.getAvailable()) {
//                        totalPriceOfTickets += ticket.getSeat().getExtraPrice() + foundMovie.getPrice();
//                    }
//                }
//            }
//        }
//        return totalPriceOfTickets;
    }

    public Integer getNumberOfTicketsSoldByDay(LocalDate certainDay) {
        List<Projection> allProjections = projectionRepository.findAll();
//        Integer numberOfTicketsSold = 0;
//        for (Projection projection : allProjections) {
//            if (projection.getStartTime().toLocalDate().equals(certainDay)) {
//                for (Ticket ticket : projection.getTicketList()) {
//                    if (!ticket.getAvailable()) {
//                        numberOfTicketsSold += 1;
//                    }
//                }
//            }
//        }
        return computeProjectionsTotalTicketsByDay(certainDay, allProjections).intValue();
    }

    public Integer getNumberOfTicketsSoldByDayAndMovieName(LocalDate certainDay, String movieName) {
        Movie foundMovie = movieRepository.findByMovieName(movieName).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "The movie was not found"));
//        Integer numberOfTicketsSold = 0;
//        for (Projection projection : foundMovie.getProjectionList()) {
//            if (projection.getStartTime().toLocalDate().equals(certainDay)) {
//                for (Ticket ticket : projection.getTicketList()) {
//                    if (!ticket.getAvailable()) {
//                        numberOfTicketsSold += 1;
//                    }
//                }
//            }
//        }
        return computeProjectionsTotalTicketsByDay(certainDay, foundMovie.getProjectionList()).intValue();
    }
}
