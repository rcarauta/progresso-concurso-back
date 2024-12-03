package br.co.progresso.concurso;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class ProgressoConcursoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProgressoConcursoApplication.class, args);
	}
	
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                // Permite que todas as rotas aceitem requisições do frontend
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:5173/")  // Permite apenas o domínio do frontend
                        .allowedMethods("OPTIONS","GET", "POST", "PUT", "DELETE")  // Métodos permitidos
                        .allowedHeaders("*")  // Permite todos os cabeçalhos
                        .allowCredentials(true);  // Permite o envio de cookies, se necessário
            }
        };
    }
    
   /* @Bean
    public CommandLineRunner runDataLoader(ApplicationContext applicationContext) {
        return args -> {
            DataLoader dataLoader = applicationContext.getBean(DataLoader.class);
            dataLoader.run(args);
        };
    } */

}
