package pl.szymanski.sharelibrary.repositories.ports;

import pl.szymanski.sharelibrary.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {
    List<Category> getAll();
    Optional<Category> findByName(String name);
}




