package ua.com.alevel.controller.task;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
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
    public ResponseEntity<List<Task>> searchTasks(@RequestBody TaskSearchValues taskSearchValues) {
        ConsoleLoggerSQL.logMethod("TaskRepository: searchTasks()");
        // если не найдется ничего - будут показаны все задачи

        // Исключаем NullPointerException
        String title = taskSearchValues.getTitle() != null ? taskSearchValues.getTitle() : null;
        Integer completed = taskSearchValues.getCompleted() != null ? taskSearchValues.getCompleted() : null;
        Long priorityId = taskSearchValues.getPriorityId() != null ? taskSearchValues.getPriorityId() : null;
        Long categoryId = taskSearchValues.getCategoryId() != null ? taskSearchValues.getCategoryId() : null;

        return ResponseEntity.ok(taskRepository.findByParams(title, completed, priorityId, categoryId));
    }
}
