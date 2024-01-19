package com.feathersoft.trainingproject.OnlineTrainTicketBooking.controller;


import com.feathersoft.trainingproject.OnlineTrainTicketBooking.dto.BookTrain;
import com.feathersoft.trainingproject.OnlineTrainTicketBooking.service.BookingService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/booking")
public class BookingContoller {
    @Autowired
    private BookingService bookingService;

    @PostMapping("/book-ticket")
    public BookTrain bookTicket(@RequestParam(name = "userId") int userId,@RequestParam(name = "trainNumber") String trainNumber,
                                @RequestParam(name = "noOfTickets") int noOfTickets){
        return bookingService.bookTickets(userId,trainNumber,noOfTickets);
    }

    @GetMapping("/display-all-bookings")
    public List<BookTrain> displayAllBooking(){
        return bookingService.displyAllBookings();
    }
    @GetMapping("/display-all-booking-by-train/{trainId}")
    public ResponseEntity<List<BookTrain>> displayAllBookingsByTrain(@PathVariable int trainId){
        return bookingService.dispalyAllBookingsByTrain(trainId);
    }

    //display booking based on user id
    @GetMapping("/display-all-booking-by-user/{userId}")
    public ResponseEntity<List<BookTrain>> displayAllBookingdByUser(@PathVariable int userId){
        return bookingService.displayAllBookingsByUser(userId);
    }

    //user and admin can can the booking
    @DeleteMapping("/cancel-booking/{bookingId}")
    public ResponseEntity<String> cancelBooking(@PathVariable int bookingId){
        return bookingService.cancelBooking(bookingId);
    }
    @PutMapping("/accept-booking/{bookingId}")
    public ResponseEntity<String> acceptBooking(@PathVariable int bookingId){
        return bookingService.acceptBooking(bookingId);
    }
    // reject the booking by admin
    @PutMapping("/reject-booking/{bookingId}")
    public ResponseEntity<String> rejectBooking(@PathVariable int bookingId){
        return bookingService.rejectBooking(bookingId);
    }

}
