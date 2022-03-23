package ua.com.alevel.service.statistic.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.alevel.entity.Statistic;
import ua.com.alevel.exception.EntityNotFoundException;
import ua.com.alevel.repository.statistic.StatisticRepository;
import ua.com.alevel.service.statistic.StatisticService;

import java.util.Optional;

@Service
@Transactional
public class StatisticServiceImpl implements StatisticService {

    private final StatisticRepository statisticRepository;

    public StatisticServiceImpl(StatisticRepository statisticRepository) {
        this.statisticRepository = statisticRepository;
    }

    @Override
    public Optional<Statistic> findById(Long id) {
        if (statisticRepository.existsById(id))
            return statisticRepository.findById(id);
        else
            throw new EntityNotFoundException("Statistic not found");
    }
}
