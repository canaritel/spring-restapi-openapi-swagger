package es.televoip;

import es.televoip.model.Task;
import es.televoip.model.enums.TaskStatus;
import es.televoip.repository.TaskRepository;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TodoApiSpringApplication {

   public static void main(String[] args) {
      SpringApplication.run(TodoApiSpringApplication.class, args);
   }

   @Bean
   public CommandLineRunner executeOnStartup(TaskRepository repository) {
      return args -> {
         // Obtén la hora y zona horaria del sistema
         ZonedDateTime systemTime = ZonedDateTime.now();
         System.out.println("Hora y zona horaria del sistema: " + systemTime);

         // Define la zona horaria de Madrid
         ZoneId madridZone = ZoneId.of("Europe/Madrid");
         ZonedDateTime madridTime = ZonedDateTime.now(madridZone);
         System.out.println("Hora y zona horaria de Madrid: " + madridTime);

         // Insertamos datos en nuestra BD, solo si está vacía
         if (repository.count() == 0L) {
            // Añadir lógica para crear y guardar las tareas en la base de datos
            Task task1 = Task.builder()
                   .description("description1")
                   .title("title1")
                   .priority(1)
                   .taskStatus(TaskStatus.ON_TIME)
                   .isCompleted(false)
                   .taskDateCreation(LocalDateTime.now())
                   .logDateCreated(OffsetDateTime.now())
                   .logLastUpdated(OffsetDateTime.now())
                   .build();

            Task task2 = Task.builder()
                   .description("description2")
                   .title("title2")
                   .priority(2)
                   .taskStatus(TaskStatus.ON_TIME)
                   .isCompleted(false)
                   .taskDateCreation(LocalDateTime.now())
                   .logDateCreated(OffsetDateTime.now())
                   .logLastUpdated(OffsetDateTime.now())
                   .build();

            Task task3 = Task.builder()
                   .description("description3")
                   .title("title3")
                   .priority(3)
                   .taskStatus(TaskStatus.ON_TIME)
                   .isCompleted(false)
                   .taskDateCreation(LocalDateTime.now())
                   .logDateCreated(OffsetDateTime.now())
                   .logLastUpdated(OffsetDateTime.now())
                   .build();

            repository.saveAll(Arrays.asList(task1, task2, task3));
         }
      };
   }

}
