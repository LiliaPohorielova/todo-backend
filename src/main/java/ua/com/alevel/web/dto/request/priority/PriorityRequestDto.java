package ua.com.alevel.web.dto.request.priority;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.com.alevel.web.dto.request.RequestDto;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PriorityRequestDto extends RequestDto {

    private String title;
    private String color;
}
