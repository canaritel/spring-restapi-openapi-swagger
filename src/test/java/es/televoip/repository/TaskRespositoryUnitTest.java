package es.televoip.repository;

import es.televoip.model.Task;
import es.televoip.model.enums.TaskStatus;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;


/*
*  En la clase TaskRepositoryTest, que se enfoca en probar la capa de acceso a datos utilizando JPA, lo ideal es trabajar
*  principalmente con objetos de tipo Task, ya que se está probando cómo las entidades son manejadas por la capa de persistencia.
*  El objetivo principal es verificar que las operaciones de persistencia, como la creación, lectura, actualización y 
*  eliminación de entidades, se realizan correctamente en la base de datos.
*
*  Componentes involucrados: En estas pruebas, interactúas directamente con el EntityManager y el repositorio JPA
*  para realizar operaciones en la base de datos.
*
 */
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD) // indicará al marco de pruebas que debe reinicializar el contexto de la aplicación después de cada prueba
@DataJpaTest(properties = "spring.config.location=classpath:application-test.properties")
//@AutoConfigureTestEntityManager // configura y permite realizar operaciones en la base de datos emulada durante las pruebas, como la inserción y recuperación de datoss
public class TaskRespositoryUnitTest {

   @Autowired
   private TestEntityManager entityManager;

   @Autowired
   private TaskRepository repository;

   @Test
   public void should_create_a_task() {
      // Given
      Task task = Task.builder()
             .description("description1")
             .title("title1")
             .priority(1)
             .isCompleted(Boolean.FALSE)
             .taskStatus(TaskStatus.ON_TIME)
             .build();
      entityManager.persist(task); ////
      // When
      Task newTask = repository.save(task);
      // Then
      assertNotNull(newTask);
      assertEquals(1, newTask.getId());
      assertThat(newTask).hasFieldOrPropertyWithValue("description", "description1");
      assertThat(newTask).hasFieldOrPropertyWithValue("title", "title1");
      assertThat(newTask).hasFieldOrPropertyWithValue("priority", 1);
   }

   @Test
   public void should_find_no_task_if_repository_is_empty() {
      // When
      List tasks = repository.findAll();
      // Then
      assertThat(tasks).isEmpty();
      assertEquals(0, tasks.size());
   }

   @Test
   public void should_find_all_tutorials() {
      Task task1 = Task.builder()
             .description("description1")
             .title("title1")
             .priority(1)
             .isCompleted(Boolean.FALSE)
             .taskStatus(TaskStatus.ON_TIME)
             .build();
      entityManager.persist(task1);

      Task task2 = Task.builder()
             .description("description2")
             .title("title2")
             .priority(2)
             .isCompleted(Boolean.FALSE)
             .taskStatus(TaskStatus.ON_TIME)
             .build();
      entityManager.persist(task2);

      Task task3 = Task.builder()
             .description("description3")
             .title("title3")
             .priority(3)
             .isCompleted(Boolean.FALSE)
             .taskStatus(TaskStatus.ON_TIME)
             .build();
      entityManager.persist(task3);

      List tasks = repository.findAll();

      assertThat(tasks).hasSize(3).contains(task1, task2, task3);
      assertEquals(3, tasks.size());
   }

   @Test
   public void should_find_task_by_id() {
      Task task1 = Task.builder()
             .description("description1")
             .title("title1")
             .priority(1)
             .isCompleted(Boolean.FALSE)
             .taskStatus(TaskStatus.ON_TIME)
             .build();
      entityManager.persist(task1);

      Task task2 = Task.builder()
             .description("description2")
             .title("title2")
             .priority(2)
             .isCompleted(Boolean.FALSE)
             .taskStatus(TaskStatus.ON_TIME)
             .build();
      entityManager.persist(task2);

      Optional<Task> foundTaskOptional = repository.findById(task2.getId());

      assertThat(foundTaskOptional).isPresent(); // Verificar que el Optional contiene un valor
      Task foundTask = foundTaskOptional.get(); // Extraer el objeto Task del Optional
      assertThat(foundTask).isEqualTo(task2);
   }

   @Test
   public void should_update_task_by_id() {
      // Given
      Task task1 = Task.builder()
             .description("description1")
             .title("title1")
             .priority(1)
             .isCompleted(Boolean.FALSE)
             .taskStatus(TaskStatus.ON_TIME)
             .build();
      entityManager.persist(task1);

      Task task2 = Task.builder()
             .description("description2")
             .title("title2")
             .priority(2)
             .isCompleted(Boolean.FALSE)
             .taskStatus(TaskStatus.ON_TIME)
             .build();
      entityManager.persist(task2);

      Task updatedTask = Task.builder()
             .description("description3")
             .title("title3")
             .priority(3)
             .isCompleted(Boolean.FALSE)
             .taskStatus(TaskStatus.ON_TIME)
             .build();
      entityManager.persist(updatedTask);
      // When
      Task task = repository.findById(task2.getId()).get();
      task.setTitle(updatedTask.getTitle());
      task.setDescription(updatedTask.getDescription());
      task.setPriority(updatedTask.getPriority());
      repository.save(task);
      Task checkTask = repository.findById(task2.getId()).get();
      // Then
      assertNotNull(checkTask);
      assertEquals(task2.getTitle(), checkTask.getTitle());
      assertThat(checkTask.getId()).isEqualTo(task2.getId());
      assertThat(checkTask.getTitle()).isEqualTo(updatedTask.getTitle());
      assertThat(checkTask.getDescription()).isEqualTo(updatedTask.getDescription());
      assertThat(checkTask.getPriority()).isEqualTo(updatedTask.getPriority());
   }

