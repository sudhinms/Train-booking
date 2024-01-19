package com.feathersoft.trainingproject.OnlineTrainTicketBooking.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
//@JsonIgnoreProperties({"user","train"})
public class BookTrain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Booking_Id")
    private int booking_id;

    @Column(name = "User Name")
    private String userName;

    @Column(name = "User Email")
    private String userEmail;

    @Column(name = "User Phone")
    private long userPhone;


    @Column(name = "Train Name")
    private String trainName;

    @Column(name = "Train Number")
    private String trainNumber;

    @Column(name = "From-Location")
    private String fromLocation;

    @Column(name = "To-Location")
    private String toLocation;


    @Column(name = "Tickets Booked")
    private int ticketsBooked;

    @Column(name = "Amount Payable")
    private double amountPayable;

    @Column(name = "Status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "train_id")
    private Train train;
}
