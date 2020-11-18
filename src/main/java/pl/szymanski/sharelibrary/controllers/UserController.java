package pl.szymanski.sharelibrary.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.szymanski.sharelibrary.commanddata.AssignBookCommandData;
import pl.szymanski.sharelibrary.commanddata.UserCommandData;
import pl.szymanski.sharelibrary.converters.CommandsDataConverter;
import pl.szymanski.sharelibrary.entity.User;
import pl.szymanski.sharelibrary.services.ports.UserService;
import pl.szymanski.sharelibrary.views.UserWithoutBooksView;

@RestController
@RequestMapping(value = "/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserWithoutBooksView> add(@RequestBody UserCommandData userCommandData) {
        User user = CommandsDataConverter.UserCommandDataToUser(userCommandData);
        return new ResponseEntity<>(
                UserWithoutBooksView.of(userService.saveUser(user)),
                HttpStatus.CREATED
        );
    }

    @PostMapping("/assign")
    public ResponseEntity<UserWithoutBooksView> assignBook(@RequestBody AssignBookCommandData assignBookCommandData) {
        return new ResponseEntity<>(
                UserWithoutBooksView.of(userService.assignBookToUser(assignBookCommandData.getUserId(), assignBookCommandData.getBookId())),
                HttpStatus.OK
        );
    }

}
