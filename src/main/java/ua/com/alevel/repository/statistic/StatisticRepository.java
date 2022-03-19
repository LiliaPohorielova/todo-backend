package ua.com.alevel.repository.statistic;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.com.alevel.entity.Statistic;

@Repository
public interface StatisticRepository extends JpaRepository<Statistic, Long> { }
