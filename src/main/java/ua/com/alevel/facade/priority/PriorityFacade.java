package ua.com.alevel.facade.priority;

import ua.com.alevel.facade.BaseFacade;
import ua.com.alevel.web.dto.request.priority.PriorityRequestDto;
import ua.com.alevel.web.dto.response.priority.PriorityResponseDto;

import java.util.List;

public interface PriorityFacade extends BaseFacade<PriorityRequestDto, PriorityResponseDto> {

    List<PriorityResponseDto> findByTitle(String text);
}
