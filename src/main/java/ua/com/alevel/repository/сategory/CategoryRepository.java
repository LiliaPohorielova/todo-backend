package ua.com.alevel.repository.сategory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.com.alevel.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> { }
