package pl.szymanski.sharelibrary.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.szymanski.sharelibrary.entity.Category;
import pl.szymanski.sharelibrary.response.CategoryResponse;
import pl.szymanski.sharelibrary.services.adapters.CategoryServiceImpl;
import pl.szymanski.sharelibrary.utils.generator.CategoryGenerator;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@WithMockUser(username = "z", password = "z")
@SpringBootTest
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CategoryServiceImpl categoryService;

    @Test
    void should_return_list_of_category_response_and_status_200() throws Exception {
        //given
        List<Category> categoryList = Arrays.asList(
                CategoryGenerator.getCategory(),
                CategoryGenerator.getCategory()
        );
        when(categoryService.getAll()).thenReturn(categoryList);
        //when
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/categories"))
                .andExpect(status().isOk())
                .andReturn();
        //then
        List<CategoryResponse> response =
                objectMapper.readValue(result.getResponse().getContentAsString(),
                        new TypeReference<>() {
                        });
        Assertions.assertThat(response.size()).isEqualTo(categoryList.size());
    }
}