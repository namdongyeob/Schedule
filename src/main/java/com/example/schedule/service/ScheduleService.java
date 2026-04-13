    package com.example.schedule.service;

    import com.example.schedule.dto.*;
    import com.example.schedule.entity.Comment;
    import com.example.schedule.entity.Schedule;
    import com.example.schedule.repository.CommentRepository;
    import com.example.schedule.repository.ScheduleRepository;
    import lombok.RequiredArgsConstructor;
    import org.springframework.stereotype.Service;
    import org.springframework.transaction.annotation.Transactional;

    import java.util.ArrayList;
    import java.util.List;

    @Service // Service빈으로 등록
    @RequiredArgsConstructor // final 필드 자동으로 생성자를 만들어줌
    public class ScheduleService {
        private final ScheduleRepository scheduleRepository; //Service가 Repository를 사용하기 위해 가져오는 것
        private final CommentRepository commentRepository; // 일정 단건 조회 시 댓글 몰록과 함께 가져오기 위해 가져옴
        @Transactional // 저장
        public CreateScheduleResponse save(CreateScheduleRequest request){
            // 유효성 검사: 필수값 및 길이 체크
            if (request.getTitle() == null || request.getTitle().isEmpty()){
                throw new IllegalStateException("제목은 필수값입니다.");
            }
            if (request.getTitle().length() > 30){
                throw new IllegalStateException("제목은 30자 이내여야합니다.");
            }
            if (request.getContent() == null || request.getContent().isEmpty()){
                throw new IllegalStateException("내용은 필수값입니다.");
            }
            if (request.getContent().length() > 200){
                throw new IllegalStateException("내용은 200자 이내여야합니다.");
            }
            if (request.getAuthor() == null || request.getAuthor().isEmpty()){
                throw new IllegalStateException("작성자명은 필수값입니다.");
            }
            if (request.getPassword() == null || request.getPassword().isEmpty()){
                throw new IllegalStateException("비밀번호는 필수값입니다.");
            }
            // 검증 통과 후 Schedule 엔티티 생성
            Schedule schedule = new Schedule(
                    request.getTitle(),
                    request.getContent(),
                    request.getAuthor(),
                    request.getPassword()
            );
            // DB에 저장 (저장 후 id, createdAt, modifiedAt이 채워진 엔티티를 반환받음)
            Schedule savdSchedule = scheduleRepository.save(schedule);
            // 저장된 데이터를 응담 DTO로 변환해서 반환 (DB객체를 클라이언트한테 보내도 되는 형태로 바꿔서 반환)
            return new CreateScheduleResponse(
              savdSchedule.getId(),
              savdSchedule.getTitle(),
              savdSchedule.getContent(),
              savdSchedule.getAuthor(),
                    savdSchedule.getCreatedAt(),
                    savdSchedule.getModifiedAt()
            );
        }
        // readOnly = true: 읽기 전용 트랜젝션
        @Transactional(readOnly = true)
        public GetScheduleResponse getOne(Long scheduleId){
            // ID로 일정 조회, 없으면 예외 발생
            Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                    () -> new IllegalStateException("존재하지 않는 유저입니다.")
            );
            // 해당 일정의 댓글 목록 조회
            List<Comment> comments = commentRepository.findByScheduleId(scheduleId);
            // Comment 엔티티 리스트 -> 응답 dto 리스트로 변환 (DB객체를 클라이언트한테 보내도 되는 형태로 바꿔서 반환)
            List<CreateCommentResponse> commentDtos = new ArrayList<>();
            for (Comment comment : comments){
                commentDtos.add(new CreateCommentResponse(
                        comment.getId(),
                        comment.getContent(),
                        comment.getAuthor(),
                        comment.getScheduleId(),
                        comment.getCreatedAt(),
                        comment.getModifiedAt()
                ));
            }
            // 일정 + 댓글 목록을 합쳐 응답 DTO로 반환 (DB객체를 클라이언트한테 보내도 되는 형태로 바꿔서 반환)
            return new GetScheduleResponse(
                    schedule.getId(),
                    schedule.getTitle(),
                    schedule.getContent(),
                    schedule.getAuthor(),
                    schedule.getCreatedAt(),
                    schedule.getModifiedAt(),
                    commentDtos
            );
        }
        // readOnly = true: 읽기 전용 트랜젝션
        // 일정 전체 조회 (작성자명 필터링)
        @Transactional(readOnly = true)
        public List<GetScheduleResponse> getALl(String author) {
            List<Schedule> schedules;
            // author 파라미터가 있으면 해당 작성자의 일정만 조회
            // 없으면 전체 일정 조회
            if (author != null){
                schedules = scheduleRepository.findByAuthor(author);
            } else {
                schedules = scheduleRepository.findAll();
            }
            // Schedul 엔티티 리스트 -> 응답 DTO 리스트로 변환 (DB객체를 클라이언트한테 보내도 되는 형태로 바꿔서 반환)
            // 목록 조회에서는 댓글을 가져오지 않음 -> 빈 리스트로 설정
            List<GetScheduleResponse> dtos = new ArrayList<>();
            for (Schedule schedule : schedules){
                GetScheduleResponse dto = new GetScheduleResponse(
                        schedule.getId(),
                        schedule.getTitle(),
                        schedule.getContent(),
                        schedule.getAuthor(),
                        schedule.getCreatedAt(),
                        schedule.getModifiedAt(),
                        new ArrayList<>() // 목록 조회시 댓글은 포함하지 않음
                );
                dtos.add(dto);
            }
            return dtos;
        }
        // 일정 수정
        @Transactional
        public UpdateScheduleResponse update(Long scheduleId, UpdateScheduleRequest request){
            // 수정할 일정 조회, 없으면 예외 발생
            Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                    () -> new IllegalStateException("존재하지 않는 일정입니다.")
            );
            // 비밀번호 일치 여부 확인 -> 틀리면 수정 불가
            if (!schedule.getPassword().equals(request.getPassword())){
                throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
            }
            // 엔티티의 update() 메서드를 호출해서 값을 변경
            schedule.update((request.getTitle()), request.getAuthor());
            return new UpdateScheduleResponse(
                    schedule.getId(),
                    schedule.getTitle(),
                    schedule.getContent(),
                    schedule.getAuthor(),
                    schedule.getCreatedAt(),
                    schedule.getModifiedAt()
            );
        }
        // 일정 삭제
        @Transactional
        public void delete (Long scheduleId, String password){
            // 삭제할 일정 조회, 없으면 예외 발생
            Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                    () -> new IllegalStateException("존재하지 않는 일정입니다.")
            );
            // 비밀번호 일치 여부 확인 -> 틀리면 삭제 불가
            if (!schedule.getPassword().equals(password)){
                throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
            }
            // ID로 해당 일정 삭제
            scheduleRepository.deleteById(scheduleId);
        }
    }
