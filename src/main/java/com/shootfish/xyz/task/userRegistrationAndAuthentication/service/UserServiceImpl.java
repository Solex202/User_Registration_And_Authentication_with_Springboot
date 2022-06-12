package com.shootfish.xyz.task.userRegistrationAndAuthentication.service;

import com.shootfish.xyz.task.userRegistrationAndAuthentication.data.models.User;
import com.shootfish.xyz.task.userRegistrationAndAuthentication.data.repository.UserRepository;
import com.shootfish.xyz.task.userRegistrationAndAuthentication.dtos.request.AddUserRequest;
import com.shootfish.xyz.task.userRegistrationAndAuthentication.dtos.request.LoginRequest;
import com.shootfish.xyz.task.userRegistrationAndAuthentication.dtos.request.UpdateProfileRequest;
import com.shootfish.xyz.task.userRegistrationAndAuthentication.dtos.response.FindUserResponse;
import com.shootfish.xyz.task.userRegistrationAndAuthentication.dtos.response.UserDto;
import com.shootfish.xyz.task.userRegistrationAndAuthentication.exceptions.*;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    private ModelMapper mapper = new ModelMapper();

    @Override
    public UserDto createUser(AddUserRequest request) {
        if(emailAlreadyExist(request.getEmail())){
            throw new EmailAlreadyExistException("Email already exist");
        }
//        if(!passwordIsValid(request.getConfirmPassword()) ) throw new InvalidPasswordException("invalid password");

        if(!request.getPassword().matches(request.getConfirmPassword())){
            throw new PasswordsMustMatchException("Passwords must match");
        }
//        if(!passwordIsValid(request.getPassword()) || passwordIsValid(request.getConfirmPassword())) throw new InvalidPasswordException("invalid password");
        if(Objects.equals(request.getFirstName(), "") || Objects.equals(request.getLastName(), "") || Objects.equals(request.getEmail(), "") || Objects.equals(request.getPassword(), "") || Objects.equals(request.getConfirmPassword(), "")){
            throw new NullFieldException("Please fill out fields");
        }

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(request.getPassword())
                .confirmPassword(request.getConfirmPassword())
                .build();

    userRepository.save(user);

    return mapper.map(user, UserDto.class);
    }

    private boolean emailAlreadyExist(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    private boolean passwordIsValid(String password) {
        String isValid = "^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=.*[@#$%^&+=])"
                + "(?=\\S+$).{12,20}$";

        return password.matches(isValid);
    }

    @Override
    public void deleteAll() {
        userRepository.deleteAll();
    }

    @Override
    public FindUserResponse findUser(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(()-> new UserNotFoundException("User not found"));

        FindUserResponse response = new FindUserResponse();
        response.setEmail(user.getEmail());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        return response;
    }

    @Override
    public String deleteUser(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(()-> new UserNotFoundException("User not found"));
        userRepository.delete(user);
        return "User deleted";
    }

    @Override
    public String updateUser(String email, UpdateProfileRequest updateRequest) {
        User user = userRepository.findByEmail(email).orElseThrow(()->new UserNotFoundException("User not found"));
        if (updateRequest.getFirstName() != null) {
            user.setFirstName(updateRequest.getFirstName());
        }
        if (updateRequest.getLastName() != null) user.setLastName(updateRequest.getLastName());
        userRepository.save(user);
        return "Profile updated";
    }

    @Override
    public String login(LoginRequest loginRequest) {

        User user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(()-> new UserNotFoundException("User not found"));
        if(!user.getPassword().matches(loginRequest.getPassword())){
            throw new UserNameOrPasswordIncorrectException("Username or password invalid");
        }
        user.setLoginStatus(true);
        userRepository.save(user);
        return "Login successful";
    }

}
