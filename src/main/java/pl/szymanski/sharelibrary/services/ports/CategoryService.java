package pl.szymanski.sharelibrary.services.ports;

import pl.szymanski.sharelibrary.entity.Category;

import java.util.List;

public interface CategoryService {

    List<Category> getAll();

    Category findByName(String name);
}
