package com.Jeeva.UserService.controller;


import com.Jeeva.UserService.Exception.UserAlreadyExistException;
import com.Jeeva.UserService.model.User;
import com.Jeeva.UserService.request.CreateUserRequest;
import com.Jeeva.UserService.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody @Valid CreateUserRequest createUserRequest) throws UserAlreadyExistException, JsonProcessingException {
        return new ResponseEntity<>(userService.createUser(createUserRequest), HttpStatus.CREATED);
    }

    @GetMapping("/getUser")
    public User findUser(@RequestParam("contact") String contact){
        return userService.loadUserByUsername(contact);
    }
}
