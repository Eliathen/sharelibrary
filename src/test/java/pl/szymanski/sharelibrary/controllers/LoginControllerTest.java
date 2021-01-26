package pl.szymanski.sharelibrary.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import pl.szymanski.sharelibrary.entity.User;
import pl.szymanski.sharelibrary.exceptions.auth.InvalidUsernameEmailOrPassword;
import pl.szymanski.sharelibrary.response.UserLoginResponse;
import pl.szymanski.sharelibrary.security.JwtAuthenticationResponse;
import pl.szymanski.sharelibrary.services.adapters.UserServiceImpl;
import pl.szymanski.sharelibrary.utils.generator.UserGenerator;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class LoginControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserServiceImpl userService;


    @Test
    void shouldLoginAndReturnToken() throws Exception {
        String login = objectMapper.writeValueAsString(UserGenerator.getLoginRequest());
        User user = UserGenerator.getUser();
        when(userService.getUserByEmailOrUserName(anyString())).thenReturn(user);
        when(userService.getJwt(any())).thenReturn(new JwtAuthenticationResponse("token"));
        MvcResult result = mockMvc.perform(post("/api/v1/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(login)
        ).andExpect(status().isOk())
                .andReturn();
        UserLoginResponse response =
                objectMapper.readValue(result.getResponse().getContentAsString(), UserLoginResponse.class);
        Assertions.assertThat(response.getResponse().getAccessToken()).isNotEmpty();
    }

    @Test
    void shouldThrowExceptionInvalidEmailUsernameOrPasswordAndReturnStatus400() throws Exception {
        String login = objectMapper.writeValueAsString(UserGenerator.getLoginRequest());
        when(userService.getUserByEmailOrUserName(anyString())).thenThrow(new InvalidUsernameEmailOrPassword());
        mockMvc.perform(post("/api/v1/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(login)
        ).andExpect(status().isForbidden())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof InvalidUsernameEmailOrPassword))
                .andReturn();
    }
}