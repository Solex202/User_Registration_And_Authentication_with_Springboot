package com.shootfish.xyz.task.userRegistrationAndAuthentication.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FindUserResponse {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
