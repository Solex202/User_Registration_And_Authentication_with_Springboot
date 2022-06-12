package com.shootfish.xyz.task.userRegistrationAndAuthentication.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
//@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddUserRequest {
    private String id;
    private String firstName;
    private String lastName;
    private String password;
    private String confirmPassword;
    private String email;

//    public CreateUserRequest(String firstName, String lastName, String password, String confirmPassword, String email, Gender gender) {
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.password = password;
//        this.confirmPassword = confirmPassword;
//        this.email = email;
//        this.gender = gender;
//    }

}
