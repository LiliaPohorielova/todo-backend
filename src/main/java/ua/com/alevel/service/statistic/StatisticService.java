package ua.com.alevel.service.statistic;

import ua.com.alevel.entity.Statistic;

import java.util.Optional;

public interface StatisticService {

     Optional<Statistic> findById(Long id);
}