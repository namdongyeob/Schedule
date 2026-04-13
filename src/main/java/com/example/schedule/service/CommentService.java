package com.example.schedule.service;

import com.example.schedule.dto.CreateCommentRequest;
import com.example.schedule.dto.CreateCommentResponse;
import com.example.schedule.entity.Comment;
import com.example.schedule.repository.CommentRepository;
import com.example.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service // Service 빈으로 등록
@RequiredArgsConstructor // final 필드 자동으로 생성자를 만들어줌
public class CommentService {
    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;
    @Transactional
    public CreateCommentResponse save(Long scheduleId,CreateCommentRequest request){
        if (request.getContent() == null || request.getContent().isEmpty()){
            // 유효성 검사: content가 없으면 예외 발생
            throw new IllegalStateException("댓글 내용은 필수값입니다.");
        }
        // 유효성 검사: content의 길이 제한
        if (request.getContent().length() > 100){
            throw new IllegalStateException("댓글 내용은 100자 이내여야 합니다.");
        }
        if (request.getAuthor() == null || request.getAuthor().isEmpty()){
            throw new IllegalStateException("작성자명은 필수값입니다.");
        }
        if (request.getPassword() == null || request.getPassword().isEmpty()){
            throw new IllegalStateException("비밀번호는 필수값입니다.");
        }
        // 댓글을 달 일정이 실제로 존재하는지 확인
        // -> 없으면 orElseThrow로 예외 발생
        scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new IllegalStateException("존재하지 않는 일정입니다.")
        );
        // 해당 일정의 댓글 수가 10개를 초과하는지 체크
        int commentCount = commentRepository.countByScheduleId(scheduleId);
        if (commentCount >= 10) {
            throw new IllegalStateException("댓글은 최대 10개까지만 작성할수 있습니다.");
        }
        // 검증 통과 후 Comment 엔티티 생성
        Comment comment = new Comment(
                request.getContent(),
                request.getAuthor(),
                request.getPassword(),
                scheduleId
        );
        // DB에 저장 (저장된 엔티티르 반환받음 -> ID, createAt등이 채워진 상태로 반환)
        Comment savedComment =commentRepository.save(comment);
        // 저장된 데이터를 응담 DTO로 변환해서 반환 (password는 포함되지 않음) (DB객체를 클라이언트한테 보내도 되는 형태로 바꿔서 반환)
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
