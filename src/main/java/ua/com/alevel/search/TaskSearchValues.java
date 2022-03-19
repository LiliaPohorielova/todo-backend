package ua.com.alevel.search;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskSearchValues {

    private String title;
    private Integer completed;
    private Long priorityId;
    private Long categoryId;
}
