package br.com.process.PIIV;

import java.util.Arrays;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackageClasses = br.com.process.controller.init.class)
@ComponentScan(basePackageClasses = br.com.process.controller.ProdutoController.class)
public class ProjetoIntegradorIvApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(ProjetoIntegradorIvApplication.class, args);
    }
    
    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {

            System.out.println("Vamos inspecionar os beans fornecidos pelo Spring Boot:");

            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                System.out.println(beanName);
            }

        };
    }

}