   @Test
   public void should_delete_listasks_by_id() {
      // Given
      Task task1 = Task.builder()
             .description("description1")
             .title("title1")
             .priority(1)
             .isCompleted(Boolean.FALSE)
             .taskStatus(TaskStatus.ON_TIME)
             .build();
      entityManager.persist(task1);

      Task task2 = Task.builder()
             .description("description2")
             .title("title2")
             .priority(2)
             .isCompleted(Boolean.FALSE)
             .taskStatus(TaskStatus.ON_TIME)
             .build();
      entityManager.persist(task2);

      Task task3 = Task.builder()
             .description("description3")
             .title("title3")
             .priority(3)
             .isCompleted(Boolean.FALSE)
             .taskStatus(TaskStatus.ON_TIME)
             .build();
      entityManager.persist(task3);
      // When
      repository.deleteById(task2.getId());
      List tutorials = repository.findAll();
      // Then
      assertThat(tutorials).hasSize(2).contains(task1, task3);
   }

   @Test
   void should_delete_task_by_id() {
      // Given
      Task task1 = Task.builder()
             //.id(1L)
             .description("description1")
             .title("title1")
             .priority(1)
             .isCompleted(Boolean.FALSE)
             .taskStatus(TaskStatus.ON_TIME)
             .build();
      entityManager.persist(task1);

      // When
      repository.deleteById(task1.getId());
      Optional<Task> foundTaskOptional = repository.findById(task1.getId());
      // Then
      assertFalse(foundTaskOptional.isPresent());
   }

   @Test
   // en el repository debemos activar @Modifying(clearAutomatically = true) 
   public void testMarkTaskAsCompleted() {
      // Given
      Task task = Task.builder()
             .description("description1")
             .title("title1")
             .priority(3)
             .isCompleted(Boolean.FALSE)
             .taskStatus(TaskStatus.ON_TIME)
             .taskDateCreation(LocalDateTime.now())
             .build();
      entityManager.persist(task);

      // When
      System.out.println("ID: " + task.getId());
      repository.markTaskAsCompleted(task.getId());

      // Then
      Task updatedTask = repository.findById(task.getId()).orElse(null);
      System.out.println(updatedTask.toString());
      assertNotNull(updatedTask);
      assertTrue(updatedTask.getIsCompleted());
   }

   @Test
   public void testFindAllByTaskStatus() {
      Task task1 = Task.builder()
             .description("description1")
             .title("title1")
             .priority(1)
             .isCompleted(Boolean.FALSE)
             .taskStatus(TaskStatus.ON_TIME)
             .build();
      entityManager.persist(task1);

      Task task2 = Task.builder()
             .description("description2")
             .title("title2")
             .priority(2)
             .isCompleted(Boolean.FALSE)
             .taskStatus(TaskStatus.LATE)
             .build();
      entityManager.persist(task2);

      Task task3 = Task.builder()
             .description("description3")
             .title("title3")
             .priority(3)
             .isCompleted(Boolean.FALSE)
             .taskStatus(TaskStatus.ON_TIME)
             .build();
      entityManager.persist(task3);

      List<Task> tasks = repository.findAllByTaskStatus(TaskStatus.ON_TIME);

      assertThat(tasks).hasSize(2).contains(task1, task3);

      for (Task task : tasks) {
         assertThat(task.getTaskStatus()).isEqualTo(TaskStatus.ON_TIME);
         assertThat(task.getPriority()).isLessThanOrEqualTo(3);
         assertThat(task.getIsCompleted()).isFalse();
      }
   }

   @Test
   public void testFindByIsCompletedTrue() {
      // Given
      Task task1 = Task.builder()
             .description("description1")
             .title("title1")
             .priority(1)
             .isCompleted(Boolean.TRUE)
             .taskStatus(TaskStatus.ON_TIME)
             .build();
      entityManager.persist(task1);

      Task task2 = Task.builder()
             .description("description2")
             .title("title2")
             .priority(2)
             .isCompleted(Boolean.FALSE)
             .taskStatus(TaskStatus.LATE)
             .build();
      entityManager.persist(task2);

      // When
      List<Task> completedTasks = repository.findByIsCompletedTrue();

      // Then
      assertThat(completedTasks).hasSize(1).contains(task1);
   }

   @Test
   public void testFindByIsCompletedFalse() {
      // Given
      Task task1 = Task.builder()
             .description("description1")
             .title("title1")
             .priority(1)
             .isCompleted(Boolean.TRUE)
             .taskStatus(TaskStatus.ON_TIME)
             .build();
      entityManager.persist(task1);

      Task task2 = Task.builder()
             .description("description2")
             .title("title2")
             .priority(2)
             .isCompleted(Boolean.FALSE)
             .taskStatus(TaskStatus.LATE)
             .build();
      entityManager.persist(task2);

      // When
      List<Task> incompletedTasks = repository.findByIsCompletedFalse();

      // Then
      assertThat(incompletedTasks).hasSize(1).contains(task2);
   }

   @Test
   public void testFindByTitleContainingIgnoreCase() {
      // Given
      Task task1 = Task.builder()
             .description("description1")
             .title("Find Me")
             .priority(1)
             .isCompleted(Boolean.TRUE)
             .taskStatus(TaskStatus.ON_TIME)
             .build();
      entityManager.persist(task1);

      Task task2 = Task.builder()
             .description("description2")
             .title("title2")
             .priority(2)
             .isCompleted(Boolean.FALSE)
             .taskStatus(TaskStatus.LATE)
             .build();
      entityManager.persist(task2);

      // When
      List<Task> foundTasks = repository.findByTitleContainingIgnoreCase("find");

      // Then
      assertThat(foundTasks).hasSize(1).contains(task1);
   }

}
