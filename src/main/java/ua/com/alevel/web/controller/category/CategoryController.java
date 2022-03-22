package ua.com.alevel.web.controller.category;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.com.alevel.entity.Category;
import ua.com.alevel.repository.сategory.CategoryRepository;
import ua.com.alevel.search.CategorySearchValues;
import ua.com.alevel.util.ConsoleLoggerSQL;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/all")
    public List<Category> findAll() {
        //return categoryRepository.findAll();
        ConsoleLoggerSQL.logMethod("CategoryRepository: findAll()");
        return categoryRepository.findAllByOrderByTitleAsc();
    }

    @PostMapping("/add")
    public ResponseEntity<Category> addCategory(@RequestBody Category category) {
        ConsoleLoggerSQL.logMethod("CategoryRepository: addCategory()");

        // Id нового объекта должен быть пустым
        if (category.getId() != null && category.getId() != 0) {
            return new ResponseEntity("ID of new category must be null", HttpStatus.NOT_ACCEPTABLE);
        }

        // Название нового объекта не должно быть пустым
        if (category.getTitle() == null || category.getTitle().trim().length() == 0) {
            return new ResponseEntity("Category name must not be empty", HttpStatus.NOT_ACCEPTABLE);
        }

        return ResponseEntity.ok(categoryRepository.save(category));
    }

    @PutMapping("/edit")
    public ResponseEntity<Category> editCategory(@RequestBody Category category) {
        ConsoleLoggerSQL.logMethod("CategoryRepository: editCategory()");

        // Id редактированного объекта НЕ должен быть пустым
        if (category.getId() == null || category.getId() == 0) {
            return new ResponseEntity("ID of edit category must not be null", HttpStatus.NOT_ACCEPTABLE);
        }

        // Название редактированного объекта не должно быть пустым
        if (category.getTitle() == null || category.getTitle().trim().length() == 0) {
            return new ResponseEntity("Category name must not be empty", HttpStatus.NOT_ACCEPTABLE);
        }

        // Метод save() работает как на создание так и на обновление
        return ResponseEntity.ok(categoryRepository.save(category));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Category> findById(@PathVariable Long id) {
        ConsoleLoggerSQL.logMethod("CategoryRepository: findById()");
        Category category = null;
        try {
            category = categoryRepository.findById(id).get();
        } catch (NoSuchElementException e) {
            return new ResponseEntity("Category with id=" + id + " not found", HttpStatus.NOT_ACCEPTABLE);
        }

        return ResponseEntity.ok(category);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        ConsoleLoggerSQL.logMethod("CategoryRepository: deleteById()");
        try {
            categoryRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>("Category with id=" + id + " not found", HttpStatus.NOT_ACCEPTABLE);
        }

        return new ResponseEntity<>("Category with id=" + id + " was deleted", HttpStatus.OK);
    }

    // поиск по любым параметрам
    // отправляем JSON и по нем будем искать
    // чтобы не перечислять все параметры для поиска через запятую
    @PostMapping("/search")
    public ResponseEntity<List<Category>> searchCategories(@RequestBody CategorySearchValues categorySearchValues) {
        ConsoleLoggerSQL.logMethod("CategoryRepository: searchCategories()");
        // если не найдется ничего - будут показаны все категории
        return ResponseEntity.ok(categoryRepository.findByTitle(categorySearchValues.getTitle()));
    }
}
