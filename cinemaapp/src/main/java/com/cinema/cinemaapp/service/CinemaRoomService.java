package com.cinema.cinemaapp.service;

import com.cinema.cinemaapp.DTO.AddCinemaRoomDTO;
import com.cinema.cinemaapp.DTO.ExtraPriceDTO;
import com.cinema.cinemaapp.model.*;
import com.cinema.cinemaapp.repository.CinemaRoomRepository;
import com.cinema.cinemaapp.repository.MovieRepository;
import com.cinema.cinemaapp.repository.ProjectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CinemaRoomService {
    private CinemaRoomRepository cinemaRoomRepository;
    private MovieRepository movieRepository;
    private ProjectionRepository projectionRepository;

    @Autowired
    public CinemaRoomService(ProjectionRepository projectionRepository, CinemaRoomRepository cinemaRoomRepository, MovieRepository movieRepository) {
        this.projectionRepository = projectionRepository;
        this.cinemaRoomRepository = cinemaRoomRepository;
        this.movieRepository = movieRepository;
    }

    public CinemaRoom addCinemaRoom(AddCinemaRoomDTO addCinemaRoomDTO) {
        CinemaRoom cinemaRoom = new CinemaRoom();
        cinemaRoom.setNumberOfRows(addCinemaRoomDTO.getNumberOfRows());
        cinemaRoom.setNumbersOfCols(addCinemaRoomDTO.getNumberOfCols());
        generateSeatsForCinemaRoom(addCinemaRoomDTO, cinemaRoom);
        generateExtraPricesForCinemaRoom(addCinemaRoomDTO, cinemaRoom);
        return cinemaRoomRepository.save(cinemaRoom);

    }

    private void generateExtraPricesForCinemaRoom(AddCinemaRoomDTO addCinemaRoomDTO, CinemaRoom cinemaRoom) {
        //1.parcurgem lista de extraprice-uri
        //2.parcurgem randurile de la startingRow la endingRow,
        // 3.la fiecare loc de pe fiecare rand setam extraprice curent
        for (ExtraPriceDTO extraPriceDTO : addCinemaRoomDTO.getExtraPrices()) {
            for (int i = extraPriceDTO.getStartingRow(); i <= extraPriceDTO.getEndingRow(); i++) {
                for (int j = 0; j < addCinemaRoomDTO.getNumberOfCols(); j++) {
                    Seat seat = getSeatByRowAndCol(cinemaRoom, i, j + 1).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "the seat was not found"));
                    seat.setExtraPrice(extraPriceDTO.getExtraPrice());
                }
            }
        }
    }

    private void generateSeatsForCinemaRoom(AddCinemaRoomDTO addCinemaRoomDTO, CinemaRoom cinemaRoom) {
        for (int i = 0; i < addCinemaRoomDTO.getNumberOfRows(); i++) {
            for (int j = 0; j < addCinemaRoomDTO.getNumberOfCols(); j++) {
                Seat seat = new Seat();
                seat.setSeatRow(i + 1);
                seat.setSeatCol(j + 1);
                seat.setExtraPrice(0);
                cinemaRoom.getSeatList().add(seat);
                seat.setCinemaRoom(cinemaRoom);
            }
        }
    }


    public Optional<Seat> getSeatByRowAndCol(CinemaRoom cinemaRoom, Integer row, Integer col) {
//        for (Seat seat : cinemaRoom.getSeatList()) {
//            if (row == seat.getSeatRow() && col == seat.getSeatCol()) {
//                return seat;
//            }
//        }
        //      return null;
        return cinemaRoom.getSeatList().stream()
                .filter((seat -> seat.getSeatRow() == row && seat.getSeatCol() == col))
                .findFirst();
    }

    public CinemaRoom updateCinemaRoom(AddCinemaRoomDTO addCinemaRoomDTO, Long cinemaRoomId) {
        CinemaRoom foundCinemaRoom = cinemaRoomRepository.findById(cinemaRoomId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "the cinema was not found"));
        foundCinemaRoom.setNumberOfRows(addCinemaRoomDTO.getNumberOfRows());
        foundCinemaRoom.setNumbersOfCols(addCinemaRoomDTO.getNumberOfCols());
        return cinemaRoomRepository.save(foundCinemaRoom);
    }

    public List<CinemaRoom> getCinemaRooms() {
        return cinemaRoomRepository.findAll();
    }

    public void deleteCinemaRoom(Long cinemaRoomId) {
        CinemaRoom foundCinemaRoom = cinemaRoomRepository.findById(cinemaRoomId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "the cinema was not found"));
        cinemaRoomRepository.delete(foundCinemaRoom);
    }
    //Care este valoarea tuturor biletelor vandute intr-o anumita zi (la un film sau la toate filmele)
    ///ticket/totalprice/


    //1.caut ca data care o dau sa fie egala cu data zilei in care s-a vandut biletul
    //2.caut filmul la care vreu sa aflu cate bilete totale s-au vandut
    //3.fac un contor unde sa adaug valoarea biletului gasit
    //4.parcutg lista de proiectii a filmului si de acolo iau biletele care apartine unui order
    //5.iau valoarea biletelor dar si extraPrice-ul si o adaug in contor
    public Double getValueOfAllTicketsSoldByMovieAndDate(String movieName, LocalDate date) {
        Movie foundMovie = movieRepository.findByMovieName(movieName).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "movie was not found"));
        Optional<Integer> totalValueOfTickets = computeProjectionsTotalPriceByDay(date, foundMovie.getProjectionList());
        if (totalValueOfTickets.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "price could not be compute");
        }
        return totalValueOfTickets.get().doubleValue();
    }

    public Double getValueOfTicketsSoldFromAllMoviesByDay(LocalDate date) {
        List<Projection> allProjectionsList = projectionRepository.findAll();
        Optional<Integer> totalValueOfTickets = computeProjectionsTotalPriceByDay(date, allProjectionsList);
        if (totalValueOfTickets.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "price could not be compute");
        }
        return totalValueOfTickets.get().doubleValue();
    }

    private Optional<Integer> computeProjectionsTotalPriceByDay(LocalDate date, List<Projection> projectionList) {
        Optional<Integer> totalValueOfTickets = projectionList.stream()
                .filter(projection -> projection.getStartTime().toLocalDate().equals(date))
                .flatMap(projection -> projection.getTicketList().stream())
                .filter(ticket -> !ticket.getAvailable())
                .map(ticket -> ticket.getSeat().getExtraPrice() + ticket.getProjection().getMovie().getPrice())
                .reduce(Integer::sum);
        return totalValueOfTickets;
    }

    //Cate bilete s-au vandut la un anumit film sau la toate filmele
    public Integer getNumberOfAllTicketsSoldByMovie(String movieName) {
       Integer totalTicketsNumber = 0;
        Movie foundMovie = movieRepository.findByMovieName(movieName).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "movie was not found"));
        for (Projection projection : foundMovie.getProjectionList()) {
            for (Ticket ticket : projection.getTicketList()) {
                if (ticket.getAvailable().equals(false)) {
                    totalTicketsNumber += 1;
                }
            }
        }
        return totalTicketsNumber;
        //todo metode
    }
    public Integer getNumberOfAllTicketsSoldByMovie2(Long movieId) {
        Integer totalTicketsNumber = 0;
        Movie foundMovie = movieRepository.findById(movieId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "movie was not found"));
        for (Projection projection : foundMovie.getProjectionList()) {
            for (Ticket ticket : projection.getTicketList()) {
                if (ticket.getAvailable().equals(false)) {
                    totalTicketsNumber += 1;
                }
            }
        }
        return totalTicketsNumber;
        //todo metode
    }
    public Integer getNumbersOfAllTicketsSoldByCinemaRoom(Long cinemaRoomId) {
        Integer totalNumbersOfTicketsSold = 0;
        CinemaRoom foundCinemaRoom = cinemaRoomRepository.findById(cinemaRoomId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "the cinema was not found"));
        for (Movie movie : foundCinemaRoom.getMovieList()) {
            for (Projection projection : movie.getProjectionList()) {
                for (Ticket ticket : projection.getTicketList()) {
                    if (ticket.getAvailable().equals(false)) {
                        totalNumbersOfTicketsSold += 1;
                    }
                }
            }
        }
        return totalNumbersOfTicketsSold;
    }


}