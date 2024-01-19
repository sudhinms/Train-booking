package com.feathersoft.trainingproject.OnlineTrainTicketBooking.repository;

import com.feathersoft.trainingproject.OnlineTrainTicketBooking.dto.BookTrain;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<BookTrain,Integer> {
    List<BookTrain> findByTrainId(int trainId);

    List<BookTrain> findByUserId(int userId);
}
