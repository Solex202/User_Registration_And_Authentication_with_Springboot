package com.shootfish.xyz.task.userRegistrationAndAuthentication.service;

import com.shootfish.xyz.task.userRegistrationAndAuthentication.data.models.User;
import com.shootfish.xyz.task.userRegistrationAndAuthentication.dtos.request.AddUserRequest;
import com.shootfish.xyz.task.userRegistrationAndAuthentication.dtos.request.LoginRequest;
import com.shootfish.xyz.task.userRegistrationAndAuthentication.dtos.request.UpdateProfileRequest;
import com.shootfish.xyz.task.userRegistrationAndAuthentication.dtos.response.FindUserResponse;
import com.shootfish.xyz.task.userRegistrationAndAuthentication.dtos.response.UserDto;

import java.util.List;


public interface UserService {
    String createUser(AddUserRequest request);

    List<User> getAllUser();

    void deleteAll();

    FindUserResponse findUser(String email);


    String  deleteUser(String email);

    String updateUser(String email, UpdateProfileRequest updateRequest);

    String login(LoginRequest loginRequest);
}
