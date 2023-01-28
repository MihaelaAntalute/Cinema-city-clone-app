package com.cinema.cinemaapp.DTO;

public class UpdateMovieDTO {
    private Integer price;

    public UpdateMovieDTO(Integer price) {
        this.price = price;
    }
    public UpdateMovieDTO(){}

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

}
