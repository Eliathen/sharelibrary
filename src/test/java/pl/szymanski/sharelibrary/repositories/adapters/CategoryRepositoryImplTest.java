package pl.szymanski.sharelibrary.repositories.adapters;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.szymanski.sharelibrary.entity.Category;
import pl.szymanski.sharelibrary.repositories.jpa.CategoryJPARepository;
import pl.szymanski.sharelibrary.repositories.ports.CategoryRepository;
import pl.szymanski.sharelibrary.utils.generator.CategoryGenerator;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.times;

@SpringBootTest
class CategoryRepositoryImplTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryJPARepository categoryJPARepository;

    @BeforeEach
    void setUp() {
        categoryJPARepository.deleteAll();
        categoryJPARepository.save(CategoryGenerator.getCategory());
    }

    @AfterEach
    void cleanUp() {
        categoryJPARepository.deleteAll();
    }


    @Test
    void shouldReturnAllCategories() {
        //given
        //when
        List<Category> categories = categoryRepository.getAll();
        //then
        Assertions.assertThat(categories.size()).isEqualTo(1);
    }

    @Test
    void shouldReturnCategory() {
        //given
        Category category = CategoryGenerator.getCategory();
        //when
        Optional<Category> result = categoryRepository.findByName(category.getName());
        //then
        Assertions.assertThat(result.get().getName()).isEqualTo(category.getName());
    }

    @Test
    void shouldCallMethodFindAllFromJpaRepository() {
        //given
        Category category = CategoryGenerator.getCategory();
        CategoryJPARepository categoryJPARepository = Mockito.mock(CategoryJPARepository.class);
        CategoryRepository repository = new CategoryRepositoryImpl(categoryJPARepository);
        //when
        repository.getAll();
        //then
        Mockito.verify(categoryJPARepository, times(1))
                .findAll();
    }

    @Test
    void shouldCallMethodFindByNameFromJpaRepository() {
        //given
        Category category = CategoryGenerator.getCategory();
        CategoryJPARepository categoryJPARepository = Mockito.mock(CategoryJPARepository.class);
        CategoryRepository repository = new CategoryRepositoryImpl(categoryJPARepository);
        //when
        repository.findByName(category.getName());
        //then
        Mockito.verify(categoryJPARepository, times(1))
                .findFirstByNameIgnoreCase(category.getName());
    }
}