package com.cinema.cinemaapp.DTO;

import java.util.List;

public class AddMovieDTO {
    private String movieName;
    private Long cinemaRoomId;
    private Integer price;
    private List<ProjectionsDTO> dates;

    public AddMovieDTO(String movieName, Long cinemaRoomId, Integer price,List<ProjectionsDTO> dates) {
        this.movieName = movieName;
        this.cinemaRoomId = cinemaRoomId;
        this.price = price;
        this.dates = dates;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public Long getCinemaRoomId() {
        return cinemaRoomId;
    }

    public void setCinemaRoomId(Long cinemaRoomId) {
        this.cinemaRoomId = cinemaRoomId;
    }

    public List<ProjectionsDTO> getDates() {
        return dates;
    }

    public void setDates(List<ProjectionsDTO> dates) {
        this.dates = dates;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
