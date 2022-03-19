package ua.com.alevel.controller.statistic;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.com.alevel.entity.Statistic;
import ua.com.alevel.repository.statistic.StatisticRepository;

@RestController
@RequestMapping("statistic")
public class StatisticController {

    private final StatisticRepository statisticRepository;
    private final Long defaultId = 1L;

    public StatisticController(StatisticRepository statisticRepository) {
        this.statisticRepository = statisticRepository;
    }

    @GetMapping()
    public ResponseEntity<Statistic> getCompletedTaskCount() {
        return ResponseEntity.ok(statisticRepository.findById(defaultId).get());
    }
}
