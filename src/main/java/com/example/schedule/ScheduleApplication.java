package com.example.schedule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
// JPA Auditing 활성화: @Createdata , @LastModfiedData 어노테이션 자동으로 동작 가능하게 해줌
// 이게 없으면 createAt, modifiedAt 필드에 값이 안들어감
@EnableJpaAuditing
@SpringBootApplication
public class ScheduleApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScheduleApplication.class, args);
    }

}
