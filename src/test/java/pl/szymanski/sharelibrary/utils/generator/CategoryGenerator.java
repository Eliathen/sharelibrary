package pl.szymanski.sharelibrary.utils.generator;

import pl.szymanski.sharelibrary.entity.Category;
import pl.szymanski.sharelibrary.utils.constant.CategoryConstant;

public class CategoryGenerator {

    public static Category getCategory() {
        Category category = new Category();
        category.setName(CategoryConstant.TEST_NAME);
        return category;
    }

}
