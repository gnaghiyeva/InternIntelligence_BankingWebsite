package org.example.bank.dtos.user;

import lombok.Data;

@Data
public class RegisterDto {
    public String firstName;
    public String lastName;
    private String email;
    private String password;
}
