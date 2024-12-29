package ba.edu.ibu.inventorymanagementwebapp.rest.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Inventory",
                version = "1.0.0",
                description = "AI SE Backend Application",
                contact = @Contact(name = "Hamza Bakaran", email = "hamza.bakaran@stu.ibu.edu.ba")
        ),
        servers = {
                @Server(url = "/", description = "Default Server URL")
        }
)

public class SwaggerConfiguration {

}