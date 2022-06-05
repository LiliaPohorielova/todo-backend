package ua.com.alevel.web.controller.task;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.com.alevel.entity.Task;
import ua.com.alevel.exception.EntityNotFoundException;
import ua.com.alevel.facade.category.CategoryFacade;
import ua.com.alevel.facade.priority.PriorityFacade;
import ua.com.alevel.facade.task.TaskFacade;
import ua.com.alevel.search.TaskSearchValues;
import ua.com.alevel.util.ConsoleLoggerSQL;
import ua.com.alevel.web.dto.request.task.TaskRequestDto;
import ua.com.alevel.web.dto.response.task.TaskResponseDto;

import java.util.List;

import static ua.com.alevel.util.SortAndPage.*;

@RestController
@RequestMapping("/task")
@CrossOrigin(origins = "http://localhost:4200")
public class TaskController {

    private final TaskFacade taskFacade;
    private final PriorityFacade priorityFacade;
    private final CategoryFacade categoryFacade;

    public TaskController(TaskFacade taskFacade, PriorityFacade priorityFacade, CategoryFacade categoryFacade) {
        this.taskFacade = taskFacade;
        this.priorityFacade = priorityFacade;
        this.categoryFacade = categoryFacade;
    }

    @GetMapping("/all")
    public List<TaskResponseDto> findAll() {
        ConsoleLoggerSQL.logMethod("TaskFacade: findAll()");
        return taskFacade.findAll();
    }

    @PostMapping("/add")
    public ResponseEntity<TaskResponseDto> addTask(@RequestBody TaskRequestDto task) {
        ConsoleLoggerSQL.logMethod("TaskFacade: addTask()");

        // Название нового объекта не должно быть пустым
        if (task.getTitle() == null || task.getTitle().trim().length() == 0) {
            return new ResponseEntity("Task name must not be empty", HttpStatus.NOT_ACCEPTABLE);
        }

        return ResponseEntity.ok(taskFacade.create(task));
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<TaskResponseDto> editTask(@PathVariable Long id, @RequestBody TaskRequestDto taskDTO) {
        ConsoleLoggerSQL.logMethod("TaskFacade: editTask()");
        TaskResponseDto task;

        // Id редактированного объекта НЕ должен быть пустым
        if (id == null || id == 0) {
            return new ResponseEntity("ID of edit task must not be null", HttpStatus.NOT_ACCEPTABLE);
        }

        // Название редактированного объекта не должно быть пустым
        if (taskDTO.getTitle() == null || taskDTO.getTitle().trim().length() == 0) {
            return new ResponseEntity("Task name must not be empty", HttpStatus.NOT_ACCEPTABLE);
        }

        try {
            if (taskDTO.getPriority() != null)
                priorityFacade.findById(taskDTO.getPriority().getId());
        } catch (EntityNotFoundException e) {
            return new ResponseEntity("Priority with id=" + taskDTO.getPriority().getId() + " not found", HttpStatus.NOT_ACCEPTABLE);
        }

        try {
            if (taskDTO.getCategory() != null)
                categoryFacade.findById(taskDTO.getCategory().getId());
        } catch (EntityNotFoundException e) {
            return new ResponseEntity("Category with id=" + taskDTO.getCategory().getId() + " not found", HttpStatus.NOT_ACCEPTABLE);
        }

        try {
            task = taskFacade.update(taskDTO, id);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity("Task with id=" + id + " not found", HttpStatus.NOT_ACCEPTABLE);
        }

        return ResponseEntity.ok(task);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<TaskResponseDto> findById(@PathVariable Long id) {
        ConsoleLoggerSQL.logMethod("TaskFacade: findById()");
        TaskResponseDto task;
        try {
            task = taskFacade.findById(id);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity("Task with id=" + id + " not found", HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(task);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteById(@PathVariable Long id) {
        ConsoleLoggerSQL.logMethod("TaskFacade: deleteById()");
        try {
            taskFacade.delete(id);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity("Task with id=" + id + " not found", HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity(HttpStatus.OK); // просто отправляем статус 200 (операция прошла успешно)
    }

    // поиск по любым параметрам
    // отправляем JSON и по нем будем искать
    // чтобы не перечислять все параметры для поиска через запятую
    @PostMapping("/search")
    public ResponseEntity<Page<Task>> searchTasks(@RequestBody TaskSearchValues taskSearchValues) {
        ConsoleLoggerSQL.logMethod("TaskFacade: searchTasks()");
        // если не найдется ничего - будут показаны все задачи

        // imitateLoading();

        String title = taskSearchValues.getTitle();
        Integer completed = taskSearchValues.getCompleted();
        Long priorityId = taskSearchValues.getPriorityId();
        Long categoryId = taskSearchValues.getCategoryId();

        String sortColumn = taskSearchValues.getSortColumn() != null ? taskSearchValues.getSortColumn() : DEFAULT_SORT_PARAM_VALUE;
        String sortDirection = taskSearchValues.getSortDirection() != null ? taskSearchValues.getSortDirection() : DEFAULT_ORDER_PARAM_VALUE;

        List<String> fields = getFields(Task.class);

        if (!checkSortingColumnIsPresent(Task.class, sortColumn))
            return new ResponseEntity("Sorting column: " + sortColumn + " not found in searched class: " + Task.class.getName() + "\n"
                    + fields,
                                        HttpStatus.NOT_ACCEPTABLE);

        if (!isSortDirection(sortDirection))
            return new ResponseEntity("Sort Direction can be only \"asc\" or \"desc\"",
                    HttpStatus.NOT_ACCEPTABLE);

        Sort.Direction direction = sortDirection.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;

        Sort sort = Sort.by(direction, sortColumn);

        Integer pageNumber = taskSearchValues.getPageNumber() != null ? taskSearchValues.getPageNumber() : DEFAULT_PAGE_PARAM_VALUE;
        Integer pageSize = taskSearchValues.getPageSize() != null ? taskSearchValues.getPageSize() : DEFAULT_SIZE_PARAM_VALUE;

        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);

        Page<Task> result = taskFacade.findByParams(title, completed, priorityId, categoryId, pageRequest);
        return ResponseEntity.ok(result);
    }

//    private void imitateLoading() {
//        try {
//            Thread.sleep(700);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
}
