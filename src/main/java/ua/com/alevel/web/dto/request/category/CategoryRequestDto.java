package ua.com.alevel.web.dto.request.category;

import lombok.Getter;
import lombok.Setter;
import ua.com.alevel.web.dto.request.RequestDto;

@Getter
@Setter
public class CategoryRequestDto extends RequestDto {

    private String title;
    private Long completedCount;
    private Long uncompletedCount;
}
