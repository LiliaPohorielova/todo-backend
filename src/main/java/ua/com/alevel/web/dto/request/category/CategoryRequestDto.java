package ua.com.alevel.web.dto.request.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.com.alevel.web.dto.request.RequestDto;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequestDto extends RequestDto {

    private String title;
    private Long completedCount;
    private Long uncompletedCount;
}
