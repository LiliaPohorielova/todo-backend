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

    // поля для поиска и сортировки
    private Long id;
    private String title;
    private Integer completed;
    private Long priorityId;
    private Long categoryId;

    // постраничность
    private Integer pageNumber;
    private Integer pageSize;

    // сортировка
    private String sortColumn;
    private String sortDirection;
}
