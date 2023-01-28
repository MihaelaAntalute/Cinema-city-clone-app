package com.cinema.cinemaapp.DTO;

import java.util.List;

public class OrderDTO {

    private long projectionId;
    private List<SeatDTO> seats;

    public OrderDTO(long projectionId, List<SeatDTO> seats) {
        this.projectionId = projectionId;
        this.seats = seats;
    }

    public long getProjectionId() {
        return projectionId;
    }

    public void setProjectionId(long projectionId) {
        this.projectionId = projectionId;
    }

    public List<SeatDTO> getSeats() {
        return seats;
    }

    public void setSeats(List<SeatDTO> seats) {
        this.seats = seats;
    }
}
