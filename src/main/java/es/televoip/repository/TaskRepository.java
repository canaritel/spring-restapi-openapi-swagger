package es.televoip.repository;

import es.televoip.model.Task;
import es.televoip.model.enums.TaskStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

   @Modifying(clearAutomatically = true) // anotación que indica que se modificará los datos en la base de datos
   @Query("UPDATE Task t SET t.isCompleted = TRUE WHERE t.id = :id")
   public void markTaskAsCompleted(@Param("id") Long id);

   @Query("SELECT t FROM Task t WHERE LOWER(t.title) LIKE LOWER(CONCAT('%', :title, '%'))")
   List<Task> getAllByTitleContainingIgnoreCase(String title);

   public List<Task> findAllByTaskStatus(TaskStatus status);

   public List<Task> findByIsCompletedTrue();

   public List<Task> findByIsCompletedFalse();

}
