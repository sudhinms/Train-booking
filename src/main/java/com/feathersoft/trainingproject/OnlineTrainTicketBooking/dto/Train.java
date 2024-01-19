package com.feathersoft.trainingproject.OnlineTrainTicketBooking.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.NotNull;
import lombok.Data;


import java.time.LocalDate;

import java.time.LocalTime;
import java.util.List;


@Entity
@Data
@Table(name = "Train")
public class Train {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private int id;

    @NotBlank
    @NotNull
    @Column(name = "Train Name",unique = true)
    private String trainName;

    @NotBlank
    @NotNull
    @Column(name = "Train Number",unique = true)
    private String trainNumber;

    @NotBlank
    @NotNull
    @Column(name = "From-Location")
    private String fromLocation;

    @NotBlank
    @NotNull
    @Column(name = "To-Location")
    private String toLocation;

    @NotNull
    @Future
    @Column(name = "Date")
    private LocalDate date;

    @NotNull
    @JsonFormat( pattern = "HH:mm")
    @Column(name = "Time")
    private LocalTime time;

    @NotNull
    @Column(name = "Price")
    private double ticketPrice;

    @NotNull
    @Column(name = "Seats Available")
    private int availableSeats;

//    @OneToMany(mappedBy = "train")
//    private List<BookTrain>  bookingDetails;

}
