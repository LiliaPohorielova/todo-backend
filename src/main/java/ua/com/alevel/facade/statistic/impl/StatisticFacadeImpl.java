package ua.com.alevel.facade.statistic.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.entity.Statistic;
import ua.com.alevel.exception.EntityNotFoundException;
import ua.com.alevel.facade.statistic.StatisticFacade;
import ua.com.alevel.service.statistic.StatisticService;
import ua.com.alevel.web.dto.response.statistic.StatisticResponseDto;

@Service
public class StatisticFacadeImpl implements StatisticFacade {

    private final StatisticService statisticService;

    public StatisticFacadeImpl(StatisticService statisticService) {
        this.statisticService = statisticService;
    }

    @Override
    public StatisticResponseDto findById(Long id) {
        if (statisticService.findById(id).isPresent()) {;
            Statistic statistic = statisticService.findById(id).get();
            return new StatisticResponseDto(statistic);
        }
        throw new EntityNotFoundException("Statistic not found");
    }
}
