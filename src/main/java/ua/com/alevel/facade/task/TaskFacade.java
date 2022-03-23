package ua.com.alevel.facade.task;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import ua.com.alevel.entity.Task;
import ua.com.alevel.facade.BaseFacade;
import ua.com.alevel.web.dto.request.task.TaskRequestDto;
import ua.com.alevel.web.dto.response.task.TaskResponseDto;

public interface TaskFacade extends BaseFacade<TaskRequestDto, TaskResponseDto> {

    Page<Task> findByParams(String text, Integer completed, Long priorityId, Long categoryId, PageRequest paging);
}
