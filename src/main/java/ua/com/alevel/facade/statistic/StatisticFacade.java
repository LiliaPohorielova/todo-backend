package ua.com.alevel.facade.statistic;

import ua.com.alevel.web.dto.response.statistic.StatisticResponseDto;

public interface StatisticFacade {

    StatisticResponseDto findById(Long id);
}
