package com.cinema.cinemaapp;

import com.cinema.cinemaapp.DTO.AddCinemaRoomDTO;
import com.cinema.cinemaapp.DTO.AddMovieDTO;
import com.cinema.cinemaapp.DTO.ExtraPriceDTO;
import com.cinema.cinemaapp.model.CinemaRoom;
import com.cinema.cinemaapp.model.Movie;
import com.cinema.cinemaapp.repository.CinemaRoomRepository;
import com.cinema.cinemaapp.repository.MovieRepository;
import com.cinema.cinemaapp.service.MovieService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MovieServiceTest {
    @InjectMocks
    private MovieService movieService;

    @Mock
    private MovieRepository movieRepository;
    @Mock
    private CinemaRoomRepository cinemaRoomRepository;


    @Test
    void testAddMovie_ShouldTrowExceptoin(){
        //given

        AddMovieDTO addMovieDTO = new AddMovieDTO("Ajunul Craciunului",null,0,null);

        //when
        when(cinemaRoomRepository.findById(any())).thenReturn(Optional.of(new CinemaRoom()));
        when(movieRepository.findByMovieName(addMovieDTO.getMovieName())).thenReturn(Optional.of(new Movie(null,addMovieDTO.getMovieName(),0,null,null,null,null,null,null)));

        assertThrows(ResponseStatusException.class, () -> movieService.addMovie(addMovieDTO));
    }
}
