package ua.com.alevel.service.priority;

import ua.com.alevel.entity.Priority;
import ua.com.alevel.service.BaseCrudService;

import java.util.List;

public interface PriorityService extends BaseCrudService<Priority> {

    List<Priority> findByTitle(String text);
}
