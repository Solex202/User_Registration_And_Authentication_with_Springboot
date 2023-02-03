package com.shootfish.xyz.task.userRegistrationAndAuthentication.service;

import com.shootfish.xyz.task.userRegistrationAndAuthentication.dtos.request.AddUserRequest;
import com.shootfish.xyz.task.userRegistrationAndAuthentication.dtos.request.LoginRequest;
import com.shootfish.xyz.task.userRegistrationAndAuthentication.dtos.request.UpdateProfileRequest;
import com.shootfish.xyz.task.userRegistrationAndAuthentication.dtos.response.FindUserResponse;
import com.shootfish.xyz.task.userRegistrationAndAuthentication.exceptions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataMongoTest
@ImportAutoConfiguration(exclude = EmbeddedMongoAutoConfiguration.class)
class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testThatUserCanBeCreated(){
        //given
        AddUserRequest request = AddUserRequest
                .builder()
                .firstName("Lota")
                .lastName("Chukwu")
                .email("lota@gmail.com")
                .password("lota2345")
                .confirmPassword("lota2345")
                .build();
        //when
        userService.createUser(request);
        //assert
        assertThat(userService.getAllUser().size(), is(1));

    }

    @Test
    void testThatUserCannotBeCreatedIfEmailIsNotValid_throwException(){
        //given
        AddUserRequest request = AddUserRequest
                .builder()
                .firstName("Lota")
                .lastName("Chukwu")
                .email("lota")
                .password("lota2345")
                .confirmPassword("lota2345")
                .build();
        //assert
        assertThrows(EmailValidationException.class,()->userService.createUser(request));

    }
    @Test
    void testThatUserCannotBeCreatedIfAnyFieldIsEmpty_throwException(){
        //given
        AddUserRequest request = AddUserRequest
                .builder()
                .firstName("")
                .lastName("")
                .email("")
                .password("")
                .confirmPassword("")
                .build();
        //when
        assertThrows(NullFieldException.class,()-> userService.createUser(request));

    }


    @Test
    public void testThatUserCannotBeCreatedWhenPasswordIsLessThan8AndContainsChars(){
        //given
        AddUserRequest userRequest = AddUserRequest
                .builder()
                .firstName("Lota")
                .lastName("Chukwu")
                .email("lota@gmail.com")
                .password("lota23")
                .confirmPassword("lota23")
                .build();
        assertThrows(InvalidPasswordException.class, ()->userService.createUser(userRequest));
    }

    @Test
    void testThatUserCannotCreateAccount_if_passwordsDontMatch_throwException(){
        //given
        AddUserRequest request = AddUserRequest
                .builder()
                .firstName("Lota")
                .lastName("Chukwu")
                .email("lota@gmail.com")
                .password("lota2345")
                .confirmPassword("lota23456")
                .build();
        //assert
        assertThrows(PasswordsMustMatchException.class,()-> userService.createUser(request));
    }

    @Test
    void testThatUserCannotCreateAccountWithEmailThatAlreadyExist_throwException() {
        //given
        AddUserRequest request = AddUserRequest
                .builder()
                .firstName("Lota")
                .lastName("Chukwu")
                .email("lota@gmail.com")
                .password("lota2345")
                .confirmPassword("lota2345")
                .build();
        //when
        userService.createUser(request);

        //given
        AddUserRequest request2 = AddUserRequest
                .builder()
                .firstName("Ginika")
                .lastName("Onwuka")
                .email("lota@gmail.com")
                .password("ginagina")
                .confirmPassword("ginagina")
                .build();

        //assert
        assertThrows(EmailAlreadyExistException.class, ()-> userService.createUser(request2));
    }

    @Test
    void testThatUserCanLogin(){
        //given
        AddUserRequest request = AddUserRequest
                .builder()
                .firstName("Lota")
                .lastName("Chukwu")
                .email("lota@gmail.com")
                .password("lota2345")
                .confirmPassword("lota2345")
                .build();
        //when
        userService.createUser(request);

        LoginRequest loginRequest =LoginRequest
                .builder()
                .email("lota@gmail.com")
                .password("lota2345")
                .build();

        String loginResponse = userService.login(loginRequest);

        assertThat(loginResponse, is("Login successful"));

    }

    @Test
    void testThatUserCannotLoginIfPasswordIsIncorrect_throwException(){
        //given
        AddUserRequest request = AddUserRequest
                .builder()
                .firstName("Lota")
                .lastName("Chukwu")
                .email("lota@gmail.com")
                .password("lota2345")
                .confirmPassword("lota2345")
                .build();
        //when
        userService.createUser(request);

        LoginRequest loginRequest =LoginRequest
                .builder()
                .email("lota@gmail.com")
                .password("lota23487")
                .build();

        assertThrows(UserNameOrPasswordIncorrectException.class,()-> userService.login(loginRequest));
    }

