package com.example.schedule.repository;

import com.example.schedule.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
// JpaRepository<Comment, Long> 상속으로 기본 CRUD 메서드를 자동으로 제공받음
public interface CommentRepository extends JpaRepository<Comment, Long> {
    int countByScheduleId (Long scheduleId); // JPA가 메서드 이름만 보고 자동으로 SQL 만들어주는 기능
    List<Comment>findByScheduleId (Long scheduleId);
}
