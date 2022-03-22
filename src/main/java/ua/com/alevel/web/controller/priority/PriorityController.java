package ua.com.alevel.web.controller.priority;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.com.alevel.entity.Priority;
import ua.com.alevel.repository.priority.PriorityRepository;
import ua.com.alevel.search.PrioritySearchValues;
import ua.com.alevel.util.ConsoleLoggerSQL;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/priority")
public class PriorityController {

    // Контроллеры напрямую с репозиториями не работают
    // Внедряем ссылку на объект (Dep Inj PriorityRepository)
    private final PriorityRepository priorityRepository;

    public PriorityController(PriorityRepository priorityRepository) {
        this.priorityRepository = priorityRepository;
    }

    @GetMapping("/all")
    public List<Priority> findAll() {
        ConsoleLoggerSQL.logMethod("PriorityRepository: findAll()");
        // return priorityRepository.findAll();
        return priorityRepository.findAllByOrderByIdAsc();
    }

    @PostMapping("/add")
    public ResponseEntity<Priority> addPriority(@RequestBody Priority priority) {
        ConsoleLoggerSQL.logMethod("PriorityRepository: addPriority()");
        // Id нового объекта должен быть пустым
        if (priority.getId() != null && priority.getId() != 0) {
            return new ResponseEntity("ID of new priority must be null", HttpStatus.NOT_ACCEPTABLE);
        }

        // Название нового объекта не должно быть пустым
        if (priority.getTitle() == null || priority.getTitle().trim().length() == 0) {
            return new ResponseEntity("Priority name must not be empty", HttpStatus.NOT_ACCEPTABLE);
        }

        // Цвет нового объекта не должно быть пустым
        if (priority.getColor() == null || priority.getColor().trim().length() == 0) {
            return new ResponseEntity("Priority color must not be empty", HttpStatus.NOT_ACCEPTABLE);
        }

        return ResponseEntity.ok(priorityRepository.save(priority));
    }

    @PutMapping("/edit")
    public ResponseEntity<Priority> editPriority(@RequestBody Priority priority) {
        ConsoleLoggerSQL.logMethod("PriorityRepository: editPriority()");
        // Id редактированного объекта НЕ должен быть пустым
        if (priority.getId() == null || priority.getId() == 0) {
            return new ResponseEntity("ID of edit priority must not be null", HttpStatus.NOT_ACCEPTABLE);
        }

        // Название редактированного объекта не должно быть пустым
        if (priority.getTitle() == null || priority.getTitle().trim().length() == 0) {
            return new ResponseEntity("Priority name must not be empty", HttpStatus.NOT_ACCEPTABLE);
        }

        // Цвет редактированного объекта не должно быть пустым
        if (priority.getColor() == null || priority.getColor().trim().length() == 0) {
            return new ResponseEntity("Priority color must not be empty", HttpStatus.NOT_ACCEPTABLE);
        }

        // Метод save() работает как на создание так и на обновление
        return ResponseEntity.ok(priorityRepository.save(priority));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Priority> findById(@PathVariable Long id) {
        ConsoleLoggerSQL.logMethod("PriorityRepository: findById()");
        Priority priority = null;
        try {
            priority = priorityRepository.findById(id).get();
        } catch (NoSuchElementException e) {
            return new ResponseEntity("Priority with id=" + id + " not found", HttpStatus.NOT_ACCEPTABLE);
        }

        return ResponseEntity.ok(priority);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        ConsoleLoggerSQL.logMethod("PriorityRepository: deleteById()");
        try {
            priorityRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>("Priority with id=" + id + " not found", HttpStatus.NOT_ACCEPTABLE);
        }

        return new ResponseEntity<>("Priority with id=" + id + " was deleted", HttpStatus.OK);
    }

    // поиск по любым параметрам
    // отправляем JSON и по нем будем искать
    // чтобы не перечислять все параметры для поиска через запятую
    @PostMapping("/search")
    public ResponseEntity<List<Priority>> searchPriorities(@RequestBody PrioritySearchValues prioritySearchValues) {
        ConsoleLoggerSQL.logMethod("PriorityRepository: searchPriorities()");
        // если не найдется ничего - будут показаны все категории
        return ResponseEntity.ok(priorityRepository.findByTitle(prioritySearchValues.getTitle()));
    }
}
