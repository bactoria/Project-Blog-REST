package com.bactoria.toy1;

        import org.springframework.boot.SpringApplication;
        import org.springframework.boot.autoconfigure.SpringBootApplication;
        import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@EnableJpaAuditing //JPA Auditing 활성화
@SpringBootApplication
public class Toy1Application {

    public static void main(String[] args) {
        SpringApplication.run(Toy1Application.class, args);
    }
}
