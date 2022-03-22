package ua.com.alevel.web.dto.request.priority;

import lombok.Getter;
import lombok.Setter;
import ua.com.alevel.web.dto.request.RequestDto;

@Getter
@Setter
public class PriorityRequestDto extends RequestDto {

    private String title;
    private String color;
}
