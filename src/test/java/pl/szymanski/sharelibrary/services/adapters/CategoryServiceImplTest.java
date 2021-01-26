package pl.szymanski.sharelibrary.services.adapters;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.szymanski.sharelibrary.entity.Category;
import pl.szymanski.sharelibrary.repositories.ports.CategoryRepository;
import pl.szymanski.sharelibrary.utils.generator.CategoryGenerator;

import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    CategoryRepository categoryRepository;

    @InjectMocks
    CategoryServiceImpl categoryService;

    @Test
    void shouldReturnListOfCategoryWithOneElement() {
        //given
        List<Category> categoryList = List.of(CategoryGenerator.getCategory());
        when(categoryRepository.getAll()).thenReturn(categoryList);
        //when
        List<Category> result = categoryService.getAll();
        //then
        Assertions.assertThat(result).hasSize(1)
                .hasAtLeastOneElementOfType(Category.class);
    }

    @Test
    void shouldReturnCategoryWithGivenName() {
        //given
        Category category = CategoryGenerator.getCategory();
        when(categoryRepository.findByName(category.getName()))
                .thenReturn(java.util.Optional.of(category));
        //when
        Category result = categoryService.findByName(category.getName());
        //then
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getName()).isEqualTo(category.getName());
    }
}