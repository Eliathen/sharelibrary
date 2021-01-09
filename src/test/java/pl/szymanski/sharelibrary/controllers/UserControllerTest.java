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
import pl.szymanski.sharelibrary.converters.RequestConverter;
import pl.szymanski.sharelibrary.entity.User;
import pl.szymanski.sharelibrary.exceptions.users.EmailAlreadyExist;
import pl.szymanski.sharelibrary.exceptions.users.UsernameAlreadyExists;
import pl.szymanski.sharelibrary.response.UserWithoutBooksResponse;
import pl.szymanski.sharelibrary.services.adapters.UserServiceImpl;
import pl.szymanski.sharelibrary.utils.generator.UserGenerator;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserServiceImpl userService;

    @Test
    void should_return_user_request() throws Exception {
        //given
        String userRequest = objectMapper.writeValueAsString(UserGenerator.getUserRequest());
        User user = UserGenerator.getUser();
        when(userService.saveUser(any())).thenReturn(user);
        //when
        MvcResult result = mockMvc.perform(post("/api/v1/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userRequest)
        )
                .andExpect(status().is(201))
                .andReturn();
        //then
        UserWithoutBooksResponse response = objectMapper.readValue(result.getResponse().getContentAsString(), UserWithoutBooksResponse.class);
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getName()).isEqualTo(user.getName());
        Assertions.assertThat(response.getSurname()).isEqualTo(user.getSurname());
        Assertions.assertThat(response.getEmail()).isEqualTo(user.getEmail());
        Assertions.assertThat(response.getUsername()).isEqualTo(user.getUsername());
    }

    @Test
    void should_throw_email_already_exists_exception_and_return_302() throws Exception {
        //given
        User user = RequestConverter.userRequestToUser(UserGenerator.getUserRequest());
        when(userService.saveUser(any())).thenThrow(new EmailAlreadyExist(user.getEmail()));
        String userRequest = objectMapper.writeValueAsString(UserGenerator.getUserRequest());
        //when
        mockMvc.perform(post("/api/v1/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userRequest)
        )
                .andExpect(status().isFound());
    }

    @Test
    void should_throw_username_already_exists_exception_and_return_302() throws Exception {
        //given
        User user = RequestConverter.userRequestToUser(UserGenerator.getUserRequest());
        when(userService.saveUser(any())).thenThrow(new UsernameAlreadyExists(user.getUsername()));
        String userRequest = objectMapper.writeValueAsString(UserGenerator.getUserRequest());
        //when
        mockMvc.perform(post("/api/v1/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userRequest)
        )
                .andExpect(status().isFound());
    }
}