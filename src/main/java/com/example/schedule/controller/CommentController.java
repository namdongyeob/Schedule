package com.example.schedule.controller;

import com.example.schedule.dto.CreateCommentRequest;
import com.example.schedule.dto.CreateCommentResponse;
import com.example.schedule.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.CacheResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/{scheduleId}/comments")
    public ResponseEntity<CreateCommentResponse>createComment(
                @PathVariable Long scheduleId,
                @RequestBody CreateCommentRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.save(scheduleId, request));
    }
}
