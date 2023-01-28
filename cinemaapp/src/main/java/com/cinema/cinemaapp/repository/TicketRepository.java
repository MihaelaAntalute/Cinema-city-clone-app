package com.cinema.cinemaapp.repository;

import com.cinema.cinemaapp.model.Projection;
import com.cinema.cinemaapp.model.Seat;
import com.cinema.cinemaapp.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket,Long> {

Ticket findByProjectionAndSeat(Projection projection, Seat seat);
}
