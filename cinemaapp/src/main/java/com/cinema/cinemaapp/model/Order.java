package com.cinema.cinemaapp.model;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private Date createdDate;

    @Column
    private Double totalPrice;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "order-ticket")
    private List<Ticket> ticketList;

    @ManyToOne
    @JsonBackReference(value = "user-order")
    @JoinColumn(name = "user_id")
    private User user;

    public Order() {
    }

    public Order(Long id, Date createdDate, Double totalPrice, List<Ticket> ticketList, User user) {
        this.id = id;
        this.createdDate = createdDate;
        this.totalPrice = totalPrice;
        this.ticketList = ticketList;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<Ticket> getTicketList() {
        if (this.ticketList == null) {
            this.ticketList = new ArrayList<>();
        }
        return ticketList;
    }

    public void setTicketList(List<Ticket> movieSeatList) {
        this.ticketList = movieSeatList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


}
