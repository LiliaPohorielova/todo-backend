package ua.com.alevel.repository.сategory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.com.alevel.entity.Category;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    // Поиск с помощью JPQL -> подключить ElasticSearch
    // Если title пустой - показываем все значения
    // lower - приводим все буквы к нижнему регистру
    @Query( "SELECT c FROM Category c WHERE " +
            "(:title is null or :title='' or lower(c.title) like lower(concat('%',:title,'%'))) " +
            "ORDER BY c.title ASC")
    List<Category> findByTitle(@Param("title") String title);

    List<Category> findAllByOrderByTitleAsc();
}
