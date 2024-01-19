package com.feathersoft.trainingproject.OnlineTrainTicketBooking.service;


import com.feathersoft.trainingproject.OnlineTrainTicketBooking.dto.BookTrain;
import com.feathersoft.trainingproject.OnlineTrainTicketBooking.dto.Train;
import com.feathersoft.trainingproject.OnlineTrainTicketBooking.dto.User;
import com.feathersoft.trainingproject.OnlineTrainTicketBooking.exception.*;
import com.feathersoft.trainingproject.OnlineTrainTicketBooking.repository.BookingRepository;
import com.feathersoft.trainingproject.OnlineTrainTicketBooking.repository.TrainRepository;
import com.feathersoft.trainingproject.OnlineTrainTicketBooking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TrainRepository trainRepository;


    public BookTrain bookTickets(int userId, String trainNumber, int noOfTickets) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found !!!"));
        Train train = trainRepository.findByTrainNumber(trainNumber);
        if (train == null) {
            throw new TrainNotFoundException("Train is not found with the given credentials !!!!!");
        }
        if (train.getAvailableSeats() < noOfTickets) {
            throw new InsufficientSeatsException("Seats not available");
        }
        BookTrain bookTrain = new BookTrain();

        bookTrain.setUserName(user.getName());
        bookTrain.setUserEmail(user.getEmail());
        bookTrain.setUserPhone(user.getPhone());
        bookTrain.setTrainNumber(train.getTrainNumber());
        bookTrain.setTrainName(train.getTrainName());
        bookTrain.setFromLocation(train.getFromLocation());
        bookTrain.setToLocation(train.getToLocation());
        bookTrain.setStatus("Pending");
        bookTrain.setUser(user);
        bookTrain.setTrain(train);
        bookTrain.setTicketsBooked(noOfTickets);
        bookTrain.setAmountPayable(noOfTickets * train.getTicketPrice());
        train.setAvailableSeats(train.getAvailableSeats() - noOfTickets);
        bookingRepository.save(bookTrain);
        return bookTrain;
    }

    public ResponseEntity<List<BookTrain>> dispalyAllBookingsByTrain(int trainId) {
        List<BookTrain> trainList = bookingRepository.findByTrainId(trainId);
        if (trainList.isEmpty()){
            throw new TrainNotFoundException("No such train exist");
        }
        return new ResponseEntity<>(trainList,HttpStatus.FOUND);
    }

    public ResponseEntity<String> cancelBooking(int bookingId) {
        BookTrain recBookId = bookingRepository.findById(bookingId).orElseThrow(() -> new BookingIdNotFoundException("No such booking id"));
        if (recBookId.getStatus().equals("Approved")) {
            throw new InvalidStatusException("You cannot cancel the booking after booking is approved !!!!");
        }
        Train train = recBookId.getTrain();
        train.setAvailableSeats(train.getAvailableSeats() + recBookId.getTicketsBooked());
        bookingRepository.delete(recBookId);
        trainRepository.save(train);
        return new ResponseEntity<>("Booking cancelled",HttpStatus.ACCEPTED);
    }

    public List<BookTrain> displyAllBookings() {
        return  bookingRepository.findAll();
    }

    public ResponseEntity<String> acceptBooking(int bookingId) {
        BookTrain recBookId = bookingRepository.findById(bookingId).orElseThrow(() -> new BookingIdNotFoundException("No such booking id"));
        if (recBookId.getStatus().equals("Approved")||(recBookId.getStatus().equals("Rejected"))) {
            System.out.println(recBookId.getStatus());
            throw new InvalidStatusException("Already approved  or rejected");
        }
        recBookId.setStatus("Approved");
        bookingRepository.save(recBookId);
        return new ResponseEntity<>("Booking Accepted",HttpStatus.ACCEPTED);
    }

    public ResponseEntity<String> rejectBooking(int bookingId) {
        BookTrain recBookId = bookingRepository.findById(bookingId).orElseThrow(() -> new BookingIdNotFoundException("No such booking id"));
        if ((recBookId.getStatus().equals("Rejected"))||(recBookId.getStatus().equals("Approved"))){
            System.out.println(recBookId.getStatus());
            throw new InvalidStatusException("Already approved or rejected ");
        }
        recBookId.setStatus("Rejected");
        bookingRepository.save(recBookId);
        return new ResponseEntity<>("Booking Rejected",HttpStatus.ACCEPTED);
    }

    public ResponseEntity<List<BookTrain>> displayAllBookingsByUser(int userId) {
//        Optional<User> user = userRepository.findById(userId);
          List<BookTrain> bookedUser = bookingRepository.findByUserId(userId);
          if( bookedUser.isEmpty()){
             throw  new UserNotFoundException("Booking details are not available for this user");
          }
          return new ResponseEntity<>(bookedUser,HttpStatus.OK);

    }
}