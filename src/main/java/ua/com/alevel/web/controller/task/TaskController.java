package ua.com.alevel.web.controller.task;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.com.alevel.entity.Category;
import ua.com.alevel.entity.Priority;
import ua.com.alevel.entity.Task;
import ua.com.alevel.repository.priority.PriorityRepository;
import ua.com.alevel.repository.task.TaskRepository;
import ua.com.alevel.repository.сategory.CategoryRepository;
import ua.com.alevel.search.TaskSearchValues;
import ua.com.alevel.util.ConsoleLoggerSQL;

import java.util.List;
import java.util.NoSuchElementException;

import static ua.com.alevel.util.SortAndPage.*;

@RestController
@RequestMapping("/task")
public class TaskController {

    private final TaskRepository taskRepository;
    private final PriorityRepository priorityRepository;
    private final CategoryRepository categoryRepository;

    public TaskController(TaskRepository taskRepository, PriorityRepository priorityRepository, CategoryRepository categoryRepository) {
        this.taskRepository = taskRepository;
        this.priorityRepository = priorityRepository;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/all")
    public List<Task> findAll() {
        ConsoleLoggerSQL.logMethod("TaskRepository: findAll()");
        return taskRepository.findAll();
    }

    @PostMapping("/add")
    public ResponseEntity<Task> addTask(@RequestBody Task task) {
        ConsoleLoggerSQL.logMethod("TaskRepository: addTask()");

        // Id нового объекта должен быть пустым
        if (task.getId() != null && task.getId() != 0) {
            return new ResponseEntity("ID of new task must be null", HttpStatus.NOT_ACCEPTABLE);
        }

        // Название нового объекта не должно быть пустым
        if (task.getTitle() == null || task.getTitle().trim().length() == 0) {
            return new ResponseEntity("Task name must not be empty", HttpStatus.NOT_ACCEPTABLE);
        }

        return ResponseEntity.ok(taskRepository.save(task));
    }

    @PutMapping("/edit")
    public ResponseEntity<Task> editTask(@RequestBody Task task) {
        ConsoleLoggerSQL.logMethod("TaskRepository: editTask()");

        // Id редактированного объекта НЕ должен быть пустым
        if (task.getId() == null || task.getId() == 0) {
            return new ResponseEntity("ID of edit task must not be null", HttpStatus.NOT_ACCEPTABLE);
        }

        // Название редактированного объекта не должно быть пустым
        if (task.getTitle() == null || task.getTitle().trim().length() == 0) {
            return new ResponseEntity("Task name must not be empty", HttpStatus.NOT_ACCEPTABLE);
        }

        Priority priority = null;
        try {
            if (task.getPriority() != null)
                priority = priorityRepository.findById(task.getPriority().getId()).get();
        } catch (NoSuchElementException e) {
            return new ResponseEntity("Priority with id=" + task.getPriority().getId() + " not found", HttpStatus.NOT_ACCEPTABLE);
        }

        Category category = null;
        try {
            if (task.getCategory() != null)
                category = categoryRepository.findById(task.getCategory().getId()).get();
        } catch (NoSuchElementException e) {
            return new ResponseEntity("Category with id=" + task.getCategory().getId() + " not found", HttpStatus.NOT_ACCEPTABLE);
        }

        // Метод save() работает как на создание так и на обновление
        return ResponseEntity.ok(taskRepository.save(task));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Task> findById(@PathVariable Long id) {
        ConsoleLoggerSQL.logMethod("TaskRepository: findById()");
        Task task = null;
        try {
            task = taskRepository.findById(id).get();
        } catch (NoSuchElementException e) {
            return new ResponseEntity("Task with id=" + id + " not found", HttpStatus.NOT_ACCEPTABLE);
        }

        return ResponseEntity.ok(task);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        ConsoleLoggerSQL.logMethod("TaskRepository: deleteById()");
        try {
            taskRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>("Task with id=" + id + " not found", HttpStatus.NOT_ACCEPTABLE);
        }

        return new ResponseEntity<>("Task with id=" + id + " was deleted", HttpStatus.OK);
    }

    // поиск по любым параметрам
    // отправляем JSON и по нем будем искать
    // чтобы не перечислять все параметры для поиска через запятую
    @PostMapping("/search")
    public ResponseEntity<Page<Task>> searchTasks(@RequestBody TaskSearchValues taskSearchValues) {
        ConsoleLoggerSQL.logMethod("TaskRepository: searchTasks()");
        // если не найдется ничего - будут показаны все задачи

        String title = taskSearchValues.getTitle();
        Integer completed = taskSearchValues.getCompleted();
        Long priorityId = taskSearchValues.getPriorityId();
        Long categoryId = taskSearchValues.getCategoryId();

        String sortColumn = taskSearchValues.getSortColumn() != null ? taskSearchValues.getSortColumn() : DEFAULT_SORT_PARAM_VALUE;
        String sortDirection = taskSearchValues.getSortDirection() != null ? taskSearchValues.getSortDirection() : DEFAULT_ORDER_PARAM_VALUE;

        if (!checkSortingColumnIsPresent(TaskSearchValues.class, sortColumn))
            return new ResponseEntity("Sorting column: " + sortColumn + " not found in searched class",
                                        HttpStatus.NOT_ACCEPTABLE);

        if (!isSortDirection(sortDirection))
            return new ResponseEntity("Sort Direction can be only \"asc\" or \"desc\"",
                    HttpStatus.NOT_ACCEPTABLE);

        Sort.Direction direction = sortDirection.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;

        Sort sort = Sort.by(direction, sortColumn);

        Integer pageNumber = taskSearchValues.getPageNumber() != null ? taskSearchValues.getPageNumber() : DEFAULT_PAGE_PARAM_VALUE;
        Integer pageSize = taskSearchValues.getPageSize() != null ? taskSearchValues.getPageSize() : DEFAULT_SIZE_PARAM_VALUE;

        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);

        Page<Task> result = taskRepository.findByParams(title, completed, priorityId, categoryId, pageRequest);
        return ResponseEntity.ok(result);
    }
}
