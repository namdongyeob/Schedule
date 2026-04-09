package com.example.schedule.controller;

import com.example.schedule.dto.CreateScheduleRequest;
import com.example.schedule.dto.CreateScheduleResponse;
import com.example.schedule.dto.GetScheduleResponse;
import com.example.schedule.service.ScheduleService;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor // final 필드 자동 생성사
@RequestMapping("/schedules") // 모든 요청 앞에 /schedules붙음
public class ScheduleController {
    private final ScheduleService scheduleService;

@PostMapping
    public ResponseEntity<CreateScheduleResponse> createSchedule(@RequestBody CreateScheduleRequest request){
    CreateScheduleResponse result = scheduleService.save(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(result);
}
@GetMapping("/{scheduleId}")
    public ResponseEntity<GetScheduleResponse> GetOneSchedule(@PathVariable Long scheduleId){
    GetScheduleResponse result = scheduleService.getOne(scheduleId);
    return ResponseEntity.status(HttpStatus.OK).body(result);
}
@GetMapping
    public ResponseEntity<List<GetScheduleResponse>> getAllSchedule(
            @RequestParam(required = false) String author){
    List<GetScheduleResponse> result = scheduleService.getALl(author);
    return ResponseEntity.status(HttpStatus.OK).body(result);
}
}
