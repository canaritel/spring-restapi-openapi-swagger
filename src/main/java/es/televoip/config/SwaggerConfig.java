package es.televoip.config;

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

   @Value("${openapi.dev-url}")
   private String devUrl;

   @Value("${openapi.prod-url}")
   private String prodUrl;

   @Bean
   public OpenAPI myOpenAPI() {
      Server devServer = new Server();
      devServer.setUrl(devUrl);
      devServer.setDescription("Server URL in Development environment");

      Server prodServer = new Server();
      prodServer.setUrl(prodUrl);
      prodServer.setDescription("Server URL in Production environment");

      Contact contact = new Contact();
      contact.setEmail("antonio@canaritel.es");
      contact.setName("Antonio González B.");
      contact.setUrl("https://www.canaritel.es");

      License mitLicense = new License().name("MIT License")
             .url("https://choosealicense.com/licenses/mit/");

      Info info = new Info()
             .title("Tutorial Management API")
             .version("1.0")
             .contact(contact)
             .description("This API exposes endpoints to manage tutorials.")
             .termsOfService("https://www.canaritel.es/legal.html")
             .license(mitLicense);

      return new OpenAPI()
             .info(info)
             .servers(List.of(devServer, prodServer));
   }
   


}
