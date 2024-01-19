package com.feathersoft.trainingproject.OnlineTrainTicketBooking.repository;

import com.feathersoft.trainingproject.OnlineTrainTicketBooking.dto.Train;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;


public interface TrainRepository extends JpaRepository<Train,Integer> {

    List<Train> findByDate(LocalDate date);

    Train findByTrainNumber(String trainNumber);
}
