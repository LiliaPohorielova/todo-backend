package ua.com.alevel.web.dto.request.task;

import lombok.Getter;
import lombok.Setter;
import ua.com.alevel.entity.Category;
import ua.com.alevel.entity.Priority;
import ua.com.alevel.web.dto.request.RequestDto;

import java.util.Date;

@Getter
@Setter
public class TaskRequestDto extends RequestDto {

    private String title;
    private Integer completed;
    private Date date;
    private Priority priority;
    private Category category;
}
