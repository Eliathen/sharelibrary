package pl.szymanski.sharelibrary.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.szymanski.sharelibrary.commanddata.LoginCommandData;
import pl.szymanski.sharelibrary.services.ports.UserService;
import pl.szymanski.sharelibrary.views.UserLoginView;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/login")
public class LoginController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<Object> login(@RequestBody LoginCommandData loginCommandData) {
        return new ResponseEntity<>(
                UserLoginView.of(userService.getUserByEmailOrUserName(loginCommandData.getUserNameOrEmail()), userService.getJwt(loginCommandData))
                , OK
        );
    }

}
