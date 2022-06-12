package com.shootfish.xyz.task.userRegistrationAndAuthentication.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Validated
public class LoginRequest {

    @Email
    private String email;
    private String password;
}
