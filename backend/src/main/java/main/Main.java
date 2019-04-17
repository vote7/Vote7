package main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource("classpath:app-config.xml")
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class
        , JpaRepositoriesAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class Main {

    //  http://localhost:8080/api/test  - api
    //  http://localhost:8080/website/test - strona
    public static void main(String []args){
        SpringApplication.run(Main.class,args);
    }
}
