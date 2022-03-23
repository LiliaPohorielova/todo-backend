package ua.com.alevel.facade.priority.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.entity.Priority;
import ua.com.alevel.exception.EntityNotFoundException;
import ua.com.alevel.facade.priority.PriorityFacade;
import ua.com.alevel.service.priority.PriorityService;
import ua.com.alevel.web.dto.request.priority.PriorityRequestDto;
import ua.com.alevel.web.dto.response.priority.PriorityResponseDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PriorityFacadeImpl implements PriorityFacade {

    private final PriorityService priorityService;

    public PriorityFacadeImpl(PriorityService priorityService) {
        this.priorityService = priorityService;
    }

    @Override
    public PriorityResponseDto create(PriorityRequestDto priorityRequestDto) {
        Priority priority = new Priority();
        priority.setTitle(priorityRequestDto.getTitle());
        priority.setColor(priorityRequestDto.getColor());
        priorityService.create(priority);
        return new PriorityResponseDto(priority);
    }

    @Override
    public PriorityResponseDto update(PriorityRequestDto priorityRequestDto, Long id) {
        if (priorityService.findById(id).isPresent()) {
            Priority priority = priorityService.findById(id).get();
            priority.setTitle(priorityRequestDto.getTitle());
            priority.setColor(priorityRequestDto.getColor());
            priorityService.create(priority);
            return new PriorityResponseDto(priority);
        }
        throw new EntityNotFoundException("Priority not found");
    }

    @Override
    public void delete(Long id) {
        priorityService.delete(id);
    }

    @Override
    public PriorityResponseDto findById(Long id) {
        if (priorityService.findById(id).isPresent()) {
            Priority priority = priorityService.findById(id).get();
            return new PriorityResponseDto(priority);
        }
        throw new EntityNotFoundException("Priority not found");
    }

    @Override
    public List<PriorityResponseDto> findAll() {
        List<Priority> all = priorityService.findAll();
        return all.stream().map(PriorityResponseDto::new).collect(Collectors.toList());
    }

    @Override
    public List<PriorityResponseDto> findByTitle(String text) {
        List<Priority> all = priorityService.findByTitle(text);
        return all.stream().map(PriorityResponseDto::new).collect(Collectors.toList());
    }
}
