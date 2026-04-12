    package com.example.schedule.service;

    import com.example.schedule.dto.*;
    import com.example.schedule.entity.Comment;
    import com.example.schedule.entity.Schedule;
    import com.example.schedule.repository.CommentRepository;
    import com.example.schedule.repository.ScheduleRepository;
    import lombok.RequiredArgsConstructor;
    import org.springframework.scheduling.annotation.Schedules;
    import org.springframework.stereotype.Service;
    import org.springframework.transaction.annotation.Transactional;

    import java.util.ArrayList;
    import java.util.List;

    @Service
    @RequiredArgsConstructor // final 필드 자동으로 생성자를 만들어줌
    public class ScheduleService {
        private final ScheduleRepository scheduleRepository; //Service가 Repository를 사용하기 위해 가져오는 것
        private final CommentRepository commentRepository;
        @Transactional
        public CreateScheduleResponse save(CreateScheduleRequest request){
            Schedule schedule = new Schedule(
                    request.getTitle(),
                    request.getContent(),
                    request.getAuthor(),
                    request.getPassword()
            );
            Schedule savdSchedule = scheduleRepository.save(schedule);

            return new CreateScheduleResponse(
              savdSchedule.getId(),
              savdSchedule.getTitle(),
              savdSchedule.getContent(),
              savdSchedule.getAuthor(),
                    savdSchedule.getCreatedAt(),
                    savdSchedule.getModifiedAt()
            );
        }
        @Transactional(readOnly = true)
        public GetScheduleResponse getOne(Long scheduleId){
            Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                    () -> new IllegalStateException("존재하지 않는 유저입니다.")
            );
            List<Comment> comments = commentRepository.findByScheduleId(scheduleId);
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
        @Transactional(readOnly = true)
        public List<GetScheduleResponse> getALl(String author) {
            List<Schedule> schedules;
            if (author != null){
                schedules = scheduleRepository.findByAuthor(author);
            } else {
                schedules = scheduleRepository.findAll();
            }

            List<GetScheduleResponse> dtos = new ArrayList<>();
            for (Schedule schedule : schedules){
                GetScheduleResponse dto = new GetScheduleResponse(
                        schedule.getId(),
                        schedule.getTitle(),
                        schedule.getContent(),
                        schedule.getAuthor(),
                        schedule.getCreatedAt(),
                        schedule.getModifiedAt(),
                        new ArrayList<>()
                );
                dtos.add(dto);
            }
            return dtos;
        }
        @Transactional
        public UpdateScheduleResponse update(Long scheduleId, UpdateScheduleRequest request){
            Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                    () -> new IllegalStateException("존재하지 않는 일정입니다.")
            );
            if (!schedule.getPassword().equals(request.getPassword())){
                throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
            }
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
        @Transactional
        public void delete (Long scheduleId, String password){
            Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                    () -> new IllegalStateException("존재하지 않는 일정입니다.")
            );
            if (!schedule.getPassword().equals(password)){
                throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
            }
            scheduleRepository.deleteById(scheduleId);
        }
    }
