package dev.lamia.repository;

import dev.lamia.model.Task;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Date;

public interface TaskRepository extends JpaRepository<Task, Long> {
    // Custom queries if needed

    List<Task> findBySubject(String subject);

    List<Task> findByPriority(String priority);

//    List<Task> findByDate(Date date);

    List<Task> findBySubjectAndPriority(String subject, String priority);

    @Query("SELECT t FROM Task t WHERE t.priority = :priority AND t.date <= :dueDate")
    List<Task> findHighPriorityTasksDueBy(@Param("priority") String priority, @Param("dueDate") Date dueDate);

    List<Task> findAll(Sort sort);
}
