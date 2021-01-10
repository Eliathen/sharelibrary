package pl.szymanski.sharelibrary.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
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
import pl.szymanski.sharelibrary.entity.Requirement;
import pl.szymanski.sharelibrary.exceptions.requirements.RequirementAlreadyExists;
import pl.szymanski.sharelibrary.requests.CreateRequirementRequest;
import pl.szymanski.sharelibrary.response.RequirementResponse;
import pl.szymanski.sharelibrary.services.adapters.RequirementServiceImpl;
import pl.szymanski.sharelibrary.utils.generator.RequirementGenerator;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@WithMockUser(username = "z", password = "z")
@AutoConfigureMockMvc
class RequirementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RequirementServiceImpl requirementService;

    @Test
    void should_return_list_of_user_requirements_and_status_200() throws Exception {
        //given
        List<Requirement> requirementList = Arrays.asList(
                RequirementGenerator.getRequirement(),
                RequirementGenerator.getRequirement());
        Long userId = 1L;
        when(requirementService.getUserRequirements(userId))
                .thenReturn(requirementList);
        //when
        MvcResult result = mockMvc.perform(
                get("/api/v1/requirements/" + userId))
                .andExpect(status().isOk())
                .andReturn();
        //then
        List<RequirementResponse> response = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                new TypeReference<>() {
                }
        );
        Assertions.assertThat(response.size()).isEqualTo(requirementList.size());

    }

    @Test
    void should_return_requirement_and_status_200() throws Exception {
        //given
        CreateRequirementRequest createRequirementRequest = RequirementGenerator.getCreateRequirementRequest();
        String request = objectMapper.writeValueAsString(createRequirementRequest);
        when(requirementService.createRequirement(any()))
                .thenReturn(RequirementGenerator.getRequirement());
        //when
        MvcResult result = mockMvc.perform(
                post("/api/v1/requirements")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request)
        )
                .andExpect(status().isCreated())
                .andReturn();
        //then
        RequirementResponse response = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                RequirementResponse.class
        );
        Assertions.assertThat(response.getUser().getId()).isEqualTo(createRequirementRequest.getUserId());
        Assertions.assertThat(response.getExchange().getId()).isEqualTo(createRequirementRequest.getExchangeId());

    }

    @Test
    void should_throw_requirement_already_exists_during_create_and_status_302() throws Exception {
        //given
        CreateRequirementRequest createRequirementRequest = RequirementGenerator.getCreateRequirementRequest();
        String request = objectMapper.writeValueAsString(createRequirementRequest);
        when(requirementService.createRequirement(any())).thenThrow(new RequirementAlreadyExists());
        //when
        MvcResult result = mockMvc.perform(
                post("/api/v1/requirements")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request)
        )
                .andExpect(status().isFound())
                .andReturn();
        //then
        assertTrue(result.getResolvedException() instanceof RequirementAlreadyExists);
    }

}