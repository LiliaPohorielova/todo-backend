package ua.com.alevel.facade.category.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.entity.Category;
import ua.com.alevel.exception.EntityNotFoundException;
import ua.com.alevel.facade.category.CategoryFacade;
import ua.com.alevel.service.category.CategoryService;
import ua.com.alevel.web.dto.request.category.CategoryRequestDto;
import ua.com.alevel.web.dto.response.category.CategoryResponseDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryFacadeImpl implements CategoryFacade {

    private final CategoryService categoryService;

    public CategoryFacadeImpl(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public CategoryResponseDto create(CategoryRequestDto categoryRequestDto) {
        Category category = new Category();
        category.setTitle(categoryRequestDto.getTitle());
        category.setCompletedCount(categoryRequestDto.getCompletedCount());
        category.setUncompletedCount(categoryRequestDto.getUncompletedCount());
        categoryService.create(category);
        return new CategoryResponseDto(category);
    }

    @Override
    public CategoryResponseDto update(CategoryRequestDto categoryRequestDto, Long id) {
        if (categoryService.findById(id).isPresent()) {
            Category category = categoryService.findById(id).get();
            category.setTitle(categoryRequestDto.getTitle());
            category.setCompletedCount(categoryRequestDto.getCompletedCount());
            category.setUncompletedCount(categoryRequestDto.getUncompletedCount());
            categoryService.create(category);
            return new CategoryResponseDto(category);
        }
        throw new EntityNotFoundException("Category not found");
    }

    @Override
    public void delete(Long id) {
        categoryService.delete(id);
    }

    @Override
    public CategoryResponseDto findById(Long id) {
        if (categoryService.findById(id).isPresent()) {
            Category category = categoryService.findById(id).get();
            return new CategoryResponseDto(category);
        }
        throw new EntityNotFoundException("Category not found");
    }

    @Override
    public List<CategoryResponseDto> findAll() {
        List<Category> all = categoryService.findAll();
        return all.stream().map(CategoryResponseDto::new).collect(Collectors.toList());
    }

    @Override
    public List<CategoryResponseDto> findByTitle(String text) {
        List<Category> all = categoryService.findByTitle(text);
        return all.stream().map(CategoryResponseDto::new).collect(Collectors.toList());
    }

    @Override
    public List<CategoryResponseDto> findAllByOrderByTitleAsc() {
        List<Category> all = categoryService.findAll();
        return all.stream().map(CategoryResponseDto::new).collect(Collectors.toList());
    }
}
