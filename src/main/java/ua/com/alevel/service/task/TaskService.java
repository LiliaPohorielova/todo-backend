package ua.com.alevel.service.task;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import ua.com.alevel.entity.Task;
import ua.com.alevel.service.BaseCrudService;

public interface TaskService extends BaseCrudService<Task> {

    Page<Task> findByParams(String text, Integer completed, Long priorityId, Long categoryId, PageRequest paging);
}
