package es.televoip;

import es.televoip.model.Task;
import es.televoip.model.enums.TaskStatus;
import es.televoip.repository.TaskRepository;
import java.time.OffsetDateTime;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;


/*
* En la clase JPAUnitTest, que se enfoca en probar la capa de acceso a datos utilizando JPA, lo ideal es trabajar
* principalmente con objetos de tipo Task, ya que se está probando cómo las entidades son manejadas por la capa de persistencia.
*
 */
@DataJpaTest(properties = "spring.config.location=classpath:application-test.properties")
public class JPAUnitTest {

   @Autowired
   private TestEntityManager entityManager;

   @Autowired
   private TaskRepository repository;

   @BeforeEach  // IMPORTANTE para evitar errores, sobre todo cuando insertamos datos previos en nuestra BD
   void setUp() {
      repository.deleteAll();
   }

   @Test
   public void should_find_no_task_if_repository_is_empty() {
      List taks = repository.findAll();

      assertThat(taks).isEmpty();
   }

   @Test
   public void should_a_task() {
      Task task = Task.builder()
             .description("description1")
             .title("title1")
             .priority(1)
             .isCompleted(Boolean.FALSE)
             .taskStatus(TaskStatus.ON_TIME)
             .build();
      entityManager.persist(task); ////

      assertThat(task).hasFieldOrPropertyWithValue("description", "description1");
      assertThat(task).hasFieldOrPropertyWithValue("title", "title1");
      assertThat(task).hasFieldOrPropertyWithValue("priority", 1);
   }

   @Test
   public void should_find_all_tutorials() {
      Task task1 = Task.builder()
             .description("description1")
             .title("title1")
             .priority(1)
             .isCompleted(Boolean.FALSE)
             .taskStatus(TaskStatus.ON_TIME)
             .logDateCreated(OffsetDateTime.now())
             .logLastUpdated(OffsetDateTime.now())
             .build();
      entityManager.persist(task1);

      Task task2 = Task.builder()
             .description("description2")
             .title("title2")
             .priority(2)
             .isCompleted(Boolean.FALSE)
             .taskStatus(TaskStatus.ON_TIME)
             .logDateCreated(OffsetDateTime.now())
             .logLastUpdated(OffsetDateTime.now())
             .build();
      entityManager.persist(task2);

      Task task3 = Task.builder()
             .description("description3")
             .title("title3")
             .priority(3)
             .isCompleted(Boolean.FALSE)
             .taskStatus(TaskStatus.ON_TIME)
             .logDateCreated(OffsetDateTime.now())
             .logLastUpdated(OffsetDateTime.now())
             .build();
      entityManager.persist(task3);

      List taks = repository.findAll();

      assertThat(taks).hasSize(3).contains(task1, task2, task3);
   }

   @Test
   public void should_find_task_by_id() {
      Task task1 = Task.builder()
             .description("description1")
             .title("title1")
             .priority(1)
             .isCompleted(Boolean.FALSE)
             .taskStatus(TaskStatus.ON_TIME)
             .logDateCreated(OffsetDateTime.now())
             .logLastUpdated(OffsetDateTime.now())
             .build();
      entityManager.persist(task1);

      Task task2 = Task.builder()
             .description("description2")
             .title("title2")
             .priority(2)
             .isCompleted(Boolean.FALSE)
             .taskStatus(TaskStatus.ON_TIME)
             .logDateCreated(OffsetDateTime.now())
             .logLastUpdated(OffsetDateTime.now())
             .build();
      entityManager.persist(task2);

      Task foundTask = repository.findById(task2.getId()).get();

      assertThat(foundTask).isEqualTo(task2);
   }

