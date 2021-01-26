package pl.szymanski.sharelibrary.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import pl.szymanski.sharelibrary.converters.RequestConverter;
import pl.szymanski.sharelibrary.entity.User;
import pl.szymanski.sharelibrary.exceptions.users.EmailAlreadyExist;
import pl.szymanski.sharelibrary.exceptions.users.UserNotFoundById;
import pl.szymanski.sharelibrary.exceptions.users.UsernameAlreadyExists;
import pl.szymanski.sharelibrary.requests.EditUserRequest;
import pl.szymanski.sharelibrary.response.UserWithoutBooksResponse;
import pl.szymanski.sharelibrary.services.adapters.BookServiceImpl;
import pl.szymanski.sharelibrary.services.adapters.UserServiceImpl;
import pl.szymanski.sharelibrary.utils.generator.BookGenerator;
import pl.szymanski.sharelibrary.utils.generator.UserGenerator;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "z", password = "z")
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserServiceImpl userService;

    @MockBean
    private BookServiceImpl bookService;

    @Test
    void shouldReturnUserRequest() throws Exception {
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
    void shouldThrowEmailAlreadyExistsExceptionAndReturn302() throws Exception {
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
    void shouldThrowUsernameAlreadyExistsExceptionAndReturn302() throws Exception {
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

    @Test
    void shouldAssignBookAndReturnStatus200() throws Exception {
        //given
        String request = objectMapper.writeValueAsString(BookGenerator.getAssignBookRequest());
        when(userService.assignBookToUser(anyLong(), anyLong())).thenReturn(UserGenerator.getUserWithBooks());
        //when
        mockMvc.perform(post("/api/v1/users/assignment")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk());
        //then
    }

    @Test
    void shouldEditUserAndReturnStatus200() throws Exception {
        final Long userId = 1L;
        EditUserRequest user = UserGenerator.getEditUserRequest();
        String request = objectMapper.writeValueAsString(user);
        when(userService.changeUserDetails(userId, user)).thenReturn(UserGenerator.getUser());
        mockMvc.perform(put("/api/v1/users/" + userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
        )
                .andExpect(status().isOk());
    }

    @Test
    void shouldThrowExceptionUserNotFoundAndReturnStatus404() throws Exception {
        final Long userId = 1L;
        EditUserRequest user = UserGenerator.getEditUserRequest();
        String request = objectMapper.writeValueAsString(user);
        when(userService.changeUserDetails(userId, user)).thenThrow(new UserNotFoundById(userId));
        mockMvc.perform(put("/api/v1/users/" + userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
        )
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof UserNotFoundById));
    }


    @Test
    void shouldWithdrawBookAndReturnStatus200() throws Exception {
        //given
        String request = objectMapper.writeValueAsString(UserGenerator.getRemoveBookFromUserRequest());
        when(userService.withdrawBookFromUser(anyLong(), anyLong())).thenReturn(UserGenerator.getUserWithBooks());
        //when
        mockMvc.perform(post("/api/v1/users/withdrawal")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk());
        //then
    }


    @Test
    void shouldGetUserAndReturnStatus200() throws Exception {
        final Long userId = 1L;
        when(userService.getUserById(userId)).thenReturn(UserGenerator.getUser());
        mockMvc.perform(get("/api/v1/users/" + userId)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk());
    }

    @Test
    void shouldThrowExceptionUserNotFoundByIdAndReturnStatus404() throws Exception {
        final Long userId = 1L;
        when(userService.getUserById(userId)).thenThrow(new UserNotFoundById(userId));
        mockMvc.perform(get("/api/v1/users/" + userId)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof UserNotFoundById));
    }
}