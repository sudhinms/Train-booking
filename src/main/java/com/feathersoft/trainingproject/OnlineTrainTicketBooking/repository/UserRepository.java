package com.feathersoft.trainingproject.OnlineTrainTicketBooking.repository;

import com.feathersoft.trainingproject.OnlineTrainTicketBooking.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByName(String username);


    Optional<User> findByEmail(String email);

    User findByEmailAndPhone(String email, long phone);

    User findByEmailAndPassword(String email, String password);
}
