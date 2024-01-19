package com.feathersoft.trainingproject.OnlineTrainTicketBooking.service;

import com.feathersoft.trainingproject.OnlineTrainTicketBooking.repository.UserRepository;
import com.feathersoft.trainingproject.OnlineTrainTicketBooking.dto.User;
import com.feathersoft.trainingproject.OnlineTrainTicketBooking.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public ResponseEntity<String> registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return new ResponseEntity<>("User Registered Successfully", HttpStatus.CREATED);
    }

    public ResponseEntity<String> updateProfile(User user, int userId) {
        Optional<User> existUser = userRepository.findById(userId);
        if (existUser.isPresent()) {
            User updateUser = existUser.get();
            updateUser.setName(user.getName());
            updateUser.setAge(user.getAge());
            updateUser.setGender(user.getGender());
            updateUser.setEmail(user.getEmail());
            updateUser.setPhone(user.getPhone());
            updateUser.setPassword(passwordEncoder.encode(user.getPassword()));
            updateUser.setRole(user.getRole());
            userRepository.save(updateUser);
            return new ResponseEntity<>("User details updated successfully", HttpStatus.ACCEPTED);
        }
        throw new UserNotFoundException("User id " + userId + " not found !!!!");
    }

    public ResponseEntity<String> changePhone(User user, String email) {
        Optional<User> checkUser = userRepository.findByEmail(email);
        if (checkUser.isPresent()) {
            User phoneUpdateUSer = checkUser.get();
            phoneUpdateUSer.setPhone(user.getPhone());
            userRepository.save(phoneUpdateUSer);
            return new ResponseEntity<>("phone changed successfully", HttpStatus.ACCEPTED);
        }
        throw new UserNotFoundException("User not found with given credentials !!!");
    }



    public ResponseEntity<String> changeEmail(User user, String email, long phone) {
        User checkUser = userRepository.findByEmailAndPhone(email, phone);
        System.out.println(checkUser);
        if (checkUser == null)
            throw new UserNotFoundException("User not found with given credentials !!!");

        User emailUpdateUSer = checkUser;
        emailUpdateUSer.setEmail(user.getEmail());
        userRepository.save(emailUpdateUSer);
        return new ResponseEntity<>("Email changed successfully", HttpStatus.ACCEPTED);

    }
    public ResponseEntity<String> changePassword(User user, String email, String password) {

            User checkUser = userRepository.findByEmailAndPassword(email, password);
            if (checkUser == null) {
                throw new UserNotFoundException("User not found with given credentials !!!");
            }else {
                checkUser.setPassword(passwordEncoder.encode(user.getPassword()));
                userRepository.save(checkUser);
                return new ResponseEntity<>("Password changed successfully", HttpStatus.ACCEPTED);
            }
        }

    public ResponseEntity<String> deleteUser(int id) {
        Optional<User> checkUser = userRepository.findById(id);
        if (checkUser.isPresent()){
           userRepository.deleteById(id);
           return new ResponseEntity<>("User deleted",HttpStatus.OK);
        }
        throw new UserNotFoundException("User not found");
    }
}


