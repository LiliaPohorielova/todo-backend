package ua.com.alevel.facade.category;

import ua.com.alevel.facade.BaseFacade;
import ua.com.alevel.web.dto.request.category.CategoryRequestDto;
import ua.com.alevel.web.dto.response.category.CategoryResponseDto;

import java.util.List;

public interface CategoryFacade extends BaseFacade<CategoryRequestDto, CategoryResponseDto> {

    List<CategoryResponseDto> findByTitle(String text);
    List<CategoryResponseDto> findAllByOrderByTitleAsc();
}
