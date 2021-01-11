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
import pl.szymanski.sharelibrary.entity.Exchange;
import pl.szymanski.sharelibrary.entity.Requirement;
import pl.szymanski.sharelibrary.exceptions.exchanges.ExchangeNotExist;
import pl.szymanski.sharelibrary.requests.AddExchangeRequest;
import pl.szymanski.sharelibrary.requests.ExecuteExchangeRequest;
import pl.szymanski.sharelibrary.response.ExchangeResponse;
import pl.szymanski.sharelibrary.response.RequirementResponse;
import pl.szymanski.sharelibrary.services.adapters.ExchangeServiceImpl;
import pl.szymanski.sharelibrary.utils.generator.ExchangeGenerator;
import pl.szymanski.sharelibrary.utils.generator.RequirementGenerator;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pl.szymanski.sharelibrary.utils.constant.CoordinatesConstant.TEST_LATITUDE;
import static pl.szymanski.sharelibrary.utils.constant.CoordinatesConstant.TEST_LONGITUDE;

@SpringBootTest
@WithMockUser(username = "z", password = "z")
@AutoConfigureMockMvc
class ExchangeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ExchangeServiceImpl exchangeService;


    @Test
    void should_return_exchange_response_and_status_201() throws Exception {
        //given
        AddExchangeRequest request = ExchangeGenerator.getAddExchangeRequest();
        Exchange exchange = ExchangeGenerator.getExchange();
        when(exchangeService.saveExchange(any())).thenReturn(exchange);
        //when
        MvcResult result = mockMvc.perform(post("/api/v1/exchanges")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        ).andExpect(status().isCreated())
                .andReturn();
        //then
        ExchangeResponse response = objectMapper.readValue(result.getResponse().getContentAsString(), ExchangeResponse.class);
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getId()).isEqualTo(exchange.getId());
        Assertions.assertThat(response.getDeposit()).isEqualTo(exchange.getDeposit());
    }

    @Test
    void should_return_list_of_user_exchanges_and_status_200() throws Exception {
        //given
        Exchange exchange = ExchangeGenerator.getExchange();
        List<Exchange> exchanges = List.of(exchange, exchange);
        long userId = 1L;
        when(exchangeService.getExchangesByUserId(userId)).thenReturn(exchanges);
        //when
        MvcResult result = mockMvc.perform(get("/api/v1/exchanges/")
                .param("userId", String.valueOf(userId))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andReturn();
        //then
        List<ExchangeResponse> response = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                new TypeReference<>() {
                }
        );
        Assertions.assertThat(response).isNotEmpty();
        Assertions.assertThat(response.size()).isEqualTo(exchanges.size());
    }

    @Test
    void should_execute_and_return_exchange_and_status_200() throws Exception {
        //given
        Exchange exchange = ExchangeGenerator.getExchange();
        ExecuteExchangeRequest request = ExchangeGenerator.getExecuteExchangeRequest();
        when(exchangeService.executeExchange(any())).thenReturn(exchange);
        //when
        MvcResult result = mockMvc.perform(post("/api/v1/exchanges/execution")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        ).andExpect(status().isOk())
                .andReturn();
        //then
        ExchangeResponse response = objectMapper.readValue(result.getResponse().getContentAsString(), ExchangeResponse.class);
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getId()).isEqualTo(exchange.getId());
        Assertions.assertThat(response.getUser().getId()).isEqualTo(exchange.getUser().getId());
    }

    @Test
    void should_finish_and_return_exchange_and_status_200() throws Exception {
        //given
        Exchange exchange = ExchangeGenerator.getExchange();
        ExecuteExchangeRequest request = ExchangeGenerator.getExecuteExchangeRequest();
        long exchangeId = 1L;
        //when
        MvcResult result = mockMvc.perform(post("/api/v1/exchanges/" + exchangeId + "/end")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent())
                .andReturn();
        //then
    }

    @Test
    void should_return_exchange_with_id_and_status_200() throws Exception {
        //given
        Exchange exchange = ExchangeGenerator.getExchange();
        long exchangeId = 1L;
        when(exchangeService.getExchangeById(exchangeId)).thenReturn(exchange);
        //when
        MvcResult result = mockMvc.perform(get("/api/v1/exchanges/" + exchangeId)
                .content(MediaType.APPLICATION_JSON_VALUE)
        ).andExpect(status().isOk())
                .andReturn();
        //then
        ExchangeResponse response = objectMapper.readValue(result.getResponse().getContentAsString(), ExchangeResponse.class);
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getId()).isEqualTo(exchangeId);
    }

    @Test
    void should_throw_exception_exchange_not_exist_and_status_404() throws Exception {
        //given
        long exchangeId = 1L;
        when(exchangeService.getExchangeById(exchangeId)).thenThrow(new ExchangeNotExist(exchangeId));
        //when
        MvcResult result = mockMvc.perform(get("/api/v1/exchanges/" + exchangeId)
                .content(MediaType.APPLICATION_JSON_VALUE)
        ).andExpect(status().isNotFound())
                .andReturn();
        //then
        assertTrue(result.getResolvedException() instanceof ExchangeNotExist);

    }

    @Test
    void should_return_list_of_requirement_by_exchange_id() throws Exception {
        //given
        List<Requirement> requirements = List.of(RequirementGenerator.getRequirement());
        long exchangeId = 1L;
        when(exchangeService.getRequirements(exchangeId)).thenReturn(requirements);
        //when
        MvcResult result = mockMvc.perform(get("/api/v1/exchanges/" + exchangeId + "/requirements")
                .content(MediaType.APPLICATION_JSON_VALUE)
        ).andExpect(status().isOk())
                .andReturn();
        //then
        List<RequirementResponse> response =
                objectMapper.readValue(result.getResponse().getContentAsString(),
                        new TypeReference<>() {
                        });
        Assertions.assertThat(response).isNotEmpty();
    }

    @Test
    void should_return_list_of_exchanges_and_status_200() throws Exception {
        //given
        Exchange exchange = ExchangeGenerator.getExchange();
        List<Exchange> exchanges = List.of(exchange, exchange);
        long userId = 1L;
        when(exchangeService.getExchangesByWithUserId(userId)).thenReturn(exchanges);
        //when
        MvcResult result = mockMvc.perform(get("/api/v1/exchanges/withUser/" + userId)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andReturn();
        //then
        List<ExchangeResponse> response = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                new TypeReference<>() {
                }
        );
        Assertions.assertThat(response).isNotEmpty();
        Assertions.assertThat(response.size()).isEqualTo(exchanges.size());
    }

    @Test
    void should_return_filtered_list_of_exchanges_and_status_200() throws Exception {
        //given
        ExchangeResponse exchange = ExchangeResponse.of(ExchangeGenerator.getExchange());
        List<ExchangeResponse> exchanges = List.of(exchange, exchange);
        when(exchangeService.filter(
                anyDouble(), anyDouble(), anyDouble(), anyList(), anyString(), anyInt(), anyList()
        )).thenReturn(exchanges);
        //when
        MvcResult result = mockMvc.perform(get("/api/v1/exchanges/filter")
                .contentType(MediaType.APPLICATION_JSON)
                .param("lat", String.valueOf(TEST_LATITUDE))
                .param("long", String.valueOf(TEST_LONGITUDE))
                .param("rad", String.valueOf(100.0))
                .param("cat", "Fantasy")
                .param("q", "query")
                .param("lan", String.valueOf(1))
                .param("con", String.valueOf(1))
        ).andExpect(status().isOk())
                .andReturn();
        //then
        List<ExchangeResponse> response = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                new TypeReference<>() {
                }
        );
        Assertions.assertThat(response).isNotEmpty();
        Assertions.assertThat(response.size()).isEqualTo(exchanges.size());
    }
}