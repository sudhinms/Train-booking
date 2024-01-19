package com.feathersoft.trainingproject.OnlineTrainTicketBooking.service;

import com.feathersoft.trainingproject.OnlineTrainTicketBooking.repository.TrainRepository;
import com.feathersoft.trainingproject.OnlineTrainTicketBooking.dto.Train;
import com.feathersoft.trainingproject.OnlineTrainTicketBooking.exception.TrainNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
public class TrainService {
    @Autowired
    private TrainRepository trainRepository;

    public ResponseEntity<String> addTrain(Train train) {
        trainRepository.save(train);
        return new ResponseEntity<>("Train details added",HttpStatus.CREATED);
    }
    public ResponseEntity<List<Train>> searchTrain(LocalDate date) {
        List<Train> trainList = trainRepository.findByDate(date);
        if (trainList.isEmpty()){
            throw new TrainNotFoundException("Trains are not  available");
        }
        return new ResponseEntity<>(trainList,HttpStatus.FOUND);
    }

    public ResponseEntity<String> updateTrainDetails(Train train, String trainNumber) {
    Train checkTrain = trainRepository.findByTrainNumber(trainNumber);
        if (checkTrain==null){
         throw new TrainNotFoundException("Train is not found with the given credentials !!!!!");
        }
        checkTrain.setTrainName(train.getTrainName());
        checkTrain.setTrainNumber(train.getTrainNumber());
        checkTrain.setFromLocation(train.getFromLocation());
        checkTrain.setToLocation(train.getToLocation());
        checkTrain.setDate(train.getDate());
        checkTrain.setTicketPrice(train.getTicketPrice());
        checkTrain.setAvailableSeats(train.getAvailableSeats());
        trainRepository.save(checkTrain);
        return new ResponseEntity<>("Train details updated successfully",HttpStatus.ACCEPTED);
    }

    public ResponseEntity<String> changeTicketPrice(Train train, String trainNumber) {
        Train checkTrain = trainRepository.findByTrainNumber(trainNumber);
        if (checkTrain == null){
            throw new TrainNotFoundException("Train is not found with the given credentials !!!!!");
        }
        checkTrain.setTicketPrice(train.getTicketPrice());
        trainRepository.save(checkTrain);
        return new ResponseEntity<>("Ticket price changed successfully",HttpStatus.ACCEPTED);

    }

    public ResponseEntity<String> checkAvailableSeats(String trainNumber) {
        Train checkTrain = trainRepository.findByTrainNumber(trainNumber);
        if (checkTrain == null){
            throw new TrainNotFoundException("Train is not found with the given credentials !!!!!");
        }
        int availableSeatDetails = checkTrain.getAvailableSeats();
        System.out.println(availableSeatDetails);
        return new ResponseEntity<>("Available seats : "+availableSeatDetails,HttpStatus.OK);
    }
}
