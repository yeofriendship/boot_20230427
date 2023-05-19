package com.example.scheduler;

import java.util.Date;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.entity.Board1;
import com.example.repository.Board1Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class MyScheduler {
    final Board1Repository board1Repository;

    // cron tab
    // 초, 분, 시간, 일, 월, 요일
    // */5 * * * * * : 5초 간격으로 동작 
    // */20 * * * * * : 20초 간격으로 동작 
    // 0 * * * * * : 0초가 될 때(정각에, 1분 단위) 동작 
    // @Scheduled(cron = "*/5 * * * * *")
    public void printDate() {
        log.info("{}", new Date().toString());
        
        List<Board1> list = board1Repository.findAll();
        log.info("{}", list.toString());
    }
}
