package ua.com.alevel.web.dto.request.statistic;

import lombok.Getter;
import lombok.Setter;
import ua.com.alevel.web.dto.request.RequestDto;

@Getter
@Setter
public class StatisticRequestDto extends RequestDto {

    private Long completedTotal;
    private Long uncompletedTotal;
}
