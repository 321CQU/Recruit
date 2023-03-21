package cn.saladday.recruit_321cqu;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
@Slf4j
@SpringBootApplication()
public class Recruit321cquApplication {

    public static void main(String[] args) {
        SpringApplication.run(Recruit321cquApplication.class, args);
        log.info("::application starting...");
    }

}
