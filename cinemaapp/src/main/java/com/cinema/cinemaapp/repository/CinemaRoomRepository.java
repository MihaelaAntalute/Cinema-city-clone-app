package com.cinema.cinemaapp.repository;

import com.cinema.cinemaapp.model.CinemaRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CinemaRoomRepository extends JpaRepository<CinemaRoom,Long> {



}
