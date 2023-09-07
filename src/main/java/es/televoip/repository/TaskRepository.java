package es.televoip.repository;

import es.televoip.model.Task;
import es.televoip.model.enums.TaskStatus;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends BaseRepository<Task, Long> {

   @Modifying(clearAutomatically = true) // anotación que indica que se modificará los datos en la base de datos
   @Query("UPDATE Task t SET t.isCompleted = TRUE WHERE t.id = :id")
   public void markTaskAsCompleted(@Param("id") Long id);

   @Query("SELECT t FROM Task t WHERE LOWER(t.title) LIKE LOWER(CONCAT('%', :title, '%'))")
   public List<Task> findAllByTitleContainingIgnoreCase(String title);

   public List<Task> findAllByTaskStatus(TaskStatus status);

   public List<Task> findByIsCompletedTrue();

   public List<Task> findByIsCompletedFalse();

   // Método de consulta Spring Data Query Methods
   public List<Task> findByTitleContainingOrDescriptionContainingAllIgnoreCase(String title, String description);

   // Mismo método usando JPQL
   @Query("SELECT t FROM Task t WHERE LOWER(t.title) LIKE LOWER(CONCAT('%', :title, '%')) "
          + "OR LOWER(t.description) LIKE LOWER(CONCAT('%', :description, '%'))")
   public List<Task> findAllByTitleContainingOrDescriptionContainingAllIgnoreCase(
          @Param("title") String title, @Param("description") String description);

   // Método de consulta Spring Data Query Methods
   public Page<Task> findByTitleContainingOrDescriptionContainingAllIgnoreCase(String title, String description, Pageable page);

   // Mismo método usando JPQL
   @Query("SELECT t FROM Task t WHERE LOWER(t.title) LIKE LOWER(CONCAT('%', :title, '%')) "
          + "OR LOWER(t.description) LIKE LOWER(CONCAT('%', :description, '%'))")
   public Page<Task> findAllByTitleContainingOrDescriptionContainingAllIgnoreCase(
          @Param("title") String title, @Param("description") String description, Pageable pageable);

}
