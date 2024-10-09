package com.example.bulletinBoard.web.login;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginForm {

    @Email
    private String email;

    @NotBlank
    private String password;


}
