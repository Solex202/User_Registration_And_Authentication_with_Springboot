package com.shootfish.xyz.task.userRegistrationAndAuthentication.controller;

import com.shootfish.xyz.task.userRegistrationAndAuthentication.data.models.User;
import com.shootfish.xyz.task.userRegistrationAndAuthentication.dtos.request.AddUserRequest;
import com.shootfish.xyz.task.userRegistrationAndAuthentication.dtos.request.LoginRequest;
import com.shootfish.xyz.task.userRegistrationAndAuthentication.dtos.request.UpdateProfileRequest;
import com.shootfish.xyz.task.userRegistrationAndAuthentication.dtos.response.ApiResponse;
import com.shootfish.xyz.task.userRegistrationAndAuthentication.exceptions.*;
import com.shootfish.xyz.task.userRegistrationAndAuthentication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins={"http://localhost:3000/**", "https://user-registration-app-frontend.herokuapp.com/**"})
@RequestMapping("api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/createUser")
    public ResponseEntity<?> createUser(@RequestBody AddUserRequest request){

        try{
            ApiResponse response = ApiResponse
                    .builder()
                    .isSuccessful(true)
                    .message(""+ userService.createUser(request))
                    .build();

            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch(EmailAlreadyExistException | PasswordsMustMatchException | NullFieldException ex){
            ApiResponse response = ApiResponse
                    .builder()
                    .isSuccessful(false)
                    .message(ex.getMessage())
                    .build();

            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findUser/{email}")
    public ResponseEntity<?> getUser(@PathVariable String email){
        try{

            ApiResponse response = ApiResponse
                    .builder()
                    .isSuccessful(true)
                    .message("" + userService.findUser(email))
                    .build();

            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch(UserNotFoundException ex){
            ApiResponse response = ApiResponse
                    .builder()
                    .isSuccessful(false)
                    .message(ex.getMessage())
                    .build();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/getAllUsers")
    public List<User> getAllUsers(){
        return userService.getAllUser();
    }

    @DeleteMapping("/deleteUser/{email}")
    public ResponseEntity<?> delete(@PathVariable String email){
        try{
            ApiResponse response = ApiResponse
                    .builder()
                    .isSuccessful(true)
                    .message("" + userService.deleteUser(email))
                    .build();

            return new ResponseEntity<>(response, HttpStatus.OK);

        }catch (UserNotFoundException ex){
            ApiResponse response = ApiResponse
                    .builder()
                    .isSuccessful(false)
                    .message(ex.getMessage())
                    .build();

            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/updateUser/{email}")
    public ResponseEntity<?> update(@PathVariable String email, @RequestBody UpdateProfileRequest request){
        try{
            ApiResponse response = ApiResponse
                    .builder()
                    .isSuccessful(true)
                    .message("" + userService.updateUser(email, request))
                    .build();

            return new ResponseEntity<>(response, HttpStatus.OK);

        }catch (UserNotFoundException ex){
            ApiResponse response = ApiResponse
                    .builder()
                    .isSuccessful(false)
                    .message(ex.getMessage())
                    .build();

            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest request){
        try{
            ApiResponse response = ApiResponse
                    .builder()
                    .isSuccessful(true)
                    .message("" + userService.login(request))
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (UserNotFoundException | UserNameOrPasswordIncorrectException ex){
            ApiResponse response = ApiResponse
                    .builder()
                    .isSuccessful(false)
                    .message(ex.getMessage())
                    .build();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
