package com.example.schedule.service;

import com.example.schedule.dto.CreateCommentRequest;
import com.example.schedule.dto.CreateCommentResponse;
import com.example.schedule.entity.Comment;
import com.example.schedule.repository.CommentRepository;
import com.example.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;
    @Transactional
    public CreateCommentResponse save(Long scheduleId,CreateCommentRequest request){
        if (request.getContent() == null || request.getContent().isEmpty()){
            throw new IllegalStateException("댓글 내용은 필수값입니다.");
        }
        if (request.getContent().length() > 100){
            throw new IllegalStateException("댓글 내용은 100자 이내여야 합니다.");
        }
        if (request.getAuthor() == null || request.getAuthor().isEmpty()){
            throw new IllegalStateException("작성자명은 필수값입니다.");
        }
        if (request.getPassword() == null || request.getPassword().isEmpty()){
            throw new IllegalStateException("비밀번호는 필수값입니다.");
        }
        scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new IllegalStateException("존재하지 않는 일정입니다.")
        );
        int commentCount = commentRepository.countByScheduleId(scheduleId);
        if (commentCount >= 10) {
            throw new IllegalStateException("댓글은 최대 10개까지만 작성할수 있습니다.");
        }
        Comment comment = new Comment(
                request.getContent(),
                request.getAuthor(),
                request.getPassword(),
                scheduleId
        );
        Comment savedComment =commentRepository.save(comment);
        return new CreateCommentResponse(
                savedComment.getId(),
                savedComment.getContent(),
                savedComment.getAuthor(),
                savedComment.getScheduleId(),
                savedComment.getCreatedAt(),
                savedComment.getModifiedAt()
        );
    }
}
