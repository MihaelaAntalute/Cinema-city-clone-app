package com.cinema.cinemaapp.controller;


import com.cinema.cinemaapp.DTO.AddCinemaRoomDTO;
import com.cinema.cinemaapp.model.CinemaRoom;
import com.cinema.cinemaapp.model.Movie;
import com.cinema.cinemaapp.service.CinemaRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/cinema")
public class CinemaController {
    private CinemaRoomService cinemaRoomService;

    @Autowired
    public CinemaController(CinemaRoomService cinemaRoomService) {
        this.cinemaRoomService = cinemaRoomService;
    }

    @PostMapping("/add")
    public CinemaRoom addCinemaRoom(@RequestBody AddCinemaRoomDTO addCinemaRoomDTO) {
        return cinemaRoomService.addCinemaRoom(addCinemaRoomDTO);
    }

    @GetMapping("/")
    public List<CinemaRoom> getCinemaRooms() {
        return cinemaRoomService.getCinemaRooms();
    }

    @PutMapping("/update/{cinemaRoomId}")
    public CinemaRoom updateCinemaRoom(@RequestBody AddCinemaRoomDTO addCinemaRoomDTO, @PathVariable Long cinemaRoomId) {
        return cinemaRoomService.updateCinemaRoom(addCinemaRoomDTO, cinemaRoomId);
    }

    @GetMapping("/getValueOffAllTicketsSoldByMovieAndDate/date")
    public Double getValueOfAllTicketsSoldByMovieAndDate(@RequestBody String moviename, @RequestBody LocalDate date) {
        return cinemaRoomService.getValueOfAllTicketsSoldByMovieAndDate(moviename, date);
    }

    @GetMapping("/getValueOfTicketsSoldFromAllMoviesByDay/{cinemaRoomId}/date")
    public Double getValueOfTicketsSoldFromAllMoviesByDay(@RequestBody LocalDate date) {
        return cinemaRoomService.getValueOfTicketsSoldFromAllMoviesByDay(date);
    }

    @GetMapping("/getNumberOfAllTicketsSoldByMovie/{movieId}")
    public Integer getNumberOfAllTicketsSoldByMovie(@PathVariable Long movieId) {
        return cinemaRoomService.getNumberOfAllTicketsSoldByMovie2(movieId);
    }

    @GetMapping("/getNumbersOfAllTicketsSoldByCinemaRoom/{cinemaRoomId}")
    public Integer getNumbersOfAllTicketsSoldByCinemaRoom(@PathVariable Long cinemaRoomId){
        return cinemaRoomService.getNumbersOfAllTicketsSoldByCinemaRoom(cinemaRoomId);
    }

    @DeleteMapping("/delete/{cinemaRoomId}")
    public void deleteCinemaRoom(@PathVariable Long cinemaRoomId) {
        cinemaRoomService.deleteCinemaRoom(cinemaRoomId);
    }


}
