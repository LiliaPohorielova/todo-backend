package ua.com.alevel.web.controller.category;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.com.alevel.entity.Category;
import ua.com.alevel.exception.EntityNotFoundException;
import ua.com.alevel.facade.category.CategoryFacade;
import ua.com.alevel.search.CategorySearchValues;
import ua.com.alevel.service.category.CategoryService;
import ua.com.alevel.util.ConsoleLoggerSQL;
import ua.com.alevel.web.dto.request.category.CategoryRequestDto;
import ua.com.alevel.web.dto.response.category.CategoryResponseDto;

import java.util.List;

@RestController
@RequestMapping("/category")
@CrossOrigin(origins = "http://localhost:4200")
public class CategoryController {

    private final CategoryFacade categoryFacade;
    private final CategoryService categoryService;

    public CategoryController(CategoryFacade categoryFacade, CategoryService categoryService) {
        this.categoryFacade = categoryFacade;
        this.categoryService = categoryService;
    }

    @GetMapping("/all")
    public List<CategoryResponseDto> findAll() {
        ConsoleLoggerSQL.logMethod("CategoryRepository: findAll()");
        //return categoryFacade.findAll();
        return categoryFacade.findAllByOrderByTitleAsc();
    }

    @PostMapping("/add")
    public ResponseEntity<CategoryResponseDto> addCategory(@RequestBody CategoryRequestDto category) {
        ConsoleLoggerSQL.logMethod("CategoryRepository: addCategory()");

        // Название нового объекта не должно быть пустым
        if (category.getTitle() == null || category.getTitle().trim().length() == 0) {
            return new ResponseEntity("Category name must not be empty", HttpStatus.NOT_ACCEPTABLE);
        }

        return ResponseEntity.ok(categoryFacade.create(category));
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<CategoryResponseDto> editCategory(@PathVariable Long id, @RequestBody CategoryRequestDto categoryDTO) {
        ConsoleLoggerSQL.logMethod("CategoryRepository: editCategory()");
        CategoryResponseDto category;
        // Id редактированного объекта НЕ должен быть пустым
        if (id == null || id == 0) {
            return new ResponseEntity("ID of edit category must not be null", HttpStatus.NOT_ACCEPTABLE);
        }

        // Название редактированного объекта не должно быть пустым
        if (categoryDTO.getTitle() == null || categoryDTO.getTitle().trim().length() == 0) {
            return new ResponseEntity("Category name must not be empty", HttpStatus.NOT_ACCEPTABLE);
        }

        try {
            category = categoryFacade.update(categoryDTO, id);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity("Category with id=" + id + " not found", HttpStatus.NOT_ACCEPTABLE);
        }

        return ResponseEntity.ok(category);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<CategoryResponseDto> findById(@PathVariable Long id) {
        ConsoleLoggerSQL.logMethod("CategoryRepository: findById()");
        CategoryResponseDto category;
        try {
            category = categoryFacade.findById(id);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity("Category with id=" + id + " not found", HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(category);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteById(@PathVariable Long id) {
        ConsoleLoggerSQL.logMethod("CategoryRepository: deleteById()");
        try {
            categoryService.delete(id);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return new ResponseEntity("Category with id = " + id + " not found", HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity(HttpStatus.OK); // просто отправляем статус 200 (операция прошла успешно)
    }

    // поиск по любым параметрам
    // отправляем JSON и по нем будем искать
    // чтобы не перечислять все параметры для поиска через запятую
    @PostMapping("/search")
    public ResponseEntity<List<Category>> searchCategories(@RequestBody CategorySearchValues categorySearchValues) {
        ConsoleLoggerSQL.logMethod("CategoryRepository: searchCategories()");
        // если не найдется ничего - будут показаны все категории
        //return ResponseEntity.ok(categoryFacade.findByTitle(categorySearchValues.getTitle()));
        return ResponseEntity.ok(categoryService.findByTitle(categorySearchValues.getTitle()));
    }
}
