package es.televoip;

import java.time.ZoneId;
import java.time.ZonedDateTime;
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
   public CommandLineRunner executeOnStartup() {
      return args -> {
         // Obtén la hora y zona horaria del sistema
         ZonedDateTime systemTime = ZonedDateTime.now();
         System.out.println("Hora y zona horaria del sistema: " + systemTime);

         // Define la zona horaria de Madrid
         ZoneId madridZone = ZoneId.of("Europe/Madrid");
         ZonedDateTime madridTime = ZonedDateTime.now(madridZone);
         System.out.println("Hora y zona horaria de Madrid: " + madridTime);
      };
   }

}
