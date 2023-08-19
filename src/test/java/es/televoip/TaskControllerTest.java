package es.televoip;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.televoip.model.dto.TaskDto;
import es.televoip.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TaskControllerTest {

   @Autowired
   private MockMvc mockMvc; // herramienta para simular solicitudes HTTP y verificar las respuestas sin usar server web real

   @MockBean
   private TaskService taskService;

   @Autowired
   private ObjectMapper objectMapper; // clase Jackson que se utiliza para convertir objetos Java en JSON y viceversa

   @Test
   public void testGetTask() throws Exception {
      // Configurar comportamiento del servicio
      TaskDto taskDto = new TaskDto();
      when(taskService.getTask(1L)).thenReturn(taskDto);

      // Realizar la solicitud GET
      mockMvc.perform(get("/api/tasks/1"))
             .andExpect(status().isOk())
             .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            // .andExpect(jsonPath("$.id", is(taskDto.getId())))
             .andExpect(jsonPath("$.title", is(taskDto.getTitle())))
             .andExpect(jsonPath("$.description", is(taskDto.getDescription())));

      // Verificar que el método del servicio se llamó correctamente
      verify(taskService, times(1)).getTask(1L);
   }

   @Test
   public void testCreateTask() throws Exception {
      // Configurar comportamiento del servicio
      TaskDto taskDto = new TaskDto();
      when(taskService.createTask(any(TaskDto.class))).thenReturn(taskDto);

      // Realizar la solicitud POST
      mockMvc.perform(post("/api/tasks")
             .contentType(MediaType.APPLICATION_JSON)
             .content(objectMapper.writeValueAsString(taskDto)))
             .andExpect(status().isCreated())
             .andExpect(content().contentType(MediaType.APPLICATION_JSON))
             //.andExpect(jsonPath("$.id").exists())
             .andExpect(jsonPath("$.id", is(taskDto.getId())))
             .andExpect(jsonPath("$.title", is(taskDto.getTitle())))
             .andExpect(jsonPath("$.description", is(taskDto.getDescription())));

      // Verificar que el método del servicio se llamó correctamente
      verify(taskService, times(1)).createTask(any(TaskDto.class));
   }

}
