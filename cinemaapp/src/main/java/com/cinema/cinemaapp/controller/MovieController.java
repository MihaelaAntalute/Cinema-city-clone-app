package com.cinema.cinemaapp.controller;

import com.cinema.cinemaapp.DTO.AddMovieDTO;
import com.cinema.cinemaapp.DTO.UpdateMovieDTO;
import com.cinema.cinemaapp.model.Movie;
import com.cinema.cinemaapp.model.Projection;
import com.cinema.cinemaapp.model.Seat;
import com.cinema.cinemaapp.repository.MovieRepository;
import com.cinema.cinemaapp.service.MovieService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movie")
public class MovieController {
    private MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping("/add")
    public Movie addMovie(@RequestBody AddMovieDTO addMovieDTO) throws JsonProcessingException {
        return movieService.addMovie(addMovieDTO);
    }

    @GetMapping("/projections-available/{movieId}")
    public List<Projection> getAllProjectionsAvailable(@PathVariable Long movieId) {
        return movieService.getAllProjectionsAvailable(movieId);
    }

    @PutMapping("/update/{movieId}")
    public Movie updateMovie(@RequestBody UpdateMovieDTO updateMovieDTO, @PathVariable Long movieId) {
        return movieService.updateMovie(updateMovieDTO, movieId);
    }

    @DeleteMapping("/delete/{movieId}")
    public void deleteMovie(@PathVariable Long movieId) {
        movieService.deleteMovie(movieId);
    }

}
