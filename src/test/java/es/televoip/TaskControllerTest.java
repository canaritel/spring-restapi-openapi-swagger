package es.televoip;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.televoip.model.Task;
import es.televoip.model.dto.TaskDto;
import es.televoip.model.enums.TaskStatus;
import es.televoip.repository.TaskRepository;
import es.televoip.service.TaskService;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class TaskControllerTest {

   @Autowired
   private MockMvc mockMvc; // herramienta para simular solicitudes HTTP y verificar las respuestas sin usar server web real

   @MockBean
   private TaskRepository repository;

   @Autowired
   private TaskService service; // Usa el servicio real

   @Autowired
   private ObjectMapper objectMapper; // clase Jackson que se utiliza para convertir objetos Java en JSON y viceversa

   @Test
   public void shouldCreateTask() throws Exception {
      Task task = Task.builder()
             .description("description1")
             .title("title1")
             .priority(1)
             .isCompleted(Boolean.FALSE)
             .taskStatus(TaskStatus.ON_TIME)
             .logDateCreated(OffsetDateTime.now())
             .logLastUpdated(OffsetDateTime.now())
             .build();

      mockMvc.perform(post("/api/tasks").contentType(MediaType.APPLICATION_JSON)
             .content(objectMapper.writeValueAsString(task)))
             .andExpect(status().isCreated())
             .andDo(print());
   }

   @Test
   public void shouldReturnTask() throws Exception {
      long id = 1L;
      Task task = Task.builder()
             .description("description1")
             .title("title1")
             .priority(1)
             .isCompleted(Boolean.FALSE)
             .taskStatus(TaskStatus.ON_TIME)
             .taskDateCreation(LocalDateTime.now().withNano(0))
             .logDateCreated(OffsetDateTime.now())
             .logLastUpdated(OffsetDateTime.now())
             .build();

      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

      when(repository.findById(id)).thenReturn(Optional.of(task));
      mockMvc.perform(get("/api/tasks/{id}", id).contentType(MediaType.APPLICATION_JSON)
             .content(objectMapper.writeValueAsString(task)))
             //.andExpect(jsonPath("$.id").value(id))
             .andExpect(jsonPath("$.title").value(task.getTitle()))
             .andExpect(jsonPath("$.description").value(task.getDescription()))
             .andExpect(jsonPath("$.priority").value(task.getPriority()))
             .andExpect(jsonPath("$.taskStatus").value(task.getTaskStatus().name()))
             .andExpect(jsonPath("$.isCompleted").value(task.getIsCompleted()))
             .andExpect(jsonPath("$.taskDateCreation").value(task.getTaskDateCreation().format(formatter)))
             .andDo(print());
   }

   @Test
   void shouldReturnNotFoundTask() throws Exception {
      long id = 1L;

      when(repository.findById(id)).thenReturn(Optional.empty());
      mockMvc.perform(get("/api/tasks/{id}", id))
             .andExpect(status().isNotFound())
             .andDo(print());
   }

   @Test
   void shouldReturnListOfTasks() throws Exception {
      List<Task> tasks = new ArrayList<>();

      Task task1 = Task.builder()
             .description("description1")
             .title("title1")
             .priority(1)
             .isCompleted(Boolean.FALSE)
             .taskStatus(TaskStatus.ON_TIME)
             .logDateCreated(OffsetDateTime.now())
             .logLastUpdated(OffsetDateTime.now())
             .build();

      Task task2 = Task.builder()
             .description("description2")
             .title("title2")
             .priority(2)
             .isCompleted(Boolean.FALSE)
             .taskStatus(TaskStatus.ON_TIME)
             .logDateCreated(OffsetDateTime.now())
             .logLastUpdated(OffsetDateTime.now())
             .build();

      Task task3 = Task.builder()
             .description("description3")
             .title("title3")
             .priority(3)
             .isCompleted(Boolean.FALSE)
             .taskStatus(TaskStatus.ON_TIME)
             .logDateCreated(OffsetDateTime.now())
             .logLastUpdated(OffsetDateTime.now())
             .build();

      tasks.add(task1);
      tasks.add(task2);
      tasks.add(task3);

      when(repository.findAll()).thenReturn(tasks);
      mockMvc.perform(get("/api/tasks/all"))
             .andExpect(status().isOk())
             .andExpect(jsonPath("$.size()").value(tasks.size()))
             .andDo(print());
   }

   @Test
   void shouldReturnListOfTasksWithFilter2() throws Exception {
      List<Task> tasks = new ArrayList<>();

      Task task1 = Task.builder()
             .description("description1")
             .title("Spring Boot")
             .priority(1)
             .isCompleted(Boolean.FALSE)
             .taskStatus(TaskStatus.ON_TIME)
             .logDateCreated(OffsetDateTime.now())
             .logLastUpdated(OffsetDateTime.now())
             .build();

      Task task3 = Task.builder()
             .description("description3")
             .title("Spring Boot")
             .priority(3)
             .isCompleted(Boolean.FALSE)
             .taskStatus(TaskStatus.ON_TIME)
             .logDateCreated(OffsetDateTime.now())
             .logLastUpdated(OffsetDateTime.now())
             .build();

      tasks.add(task1);
      tasks.add(task3);

      String titleFilter = "Boot";
//      MultiValueMap<String, String> paramsMap = new LinkedMultiValueMap<>();
//      paramsMap.add("title", title);

      when(repository.findByTitleContainingIgnoreCase(titleFilter)).thenReturn(tasks);
      // Verificar que el método findByTitleContaining() se llamó con el parámetro esperado
      // verify(repository, times(1)).findByTitleContaining(titleFilter);

      String response = mockMvc.perform(get("/api/tasks/all/titles").contentType(MediaType.APPLICATION_JSON)
             .param("title", titleFilter))
             .andExpect(status().isOk())
             .andReturn()
             .getResponse()
             .getContentAsString();
      System.out.println("Response JSON: " + response);

//      mockMvc.perform(get("/api/tasks/all").params(paramsMap))
//             .andExpect(status().isOk())
//             .andExpect(jsonPath("$.size()").value(tasks.size()))
//             .andDo(print());
//      tasks = Collections.emptyList();
//
//      when(repository.findByTitleContaining(title)).thenReturn(tasks);
//      mockMvc.perform(get("/api/tasks/all").params(paramsMap))
//             .andExpect(status().isNoContent())
//             .andDo(print());
   }

   @Test
   void test1() throws Exception {
      List<Task> tasks = new ArrayList<>();

      Task task1 = Task.builder()
             .description("description1")
             .title("Spring Boot")
             .priority(1)
             .isCompleted(Boolean.FALSE)
             .taskStatus(TaskStatus.ON_TIME)
             .logDateCreated(OffsetDateTime.now())
             .logLastUpdated(OffsetDateTime.now())
             .build();

      Task task3 = Task.builder()
             .description("description3")
             .title("Spring Boot")
             .priority(3)
             .isCompleted(Boolean.FALSE)
             .taskStatus(TaskStatus.ON_TIME)
             .logDateCreated(OffsetDateTime.now())
             .logLastUpdated(OffsetDateTime.now())
             .build();

      tasks.add(task1);
      tasks.add(task3);

      mockMvc.perform(get("/api/tasks/all").contentType(MediaType.APPLICATION_JSON))
             // .param("title", "Boot"))
             .andExpect(status().isOk())
             .andExpect(MockMvcResultMatchers
                    .content()
                    .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
   }

   @Test
   void shouldReturnListOfTasksWithFilter() throws Exception {
      List<TaskDto> taskDtos = new ArrayList<>();

      TaskDto taskDto1 = new TaskDto();
      taskDto1.setDescription("description1");
      taskDto1.setTitle("Spring Boot");
      taskDto1.setPriority(1);
      taskDto1.setIsCompleted(Boolean.FALSE);
      taskDto1.setTaskStatus(TaskStatus.ON_TIME);

      TaskDto taskDto2 = new TaskDto();
      taskDto2.setDescription("description3");
      taskDto2.setTitle("Spring Boot");
      taskDto2.setPriority(3);
      taskDto2.setIsCompleted(Boolean.FALSE);
      taskDto2.setTaskStatus(TaskStatus.ON_TIME);

      taskDtos.add(taskDto1);
      taskDtos.add(taskDto2);

      String titleFilter = "Boot";

      // Simula el comportamiento del servicio
      when(service.getAllTasksByTitleContaining(titleFilter)).thenReturn(taskDtos);

      String response = mockMvc.perform(get("/api/tasks/all/titles").contentType(MediaType.APPLICATION_JSON)
             .param("title", titleFilter))
             .andExpect(status().isOk())
             .andReturn()
             .getResponse()
             .getContentAsString();
      System.out.println("Response JSON: " + response);

      // Verifica que el método del servicio se llamó con el parámetro correcto
      verify(service).getAllTasksByTitleContaining(titleFilter);
   }

}
