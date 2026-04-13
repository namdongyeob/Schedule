package com.example.schedule.controller;

import com.example.schedule.dto.*;
import com.example.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // 반환값을 jSON으로 바꿔서 응답 body에 담음
@RequiredArgsConstructor // final 필드 자동 생성사
@RequestMapping("/schedules") // 모든 요청 앞에 /schedules붙음
public class ScheduleController {
    // 비지니스는 Service에 위임, controller는 요청/음답 처리만 담당
    private final ScheduleService scheduleService;
    // 일정 생성
@PostMapping
    public ResponseEntity<CreateScheduleResponse> createSchedule(
            // HTTP 요청 body의 JSON을 CreateScheduleRequest 객체로 자동 변환
            @RequestBody CreateScheduleRequest request){
    CreateScheduleResponse result = scheduleService.save(request);
    // 201 Created: 새 리소스 생성 성공시 사용되는 HTTP 상태코드
    return ResponseEntity.status(HttpStatus.CREATED).body(result);
}
    // 일정 단건 조회
@GetMapping("/{scheduleId}")
    public ResponseEntity<GetScheduleResponse> getOneSchedule(
            // URL 경로의 {scheduleId} 값을 Long 타입으로 자동 변환해서 주입
            @PathVariable Long scheduleId){
    GetScheduleResponse result = scheduleService.getOne(scheduleId);
    // 200 OK: 조회 성공시 사용되는 HTTP 상태코드
    return ResponseEntity.status(HttpStatus.OK).body(result);
}
    // 일정 다건 조회 (작성자 필터링 선택)
@GetMapping
    public ResponseEntity<List<GetScheduleResponse>> getAllSchedule(
            // (required = false -> author 없이 요청해도 오류 없음 (전체 선택)
            @RequestParam(required = false) String author){
    List<GetScheduleResponse> result = scheduleService.getALl(author);
    // 200 OK: 조회 성공시 사용되는 HTTP 상태코드
    return ResponseEntity.status(HttpStatus.OK).body(result);
}
    // 일정 수정
@PatchMapping("/{scheduleId}")
    public ResponseEntity<UpdateScheduleResponse> update(
        @RequestBody UpdateScheduleRequest request,
        @PathVariable Long scheduleId){
    UpdateScheduleResponse result = scheduleService.update(scheduleId, request);
    return ResponseEntity.status(HttpStatus.OK).body(result);
}
    // 일정 삭제
@DeleteMapping("/{scheduleId}")
    public ResponseEntity<Void> delete (
            @PathVariable Long scheduleId,
            @RequestBody DeleteScheduleRequest request){
    scheduleService.delete(scheduleId, request.getPassword());
    // 204 No content: 삭제 성공, 응답 body 없음
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
}
}
