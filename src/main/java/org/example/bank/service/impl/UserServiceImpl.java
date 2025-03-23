package org.example.bank.service.impl;

import jakarta.servlet.http.HttpSession;
import org.example.bank.config.JwtUtil;
import org.example.bank.dtos.user.LoginDto;
import org.example.bank.dtos.user.RegisterDto;
import org.example.bank.model.Role;
import org.example.bank.model.User;
import org.example.bank.repository.UserRepository;
import org.example.bank.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private HttpSession httpSession;

    @Override
    public String registerUser(RegisterDto registerDto) {
        if (userRepository.findByEmail(registerDto.getEmail()).isPresent()) {
            return "Email already exists!";
        }
        User user = new User();
        user.setFirstName(registerDto.getFirstName());
        user.setLastName(registerDto.getLastName());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setRole(Role.USER);
        userRepository.save(user);
        return "User registered successfully!";
    }

    @Override
    public String loginUser(LoginDto loginDto) {
        Optional<User> user = userRepository.findByEmail(loginDto.getEmail());

        if (user.isPresent() && passwordEncoder.matches(loginDto.getPassword(), user.get().getPassword())) {
            return jwtUtil.generateToken(user.get().getEmail());
        }
        return "Invalid credentials";
    }

    @Override
    public String getUserByUsername(String email) {
        return userRepository.findByEmail(email)
                .map(User::getEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }

    @Override
    public String logoutUser() {
        httpSession.invalidate();  // ✅ Kullanıcının oturumunu sonlandır
        return "User logged out successfully";
    }

}
