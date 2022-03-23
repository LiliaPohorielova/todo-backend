package ua.com.alevel.service.priority.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.entity.Priority;
import ua.com.alevel.exception.EntityNotFoundException;
import ua.com.alevel.repository.priority.PriorityRepository;
import ua.com.alevel.service.priority.PriorityService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PriorityServiceImpl implements PriorityService {
    
    private final PriorityRepository priorityRepository;

    public PriorityServiceImpl(PriorityRepository priorityRepository) {
        this.priorityRepository = priorityRepository;
    }

    @Override
    public void create(Priority entity) {
        priorityRepository.save(entity);
    }

    @Override
    public void update(Priority entity) {
        checkExist(entity.getId());
        priorityRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        checkExist(id);
        priorityRepository.deleteById(id);
    }

    @Override
    public Optional<Priority> findById(Long id) {
        return priorityRepository.findById(id);
    }

    @Override
    public List<Priority> findAll() {
        return priorityRepository.findAll();
    }

    @Override
    public List<Priority> findByTitle(String text){
        return priorityRepository.findByTitle(text);
    }

    private void checkExist(Long id) {
        if (!priorityRepository.existsById(id)) {
            throw new EntityNotFoundException("Entity not found");
        }
    }
}
