package org.example.bank.service;

import org.example.bank.dtos.user.LoginDto;
import org.example.bank.dtos.user.RegisterDto;

public interface UserService {
    String registerUser(RegisterDto registerDto);
    String loginUser(LoginDto loginDto);
    String getUserByUsername(String username);
    String logoutUser();
}
