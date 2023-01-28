package com.cinema.cinemaapp.DTO;

public class ExtraPriceDTO {

    private Integer startingRow;
    private Integer endingRow;
    private Integer extraPrice;

    public ExtraPriceDTO(Integer startingRow, Integer endingRow, Integer extraPrice) {
        this.startingRow = startingRow;
        this.endingRow = endingRow;
        this.extraPrice = extraPrice;
    }

    public Integer getStartingRow() {
        return startingRow;
    }

    public void setStartingRow(Integer startingRow) {
        this.startingRow = startingRow;
    }

    public Integer getEndingRow() {
        return endingRow;
    }

    public void setEndingRow(Integer endingRow) {
        this.endingRow = endingRow;
    }

    public Integer getExtraPrice() {
        return extraPrice;
    }

    public void setExtraPrice(Integer extraPrice) {
        this.extraPrice = extraPrice;
    }
}