//    @Test
//    void testThatUserCanLogOut(){
//        //given
//        AddUserRequest request = AddUserRequest
//                .builder()
//                .firstName("Lota")
//                .lastName("Chukwu")
//                .email("lota@gmail.com")
//                .password("lota2345")
//                .confirmPassword("lota2345")
//                .build();
//        //when
//        userService.createUser(request);
//
//        LoginRequest loginRequest = LoginRequest
//                .builder()
//                .email("lota@gmail.com")
//                .password("lota2345")
//                .build();
//
//        String loginResponse = userService.login(loginRequest);
//
//        assertThat(loginResponse, is("Login successful"));
//
//        userService.logOut();
//
//    }

    @Test
    void testThatAdminUserCanFindUserByEmail() {
        //given
        AddUserRequest request = AddUserRequest
                .builder()
                .firstName("Lota")
                .lastName("Chukwu")
                .email("lota@gmail.com")
                .password("lota2345")
                .confirmPassword("lota2345")
                .build();

        userService.createUser(request);
        //given
        AddUserRequest request2 = AddUserRequest
                .builder()
                .firstName("ginika")
                .lastName("onwuka")
                .email("ginabby@gmail.com")
                .password("ginagina")
                .confirmPassword("ginagina")
                .build();
        userService.createUser(request2);

        assertThat(userService.getAllUser().size(), is(2));

        FindUserResponse response = userService.findUser(request2.getEmail());
        //assert
        assertThat(response.getEmail(), is("ginabby@gmail.com"));
        assertThat(response.getFirstName(),is("ginika"));
        assertThat(response.getLastName(),is("onwuka"));

    }

    @Test
    void testThatUserCannotFindUserByEmail_throwException() {
        //given
        AddUserRequest request = AddUserRequest
                .builder()
                .firstName("Lota")
                .lastName("Chukwu")
                .email("lota@gmail.com")
                .password("lota2345")
                .confirmPassword("lota2345")
                .build();
        //when
        userService.createUser(request);

        //given
        AddUserRequest request2 = AddUserRequest
                .builder()
                .firstName("Ginika")
                .lastName("Onwuka")
                .email("gina@gmail.com")
                .password("ginagina")
                .confirmPassword("ginagina")
                .build();
        //when
        userService.createUser(request2);

        assertThat(userService.getAllUser().size(), is(2));
        //assert
        assertThrows(UserNotFoundException.class,()-> userService.findUser("fumi@gmail.com"));

    }

    @Test
    void testThatUserCanDeleteUser() {
        //given
        AddUserRequest request = AddUserRequest
                .builder()
                .firstName("mmeso")
                .lastName("onwuka")
                .email("mmeso@gmail.com")
                .password("mmeso123")
                .confirmPassword("mmeso123")
                .build();
        //when
        userService.createUser(request);

        //given
        AddUserRequest request2 = AddUserRequest
                .builder()
                .firstName("mercy")
                .lastName("chioma")
                .email("mercy@gmail.com")
                .password("mercySaidNo")
                .confirmPassword("mercySaidNo")
                .build();
        //when
        userService.createUser(request2);

        assertThat(userService.getAllUser().size(), is(2));

        String message = userService.deleteUser("mercy@gmail.com");
        //assert
        assertThat(userService.getAllUser().size(), is(1));
        assertThat(message, is("User deleted"));
    }

    @Test
    void testThatAdminUserCannotDeleteUserIfNotFound_throwException() {
        //given
        AddUserRequest request = AddUserRequest
                .builder()
                .firstName("mmeso")
                .lastName("onwuka")
                .email("mmeso@gmail.com")
                .password("mmeso123")
                .confirmPassword("mmeso123")
                .build();
        //when
        userService.createUser(request);

        //given
        AddUserRequest request2 = AddUserRequest
                .builder()
                .firstName("mercy")
                .lastName("chioma")
                .email("mercy@gmail.com")
                .password("mercySaidNo")
                .confirmPassword("mercySaidNo")
                .build();

        //when
        userService.createUser(request2);
        //assert
        assertThat(userService.getAllUser().size(), is(2));

        assertThrows(UserNotFoundException.class, ()-> userService.deleteUser("femi@gmail.com"));
    }

    @Test
    void testThatUserCanEditFirstName() {
        //given
        AddUserRequest request = AddUserRequest
                .builder()
                .firstName("mmeso")
                .lastName("onwuka")
                .email("mmeso@gmail.com")
                .password("mmeso123")
                .confirmPassword("mmeso123")
                .build();
        //when
        userService.createUser(request);

        //given
        AddUserRequest request2 = AddUserRequest
                .builder()
                .firstName("mercy")
                .lastName("chioma")
                .email("mercy@gmail.com")
                .password("mercySaidNo")
                .confirmPassword("mercySaidNo")
                .build();
        //when
        userService.createUser(request2);

        assertThat(userService.getAllUser().size(), is(2));

        UpdateProfileRequest updateRequest = new UpdateProfileRequest();
        updateRequest.setFirstName("joel");

        String response = userService.updateUser(request2.getEmail(), updateRequest);

        FindUserResponse response2 = userService.findUser(request2.getEmail());

        //assert
        assertThat(response, is("Profile updated"));
        assertThat(response2.getFirstName(),is("joel"));
        assertThat(response2.getLastName(),is("chioma"));
        assertThat(response2.getEmail(),is("mercy@gmail.com"));
    }

    @Test
    void testThatUserCanEditLastName() {
        //given
        AddUserRequest request = AddUserRequest
                .builder()
                .firstName("mmeso")
                .lastName("onwuka")
                .email("mmeso@gmail.com")
                .password("mmeso123")
                .confirmPassword("mmeso123")
                .build();
        //when
        userService.createUser(request);

        //given
        AddUserRequest request2 = AddUserRequest
                .builder()
                .firstName("mercy")
                .lastName("chioma")
                .email("mercy@gmail.com")
                .password("mercySaidNo")
                .confirmPassword("mercySaidNo")
                .build();
        //when
        userService.createUser(request2);

        assertThat(userService.getAllUser().size(), is(2));

        UpdateProfileRequest updateRequest = new UpdateProfileRequest();
        updateRequest.setLastName("okanga");

        String response = userService.updateUser(request2.getEmail(), updateRequest);

        FindUserResponse response2 = userService.findUser(request2.getEmail());

        //assert
        assertThat(response, is("Profile updated"));
        assertThat(response2.getFirstName(),is("mercy"));
        assertThat(response2.getLastName(),is("okanga"));
        assertThat(response2.getEmail(),is("mercy@gmail.com"));
    }


    @AfterEach
    void tearDown() {
        userService.deleteAll();
    }
}