package org.example.bank.controller;


import org.example.bank.dtos.user.LoginDto;
import org.example.bank.dtos.user.RegisterDto;
import org.example.bank.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> register(@ModelAttribute RegisterDto registerDto) {
        return ResponseEntity.ok(userService.registerUser(registerDto));
    }

    @PostMapping(value = "/login", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> login(@ModelAttribute LoginDto loginDto) {
        return ResponseEntity.ok(userService.loginUser(loginDto));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        String response = userService.logoutUser();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
