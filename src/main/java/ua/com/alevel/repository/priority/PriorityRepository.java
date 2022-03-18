package ua.com.alevel.repository.priority;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.com.alevel.entity.Priority;

@Repository
public interface PriorityRepository extends JpaRepository<Priority, Long> { }
