package com.shootfish.xyz.task.userRegistrationAndAuthentication.data.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Validated
public class User {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String password;
    private String confirmPassword;
    @Email
    @NotNull @NotBlank
    private String email;
    private boolean loginStatus ;
}
