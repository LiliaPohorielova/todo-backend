package ua.com.alevel.repository.priority;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.com.alevel.entity.Priority;
import ua.com.alevel.repository.BaseRepository;

import java.util.List;

@Repository
public interface PriorityRepository extends BaseRepository<Priority> {

    @Query( "SELECT p FROM Priority p WHERE " +
            "(:title is null or :title='' or lower(p.title) like lower(concat('%',:title,'%'))) " +
            "ORDER BY p.title ASC")
    List<Priority> findByTitle(@Param("title") String title);

    List<Priority> findAllByOrderByIdAsc();
}
