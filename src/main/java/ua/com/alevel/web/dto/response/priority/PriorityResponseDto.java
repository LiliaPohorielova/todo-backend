package ua.com.alevel.web.dto.response.priority;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.com.alevel.entity.Priority;
import ua.com.alevel.web.dto.response.ResponseDto;

@Getter
@Setter
@NoArgsConstructor
public class PriorityResponseDto extends ResponseDto {

    private Long id;
    private String title;
    private String color;

    public PriorityResponseDto(Priority priority) {
        if (priority != null) {
            this.id = priority.getId();
            this.title = priority.getTitle();
            this.color = priority.getColor();
        }
    }
}
