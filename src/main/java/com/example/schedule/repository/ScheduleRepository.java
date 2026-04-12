package com.example.schedule.repository;
import java.util.List;
import java.util.Optional;

import com.example.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule,Long> {
    List<Schedule> findByAuthor(String author); // JPA가 메서드 이름만 보고 자동으로 SQL 만들어주는 기능


}
