package es.televoip;

import es.televoip.model.Task;
import es.televoip.model.enums.TaskStatus;
import es.televoip.repository.TaskRepository;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@Slf4j
@SpringBootApplication
public class TodoApiSpringApplication {

   @Value("${spring.datasource.url}")
   private String dataSourceUrl;

   public static void main(String[] args) {
      SpringApplication.run(TodoApiSpringApplication.class, args);
   }

   @Bean
   public CommandLineRunner executeOnStartup(TaskRepository repository) {
      return args -> {
         // Obtén la hora y zona horaria del sistema
         ZonedDateTime systemTime = ZonedDateTime.now();

         log.info("Hora y zona horaria del sistema: {}" + systemTime);

         // Define la zona horaria de Madrid
         ZoneId madridZone = ZoneId.of("Europe/Madrid");
         ZonedDateTime madridTime = ZonedDateTime.now(madridZone);

         log.info("Hora y zona horaria de Madrid: {}" + madridTime);

         // Creamos una lista de tareas
         List<Task> tasks = new ArrayList<>();

         // Insertamos datos en nuestra BD, solo si está vacía y no es la BD en memoria usada para los Test
         if (repository.count() == 0L && !"jdbc:h2:mem:testdb".equals(dataSourceUrl)) {
            // Recorremos el bucle for para crear 1000 tareas
            for (int i = 1; i < 1000; i++) {
               Task task = Task.builder()
                      .description("description" + i)
                      .title("title" + i)
                      .priority(1)
                      .taskStatus(TaskStatus.ON_TIME)
                      .isCompleted(false)
                      .taskDateCreation(LocalDateTime.now()).build();

               tasks.add(task);
            }

            // Guardamos las tareas en la base de datos
            repository.saveAll(tasks);
         }

         // Insertamos datos en nuestra BD, solo si está vacía y no es la BD en memoria usada para los Test
//         if (repository.count() == 0L && !"jdbc:h2:mem:testdb".equals(dataSourceUrl)) {
//            // Añadir lógica para crear y guardar las tareas en la base de datos
//            Task task1 = Task.builder().description("description1").title("title1").priority(1)
//                   .taskStatus(TaskStatus.ON_TIME).isCompleted(false).taskDateCreation(LocalDateTime.now()).build();
//
//            Task task2 = Task.builder().description("description2").title("title2").priority(2)
//                   .taskStatus(TaskStatus.ON_TIME).isCompleted(false).taskDateCreation(LocalDateTime.now()).build();
//
//            Task task3 = Task.builder().description("description3").title("title3").priority(3)
//                   .taskStatus(TaskStatus.ON_TIME).isCompleted(false).taskDateCreation(LocalDateTime.now()).build();
//
//            repository.saveAll(Arrays.asList(task1, task2, task3));
//         }
         log.info("************************   DataSource URL: {}" + dataSourceUrl);
      };
   }

}
