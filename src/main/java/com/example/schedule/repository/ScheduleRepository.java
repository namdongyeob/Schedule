package com.example.schedule.repository;
import java.util.List;
import java.util.Optional;

import com.example.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
// JpaRepository<Schedule,Long> 상속으로 기본 CRUD 메서드를 자동으로 제공받음
// Schedule: 관리할 entity타입 / Long: 해당 entity의 PK 타입
public interface ScheduleRepository extends JpaRepository<Schedule,Long> {
    List<Schedule> findByAuthor(String author); // JPA가 메서드 이름만 보고 자동으로 SQL 만들어주는 기능


}