   @Test
   public void should_find_status_task() {
      Task task1 = Task.builder()
             .description("description1")
             .title("title1")
             .priority(1)
             .isCompleted(Boolean.FALSE)
             .taskStatus(TaskStatus.ON_TIME)
             .logDateCreated(OffsetDateTime.now())
             .logLastUpdated(OffsetDateTime.now())
             .build();
      entityManager.persist(task1);

      Task task2 = Task.builder()
             .description("description2")
             .title("title2")
             .priority(2)
             .isCompleted(Boolean.FALSE)
             .taskStatus(TaskStatus.LATE)
             .logDateCreated(OffsetDateTime.now())
             .logLastUpdated(OffsetDateTime.now())
             .build();
      entityManager.persist(task2);

      Task task3 = Task.builder()
             .description("description3")
             .title("title3")
             .priority(3)
             .isCompleted(Boolean.FALSE)
             .taskStatus(TaskStatus.ON_TIME)
             .logDateCreated(OffsetDateTime.now())
             .logLastUpdated(OffsetDateTime.now())
             .build();
      entityManager.persist(task3);

      List tasks = repository.findAllByTaskStatus(TaskStatus.ON_TIME);

      assertThat(tasks).hasSize(2).contains(task1, task3);
   }

   @Test
   public void should_update_task_by_id() {
      Task task1 = Task.builder()
             .description("description1")
             .title("title1")
             .priority(1)
             .isCompleted(Boolean.FALSE)
             .taskStatus(TaskStatus.ON_TIME)
             .logDateCreated(OffsetDateTime.now())
             .logLastUpdated(OffsetDateTime.now())
             .build();
      entityManager.persist(task1);

      Task task2 = Task.builder()
             .description("description2")
             .title("title2")
             .priority(2)
             .isCompleted(Boolean.FALSE)
             .taskStatus(TaskStatus.ON_TIME)
             .logDateCreated(OffsetDateTime.now())
             .logLastUpdated(OffsetDateTime.now())
             .build();
      entityManager.persist(task2);

      Task updatedTask = Task.builder()
             .description("description3")
             .title("title3")
             .priority(3)
             .isCompleted(Boolean.FALSE)
             .taskStatus(TaskStatus.ON_TIME)
             .logDateCreated(OffsetDateTime.now())
             .logLastUpdated(OffsetDateTime.now())
             .build();
      entityManager.persist(updatedTask);

      Task task = repository.findById(task2.getId()).get();
      task.setTitle(updatedTask.getTitle());
      task.setDescription(updatedTask.getDescription());
      task.setPriority(updatedTask.getPriority());
      repository.save(task);

      Task checkTask = repository.findById(task2.getId()).get();

      assertThat(checkTask.getId()).isEqualTo(task2.getId());
      assertThat(checkTask.getTitle()).isEqualTo(updatedTask.getTitle());
      assertThat(checkTask.getDescription()).isEqualTo(updatedTask.getDescription());
      assertThat(checkTask.getPriority()).isEqualTo(updatedTask.getPriority());
   }

   @Test
   public void should_delete_task_by_id() {
      Task task1 = Task.builder()
             .description("description1")
             .title("title1")
             .priority(1)
             .isCompleted(Boolean.FALSE)
             .taskStatus(TaskStatus.ON_TIME)
             .logDateCreated(OffsetDateTime.now())
             .logLastUpdated(OffsetDateTime.now())
             .build();
      entityManager.persist(task1);

      Task task2 = Task.builder()
             .description("description2")
             .title("title2")
             .priority(2)
             .isCompleted(Boolean.FALSE)
             .taskStatus(TaskStatus.ON_TIME)
             .logDateCreated(OffsetDateTime.now())
             .logLastUpdated(OffsetDateTime.now())
             .build();
      entityManager.persist(task2);

      Task task3 = Task.builder()
             .description("description3")
             .title("title3")
             .priority(3)
             .isCompleted(Boolean.FALSE)
             .taskStatus(TaskStatus.ON_TIME)
             .logDateCreated(OffsetDateTime.now())
             .logLastUpdated(OffsetDateTime.now())
             .build();
      entityManager.persist(task3);

      repository.deleteById(task2.getId());

      List tutorials = repository.findAll();

      assertThat(tutorials).hasSize(2).contains(task1, task3);
   }

}
