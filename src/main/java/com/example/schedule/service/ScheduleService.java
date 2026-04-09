    package com.example.schedule.service;

    import com.example.schedule.dto.CreateScheduleRequest;
    import com.example.schedule.dto.CreateScheduleResponse;
    import com.example.schedule.entity.Schedule;
    import com.example.schedule.repository.ScheduleRepository;
    import lombok.RequiredArgsConstructor;
    import org.springframework.scheduling.annotation.Schedules;
    import org.springframework.stereotype.Service;
    import org.springframework.transaction.annotation.Transactional;

    @Service
    @RequiredArgsConstructor // final 필드 자동으로 생성자를 만들어줌
    public class ScheduleService {
        private final ScheduleRepository scheduleRepository; //Service가 Repository를 사용하기 위해 가져오는 것
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
    }
