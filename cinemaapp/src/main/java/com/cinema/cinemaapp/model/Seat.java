package com.cinema.cinemaapp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;

@Entity
public class Seat {
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private Integer seatRow;
    @Column
    private Integer seatCol;
    @Column
    private Integer extraPrice;

    @ManyToOne
    @JoinColumn(name = "cinema_room_id")
    @JsonBackReference(value = "cinema-seat")
    private CinemaRoom cinemaRoom;

    @OneToMany(mappedBy = "seat", cascade = CascadeType.ALL)//todo pus in loc de merge si persist
    @JsonManagedReference(value = "seat-ticket")
   private List<Ticket> ticketList;

    public Seat(Long id, Integer seatRow, Integer col, Integer extraPrice, CinemaRoom cinemaRoom, List<Ticket> ticketList) {
        this.id = id;
        this.seatRow = seatRow;
        this.seatCol = col;
        this.extraPrice = extraPrice;
        this.cinemaRoom = cinemaRoom;
        this.ticketList = ticketList;
    }
    public Seat(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSeatRow() {
        return seatRow;
    }

    public void setSeatRow(Integer row) {
        this.seatRow = row;
    }

    public Integer getSeatCol() {
        return seatCol;
    }

    public void setSeatCol(Integer col) {
        this.seatCol = col;
    }

    public Integer getExtraPrice() {
        return extraPrice;
    }

    public void setExtraPrice(Integer extraPrice) {
        this.extraPrice = extraPrice;
    }

    public CinemaRoom getCinemaRoom() {
        return cinemaRoom;
    }

    public void setCinemaRoom(CinemaRoom cinemaRoom) {
        this.cinemaRoom = cinemaRoom;
    }

    public List<Ticket> getTicketList() {
        return ticketList;
    }

    public void setTicketList(List<Ticket> ticketList) {
        this.ticketList = ticketList;
    }
}
