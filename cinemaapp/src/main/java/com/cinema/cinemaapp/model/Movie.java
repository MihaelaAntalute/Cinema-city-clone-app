package com.cinema.cinemaapp.model;

import com.cinema.cinemaapp.config.StringListConverter;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Movie {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String movieName;

    @Column
    private Integer price;

    @Column
    private String overview;

    @Column
    private String language;

    @Column
    private LocalDate releaseDate;

    @Column
    private Double voteAverage;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL) //todo pus in loc de merge si persist
    @JsonManagedReference(value = "movie-projection")
    private List<Projection> projectionList;

    @ManyToOne
    @JoinColumn(name = "cinema_room_id")
    @JsonBackReference(value = "cinema-movie")
    private CinemaRoom cinemaRoom;

    @Convert(converter = StringListConverter.class)
    private List<String> genres;

    public Movie() {
    }

    public Movie(Long id, String movieName, Integer price, String overview, String language, LocalDate releaseDate, Double voteAverage, List<Projection> projectionList, CinemaRoom cinemaRoom) {
        this.id = id;
        this.movieName = movieName;
        this.price = price;
        this.overview = overview;
        this.language = language;
        this.releaseDate = releaseDate;
        this.voteAverage = voteAverage;
        this.projectionList = projectionList;
        this.cinemaRoom = cinemaRoom;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public List<Projection> getProjectionList() {
        if (this.projectionList == null) {
            this.projectionList = new ArrayList<>();
        }
        return projectionList;
    }

    public void setProjectionList(List<Projection> projectionList) {
        this.projectionList = projectionList;
    }

    public CinemaRoom getCinemaRoom() {
        return cinemaRoom;
    }

    public void setCinemaRoom(CinemaRoom cinemaRoom) {
        this.cinemaRoom = cinemaRoom;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
