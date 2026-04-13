package com.example.schedule.controller;

import com.example.schedule.dto.CreateCommentRequest;
import com.example.schedule.dto.CreateCommentResponse;
import com.example.schedule.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController // 반환값을 jSON으로 바꿔서 응답 body에 담음
@RequiredArgsConstructor // final 필드 자동 생성사
@RequestMapping("/schedules") // 모든 요청 앞에 /schedules붙음
public class CommentController {
    // Service계층에 비지니스 로직을 위임
    private final CommentService commentService;
    // 특정 일정에 댓글 생성
    @PostMapping("/{scheduleId}/comments")
    public ResponseEntity<CreateCommentResponse>createComment(
                // URL 경로의 {scheduleId} 값을 Long 타입으로 자동 변환해서 주입
                @PathVariable Long scheduleId,
                // HTTP요청 BODY의 JSON을 CreateCommentRequest 객체로 자동 변환
                @RequestBody CreateCommentRequest request){
        // 201 Created 상태코드와 함께 생성된 댓글 정보를 응답으로 변환
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.save(scheduleId, request));
    }
}
