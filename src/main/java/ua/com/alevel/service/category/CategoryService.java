package ua.com.alevel.service.category;

import ua.com.alevel.entity.Category;
import ua.com.alevel.service.BaseCrudService;

import java.util.List;

public interface CategoryService extends BaseCrudService<Category> {

    List<Category> findByTitle(String text);
    List<Category> findAllByOrderByTitleAsc();
}