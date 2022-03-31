package ua.com.alevel.repository.task;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.com.alevel.entity.Task;
import ua.com.alevel.repository.BaseRepository;

@Repository
public interface TaskRepository extends BaseRepository<Task> {

    @Query("SELECT p FROM Task p where " +
            "(:title is null or :title='' or lower(p.title) like lower(concat('%', :title,'%'))) and" +
            "(:completed is null or p.completed=:completed) and " +
            "(:priorityId is null or p.priority.id=:priorityId) and " +
            "(:categoryId is null or p.category.id=:categoryId)"
    )
    Page<Task> findByParams(@Param("title") String title,
                            @Param("completed") Integer completed,
                            @Param("priorityId") Long priorityId,
                            @Param("categoryId") Long categoryId,
                            Pageable pageable
    );
}
