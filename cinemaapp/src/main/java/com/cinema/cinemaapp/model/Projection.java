package com.cinema.cinemaapp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity

public class Projection {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private LocalDateTime startTime;

    @Column
    private LocalDateTime endTime;

    @OneToMany(mappedBy = "projection", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonManagedReference(value = "projection-ticket")
    private List<Ticket> ticketList;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    @JsonBackReference(value = "movie-projection")
    private Movie movie;

    public Projection() {
    }

    public Projection(Long id, LocalDateTime startTime, LocalDateTime endTime, List<Ticket> ticketList, Movie movie) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.ticketList = ticketList;
        this.movie = movie;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public List<Ticket> getTicketList() {
        if(this.ticketList == null){
            this.ticketList = new ArrayList<>();
        }
        return ticketList;
    }

    public void setTicketList(List<Ticket> ticketList) {
        this.ticketList = ticketList;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}