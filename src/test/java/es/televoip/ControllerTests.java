package es.televoip;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.televoip.model.Task;
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
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class ControllerTests {

    @MockBean
    private TaskRepository repository;

    @MockBean // Mock TaskService
    private TaskService taskService;

    @Autowired
    private MockMvc mockMvc; // herramienta para simular solicitudes HTTP y verificar las respuestas sin usar server web real

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

        when(repository.findByTitleContainingIgnoreCase("Boot")).thenReturn(tasks);
        // Verificar que el método findByTitleContaining() se llamó con el parámetro esperado
        // verify(repository, times(1)).findByTitleContaining(titleFilter);
        System.out.println("Lista: " + tasks);

        mockMvc.perform(get("/api/tasks/alltitles/{title}", titleFilter)
                .contentType(MediaType.APPLICATION_JSON))
                //.param("title", "Boot"))
                .andExpect(status().isOk())
                .andDo(print());
        // .andExpect(status().isOk())
        //  .andReturn()
        //  .getResponse()
        //  .getContentAsString();
        //System.out.println("Response JSON: " + response);

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
}
