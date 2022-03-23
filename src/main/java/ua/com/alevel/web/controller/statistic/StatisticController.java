package ua.com.alevel.web.controller.statistic;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.com.alevel.facade.statistic.StatisticFacade;
import ua.com.alevel.web.dto.response.statistic.StatisticResponseDto;

@RestController
@RequestMapping("statistic")
public class StatisticController {

    private final StatisticFacade statisticFacade;

    public StatisticController(StatisticFacade statisticFacade) {
        this.statisticFacade = statisticFacade;
    }

    @GetMapping()
    public ResponseEntity<StatisticResponseDto> getCompletedTaskCount() {
        Long defaultId = 1L;
        return ResponseEntity.ok(statisticFacade.findById(defaultId));
    }
}
