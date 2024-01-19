package com.feathersoft.trainingproject.OnlineTrainTicketBooking.controller;

import com.feathersoft.trainingproject.OnlineTrainTicketBooking.dto.User;
import com.feathersoft.trainingproject.OnlineTrainTicketBooking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register-user")
    public ResponseEntity<String> registerUser(@RequestBody User user){
        return userService.registerUser(user);
    }

    @PutMapping("/manage-profile/{userId}")
    public ResponseEntity<String > updateProfile(@RequestBody User user, @PathVariable int userId){
        return userService.updateProfile(user,userId);
    }

    @PutMapping("/change-email")
    public ResponseEntity<String> changePhone (@RequestBody User user,@RequestParam(name = "email")String email,@RequestParam(name = "phone") long phone){
        return userService.changeEmail(user,email,phone);
    }

    @PatchMapping("/change-password")
    public ResponseEntity<String> changePassword (@RequestBody User user,@RequestParam(name = "email")String email,@RequestParam(name = "password") String password){
        return userService.changePassword(user,email,password);
    }

    @DeleteMapping("/delete-user/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id){
        return userService.deleteUser(id);
    }

}
