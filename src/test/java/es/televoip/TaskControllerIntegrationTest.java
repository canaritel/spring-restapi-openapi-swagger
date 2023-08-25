package es.televoip;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.televoip.model.Task;
import es.televoip.model.enums.TaskStatus;
import es.televoip.repository.TaskRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD) // indicará al marco de pruebas que debe reinicializar el contexto de la aplicación después de cada prueba
@SpringBootTest(properties = "spring.config.location=classpath:application-test.properties") // Crea un contexto de aplicación de Spring para la prueba y carga todas las configuraciones de la aplicación
@AutoConfigureMockMvc // configura automáticamente el objeto MockMvc. Es una herramienta que te permite simular solicitudes HTTP y verificar las respuestas recibidas
@AutoConfigureTestEntityManager // configura y permite realizar operaciones en la base de datos emulada durante las pruebas, como la inserción y recuperación de datoss
@Transactional // se asegura que cada prueba se ejecute dentro de una transacción y se revierta al final de la prueba
class TaskControllerIntegrationTest {

   @Autowired
   private TestEntityManager entityManager;

   @Autowired
   private MockMvc mockMvc; // Herramienta para simular solicitudes HTTP y verificar respuestas

   @Autowired
   private ObjectMapper objectMapper; // Clase Jackson para convertir objetos Java en JSON

   @Autowired
   private TaskRepository repository; // Repositorio real

   @Test
   public void shouldCreateTask() throws Exception {
      // Preparar los datos de la tarea
      Task task = Task.builder()
             .description("description1")
             .title("title1")
             .priority(1)
             .isCompleted(Boolean.FALSE)
             .taskStatus(TaskStatus.ON_TIME)
             .build();

      // Realizar la solicitud HTTP de creación de tarea y verificar la respuesta
      mockMvc.perform(post("/api/tasks")
             .contentType(MediaType.APPLICATION_JSON)
             .content(objectMapper.writeValueAsString(task)))
             .andExpect(status().isCreated())
             .andDo(print());

      // Verificar que la tarea se creó en la base de datos
      List<Task> tasksInDatabase = repository.findAll();
      assertThat(tasksInDatabase).hasSize(1);
      assertThat(tasksInDatabase.get(0).getTitle()).isEqualTo(task.getTitle());
      assertThat(tasksInDatabase.get(0).getDescription()).isEqualTo(task.getDescription());
   }

   @Test
   public void shouldRetrieveTaskById() throws Exception {
      // Preparar datos en la base de datos
      long id = 1L;
      Task task = Task.builder()
             // .id(id)
             .description("description1")
             .title("title1")
             .priority(1)
             .isCompleted(Boolean.FALSE)
             .taskStatus(TaskStatus.ON_TIME)
             .build();
      entityManager.persist(task);

      // Realizar la solicitud HTTP para obtener una tarea por ID y verificar la respuesta
      mockMvc.perform(get("/api/tasks/{id}", id)
             .contentType(MediaType.APPLICATION_JSON)
             .content(objectMapper.writeValueAsString(task)))
             .andExpect(status().isOk())
             .andDo(print());

      // Recuperar la tarea de la base de datos utilizando el repositorio
      Task responseTask = repository.findById(id).get();

      // Verificar que los atributos de la tarea coinciden con los esperados
      assertThat(responseTask.getId()).isEqualTo(task.getId());
      assertThat(responseTask.getTitle()).isEqualTo(task.getTitle());
      assertThat(responseTask.getDescription()).isEqualTo(task.getDescription());
      assertThat(responseTask.getPriority()).isEqualTo(task.getPriority());
   }

   @Test
   public void shouldReturnNotFoundForInvalidTaskId() throws Exception {
      // Realizar la solicitud HTTP con un ID de tarea no válido
      mockMvc.perform(get("/api/tasks/{id}", 9999L)
             .contentType(MediaType.APPLICATION_JSON))
             .andExpect(status().isNotFound())
             .andDo(print());
   }

   @Test
   public void shouldReturnListOfTasks() throws Exception {
      // Preparar datos en la base de datos
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

      // Realizar solicitud HTTP para obtener la lista de tareas y verificar la respuesta
      mockMvc.perform(get("/api/tasks/all")
             .contentType(MediaType.APPLICATION_JSON))
             .andExpect(status().isOk())
             .andExpect(jsonPath("$.size()").value(2))
             .andExpect(jsonPath("$[0].title").value(task1.getTitle()))
             .andExpect(jsonPath("$[1].title").value(task2.getTitle()))
             .andExpect(jsonPath("$[0].description").value(task1.getDescription()))
             .andExpect(jsonPath("$[1].description").value(task2.getDescription()))
             .andExpect(jsonPath("$[0].priority").value(task1.getPriority()))
             .andExpect(jsonPath("$[1].priority").value(task2.getPriority()))
            // .andExpect(jsonPath("$[0].isCompleted").value(task1.getIsCompleted()))
            // .andExpect(jsonPath("$[1].iaCompleted").value(task2.getIsCompleted()))
            // .andExpect(jsonPath("$[0].taskStatus").value(task1.getTaskStatus()))
            // .andExpect(jsonPath("$[1].taskStatus").value(task2.getTaskStatus()))
             .andDo(print());

      // Verificar la base de datos utilizando el repositorio
      List<Task> tasks = repository.findAll();
      assertThat(tasks).hasSize(2);
      assertThat(tasks.get(0).getTitle()).isEqualTo(task1.getTitle());
      assertThat(tasks.get(1).getTitle()).isEqualTo(task2.getTitle());
   }

   @Test
   public void shouldUpdateTask() throws Exception {
      // Preparar datos en la base de datos
      Task task = Task.builder()
             .description("description1")
             .title("title1")
             .priority(1)
             .isCompleted(Boolean.FALSE)
             .taskStatus(TaskStatus.ON_TIME)
             .build();
      entityManager.persist(task);

      // Datos actualizados
      Task updatedTask = new Task();
      updatedTask.setDescription("updated-description");
      updatedTask.setTitle("updated-title");
      updatedTask.setPriority(2);
      updatedTask.setIsCompleted(Boolean.TRUE);
      updatedTask.setTaskStatus(TaskStatus.LATE);

      // Realizar la solicitud HTTP para actualizar la tarea
      mockMvc.perform(put("/api/tasks/{id}", task.getId())
             .contentType(MediaType.APPLICATION_JSON)
             .content(objectMapper.writeValueAsString(updatedTask)))
             .andExpect(status().isOk())
             .andExpect(jsonPath("$.title").value(updatedTask.getTitle()))
             .andExpect(jsonPath("$.description").value(updatedTask.getDescription()))
             .andExpect(jsonPath("$.priority").value(updatedTask.getPriority()))
             .andExpect(jsonPath("$.isCompleted").value(updatedTask.getIsCompleted()))
             .andExpect(jsonPath("$.taskStatus").value(updatedTask.getTaskStatus().name()))
             .andDo(print());

      // Verificar que la tarea se actualizó en la base de datos
      Task updatedTaskInDatabase = repository.findById(task.getId()).orElse(null);
      assertThat(updatedTaskInDatabase).isNotNull();
      assertThat(updatedTaskInDatabase.getTitle()).isEqualTo(updatedTask.getTitle());
      assertThat(updatedTaskInDatabase.getDescription()).isEqualTo(updatedTask.getDescription());
   }

}
