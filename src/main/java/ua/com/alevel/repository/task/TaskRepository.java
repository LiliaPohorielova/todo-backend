package ua.com.alevel.repository.task;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.com.alevel.entity.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query( "SELECT t FROM Task t WHERE " +
            "(:title is null or :title='' or lower(t.title) like lower(concat('%',:title,'%'))) AND " +
            "(:completed is null or t.completed=:completed)  AND " +
            "(:priorityId is null or t.priority.id=:priorityId)  AND " +
            "(:categoryId is null or t.category.id=:categoryId)")
    Page<Task> findByParams(@Param("title") String title,
                            @Param("completed") Integer completed,
                            @Param("priorityId") Long priorityId,
                            @Param("categoryId") Long categoryId,
                            Pageable pageable);
}
