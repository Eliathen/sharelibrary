package pl.szymanski.sharelibrary.utils.generator;

import pl.szymanski.sharelibrary.entity.Category;
import pl.szymanski.sharelibrary.requests.CategoryRequest;
import pl.szymanski.sharelibrary.utils.constant.CategoryConstant;

public class CategoryGenerator {

    public static Category getCategory() {
        Category category = new Category();
        category.setName(CategoryConstant.TEST_NAME);
        category.setId(1);
        return category;
    }

    public static CategoryRequest getCategoryRequest() {
        return new CategoryRequest(
                1, CategoryConstant.TEST_NAME
        );
    }
}
