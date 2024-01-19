package com.feathersoft.trainingproject.OnlineTrainTicketBooking.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Entity
@Data
@JsonIgnoreProperties("Password")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private int id;

    @NotBlank
    @NotNull
    @Column(name = "Name")
    private String name;

  
    @Column(name = "Age")
    private Integer age;

    @NotBlank
    @NotNull
    @Column(name = "Gender")
    private String gender;

    @NotBlank
    @NotNull
    @Column(name = "Email",unique = true)
    private String email;

    @NotNull
    @Column(name = "Phone",length = 10,unique = true)
    private long phone;

    @Size(message = "Enter valid password",min = 3,max = 270)
    @Column(name = "Password")
    private String password;

    @NotBlank
    @NotNull
    @Column(name = "Role")
    private String role;

//    @OneToMany(mappedBy = "user")
//    private List<BookTrain> bookingDetails;
}
