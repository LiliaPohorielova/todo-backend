package ua.com.alevel.service.task.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.alevel.entity.Task;
import ua.com.alevel.exception.EntityNotFoundException;
import ua.com.alevel.repository.task.TaskRepository;
import ua.com.alevel.service.task.TaskService;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public void create(Task entity) {
        taskRepository.save(entity);
    }

    @Override
    public void update(Task entity) {
        checkExist(entity.getId());
        taskRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        checkExist(id);
        taskRepository.deleteById(id);
    }

    @Override
    public Optional<Task> findById(Long id) {
        return taskRepository.findById(id);
    }

    @Override
    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    @Override
    public Page<Task> findByParams(String text, Integer completed, Long taskId, Long categoryId, PageRequest paging) {
        return taskRepository.findByParams(text, completed, taskId, categoryId, paging);
    }

    private void checkExist(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new EntityNotFoundException("Entity not found");
        }
    }
}
