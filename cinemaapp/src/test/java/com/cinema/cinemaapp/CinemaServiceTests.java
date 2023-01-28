package com.cinema.cinemaapp;

import com.cinema.cinemaapp.DTO.AddCinemaRoomDTO;
import com.cinema.cinemaapp.DTO.ExtraPriceDTO;
import com.cinema.cinemaapp.model.CinemaRoom;
import com.cinema.cinemaapp.repository.CinemaRoomRepository;
import com.cinema.cinemaapp.repository.MovieRepository;
import com.cinema.cinemaapp.service.CinemaRoomService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
@ExtendWith({MockitoExtension.class})

class CinemaServiceTests {

	@InjectMocks
	private CinemaRoomService cinemaRoomService;

	@Mock
	private CinemaRoomRepository cinemaRoomRepository;

	@Mock
	private MovieRepository movieRepository;
	@Test
	void contextLoads() {
	}



	@Test
	void testAddCinemaRoom() {
		//given

		ExtraPriceDTO extraPriceDTO = new ExtraPriceDTO(4,6,3);
		AddCinemaRoomDTO addCinemaRoomDTO = new AddCinemaRoomDTO(8,9, Arrays.asList(extraPriceDTO));

		//when
		CinemaRoom cinemaRoomRepositoryObject = new CinemaRoom(1L, 8,9,null,null);
		when(cinemaRoomRepository.save(any())).thenReturn(cinemaRoomRepositoryObject);

		CinemaRoom result = cinemaRoomService.addCinemaRoom(addCinemaRoomDTO);
		//then
		assertEquals(8, result.getNumberOfRows());
	}




}
