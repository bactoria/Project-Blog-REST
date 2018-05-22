package com.bactoria.toy1;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@EnableJpaAuditing //JPA Auditing 활성화
@SpringBootApplication
public class Toy1Application {

/*
    //local
    public static final String APPLICATION_LOCATIONS = "spring.config.location="
            + "classpath:../application.yml";

            //+ ", C:/Users/bactoria/real-application.yml";
*/

    //ec2

    public static final String APPLICATION_LOCATIONS = "spring.config.location="
            + "classpath:application.yml,"
            + "/home/ec2-user/app/config/myBlog/real-application.yml";


    public static void main(String[] args) {
        new SpringApplicationBuilder(Toy1Application.class)
                .properties(APPLICATION_LOCATIONS)
                .run(args);
    }
}