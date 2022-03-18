package ua.com.alevel.controller.priority;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.com.alevel.entity.Priority;
import ua.com.alevel.repository.priority.PriorityRepository;

import java.util.List;

@RestController
@RequestMapping("/priority")
public class PriorityController {

    // Контроллеры напрямую с репозиториями не работают
    // Внедряем ссылку на объект (Dep Inj PriorityRepository)
    private final PriorityRepository priorityRepository;

    public PriorityController(PriorityRepository priorityRepository) {
        this.priorityRepository = priorityRepository;
    }

    @GetMapping("/test")
    public List<Priority> test() {
        return priorityRepository.findAll();
    }
}
