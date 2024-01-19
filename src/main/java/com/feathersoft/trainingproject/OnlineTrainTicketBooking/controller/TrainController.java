package com.feathersoft.trainingproject.OnlineTrainTicketBooking.controller;

import com.feathersoft.trainingproject.OnlineTrainTicketBooking.dto.Train;
import com.feathersoft.trainingproject.OnlineTrainTicketBooking.service.TrainService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/train")
public class TrainController {
    @Autowired
    private TrainService trainService;

    @PostMapping("/add-train")
    public ResponseEntity<String > addTrain(@Valid @RequestBody Train train){
        return trainService.addTrain(train);
    }
    @GetMapping("/search-train/{date}")
    public ResponseEntity<List<Train>> searchTrain(@PathVariable LocalDate date){
        return trainService.searchTrain(date);
    }
    @PutMapping("/update-train-details/{trainNumber}")
    public ResponseEntity<String> updateTrainDetails(@RequestBody Train train ,@PathVariable String trainNumber){
        return trainService.updateTrainDetails(train,trainNumber);
    }


    @PutMapping("/change-ticket-price/{trainNumber}")
    public ResponseEntity<String> changeTicketPrice(@RequestBody Train train ,@PathVariable String trainNumber){
        return trainService.changeTicketPrice(train,trainNumber);
    }
    @GetMapping("/check-available-seats/{trainNumber}")
    public ResponseEntity<String> checkAvailableSeats(@PathVariable String trainNumber){
        return trainService.checkAvailableSeats(trainNumber);

    }

}
