package com.cj.core.timing;

import com.cj.core.service.DBService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ScheduledService {


    @Autowired
    private DBService dbService;

    @Value("${isbackup}")
    private Boolean isbackup;

    @Scheduled(cron = "0 0 4 * * *")
    @Async("getAsyncExecutor")
    public void scheduled() {
        if (isbackup) {
            log.info("=====>>>>>数据备份");
            dbService.backup();
        }
    }
}
