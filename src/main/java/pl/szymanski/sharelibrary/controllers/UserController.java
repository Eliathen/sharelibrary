package pl.szymanski.sharelibrary.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.szymanski.sharelibrary.commanddata.AssignBookRequest;
import pl.szymanski.sharelibrary.commanddata.EditUserRequest;
import pl.szymanski.sharelibrary.commanddata.RemoveBookFromUserRequest;
import pl.szymanski.sharelibrary.commanddata.UserRequest;
import pl.szymanski.sharelibrary.converters.RequestConverter;
import pl.szymanski.sharelibrary.entity.User;
import pl.szymanski.sharelibrary.services.ports.UserService;
import pl.szymanski.sharelibrary.views.UserResponse;
import pl.szymanski.sharelibrary.views.UserWithoutBooksResponse;

@RestController
@RequestMapping(value = "/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserWithoutBooksResponse> add(@RequestBody UserRequest userRequest) {
        User user = RequestConverter.userRequestToUser(userRequest);
        return new ResponseEntity<>(
                UserWithoutBooksResponse.of(userService.saveUser(user)),
                HttpStatus.CREATED
        );
    }

    @PostMapping("/assignment")
    public ResponseEntity<UserResponse> assignBook(@RequestBody AssignBookRequest assignBookRequest) {
        return new ResponseEntity<>(
                UserResponse.of(userService.assignBookToUser(assignBookRequest.getUserId(), assignBookRequest.getBookId())),
                HttpStatus.OK
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserWithoutBooksResponse> editUser(@PathVariable("id") Long id, @RequestBody EditUserRequest editUserRequest) {
        return new ResponseEntity<>(
                UserWithoutBooksResponse.of(userService.changeUserDetails(id, editUserRequest)),
                HttpStatus.OK
        );
    }

    @PostMapping("/withdrawal")
    public ResponseEntity<UserResponse> withdrawBookFromUser(@RequestBody RemoveBookFromUserRequest removeBookFromUserRequest) {
        return new ResponseEntity<>(
                UserResponse.of(userService.withdrawBookFromUser(removeBookFromUserRequest.getUserId(), removeBookFromUserRequest.getBookId())),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserWithoutBooksResponse> getUser(@PathVariable("id") Long id) {
        return new ResponseEntity<>(
                UserWithoutBooksResponse.of(userService.getUserById(id)),
                HttpStatus.OK
        );
    }
}
