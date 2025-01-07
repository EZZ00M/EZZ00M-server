package ezz00m.ezz00mserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication(exclude = HibernateJpaAutoConfiguration.class)
public class Ezz00MServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(Ezz00MServerApplication.class, args);
    }

}
