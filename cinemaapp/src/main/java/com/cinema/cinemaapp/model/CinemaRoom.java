package com.cinema.cinemaapp.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity

public class CinemaRoom {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private int numberOfRows;

    @Column
    private int numbersOfCols;

    @OneToMany(mappedBy = "cinemaRoom", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "cinema-movie")
    private List<Movie> movieList;

    @OneToMany(mappedBy = "cinemaRoom", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "cinema-seat")
    private List<Seat> seatList;

    public CinemaRoom() {
    }

    public CinemaRoom(Long id, int numberOfRows, int numbersOfCols, List<Movie> movieList, List<Seat> seatList) {
        this.id = id;
        this.numberOfRows = numberOfRows;
        this.numbersOfCols = numbersOfCols;
        this.movieList = movieList;
        this.seatList = seatList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Movie> getMovieList() {
        return movieList;
    }

    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
    }

    public List<Seat> getSeatList(){
        if(this.seatList == null){
            this.seatList = new ArrayList<>();
        }
        return seatList;
    }

    public void setSeatList(List<Seat> seatList) {
        this.seatList = seatList;
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public void setNumberOfRows(int numberOfRows) {
        this.numberOfRows = numberOfRows;
    }

    public int getNumbersOfCols() {
        return numbersOfCols;
    }

    public void setNumbersOfCols(int numbersOfCols) {
        this.numbersOfCols = numbersOfCols;
    }

}
