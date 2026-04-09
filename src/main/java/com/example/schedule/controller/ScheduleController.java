package com.example.schedule.controller;

import com.example.schedule.dto.CreateScheduleRequest;
import com.example.schedule.dto.CreateScheduleResponse;
import com.example.schedule.service.ScheduleService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor // final 필드 자동 생성사
@RequestMapping("/schedules") // 모든 요청 앞에 /schedules붙음
public class ScheduleController {
    private final ScheduleService scheduleService;

@PostMapping
    public ResponseEntity<CreateScheduleResponse> createSchedul(@RequestBody CreateScheduleRequest request){
    CreateScheduleResponse result = scheduleService.save(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(result);
}
}
