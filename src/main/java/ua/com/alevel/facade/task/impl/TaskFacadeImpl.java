package ua.com.alevel.facade.task.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ua.com.alevel.entity.Task;
import ua.com.alevel.exception.EntityNotFoundException;
import ua.com.alevel.facade.task.TaskFacade;
import ua.com.alevel.service.task.TaskService;
import ua.com.alevel.web.dto.request.task.TaskRequestDto;
import ua.com.alevel.web.dto.response.task.TaskResponseDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskFacadeImpl implements TaskFacade {

    private final TaskService taskService;

    public TaskFacadeImpl(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public TaskResponseDto create(TaskRequestDto taskRequestDto) {
        Task task = new Task();
        task.setTitle(taskRequestDto.getTitle());
        task.setPriority(taskRequestDto.getPriority());
        task.setCategory(taskRequestDto.getCategory());
        task.setCompleted(taskRequestDto.getCompleted());
        task.setDate(taskRequestDto.getDate());
        taskService.create(task);
        return new TaskResponseDto(task);
    }

    @Override
    public TaskResponseDto update(TaskRequestDto taskRequestDto, Long id) {
        if (taskService.findById(id).isPresent()) {
            Task task = taskService.findById(id).get();
            task.setTitle(taskRequestDto.getTitle());
            task.setPriority(taskRequestDto.getPriority());
            task.setCategory(taskRequestDto.getCategory());
            task.setCompleted(taskRequestDto.getCompleted());
            task.setDate(taskRequestDto.getDate());
            taskService.create(task);
            return new TaskResponseDto(task);
        }
        throw new EntityNotFoundException("Task not found");
    }

    @Override
    public void delete(Long id) {
        taskService.delete(id);
    }

    @Override
    public TaskResponseDto findById(Long id) {
        if (taskService.findById(id).isPresent()) {
            Task task = taskService.findById(id).get();
            return new TaskResponseDto(task);
        }
        throw new EntityNotFoundException("Task not found");
    }

    @Override
    public List<TaskResponseDto> findAll() {
        List<Task> all = taskService.findAll();
        return all.stream().map(TaskResponseDto::new).collect(Collectors.toList());
    }

    @Override
    public Page<Task> findByParams(String text, Integer completed, Long priorityId, Long categoryId, PageRequest paging) {
        return taskService.findByParams(text, completed, priorityId, categoryId, paging);
    }
}
