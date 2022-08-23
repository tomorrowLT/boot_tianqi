package com.lt.boot_tianqi;

import com.lt.boot_tianqi.controller.Pusher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling // 开启定时任务
public class BootTianqiApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootTianqiApplication.class, args);
    }

    // 定时 两分钟执行一次
    //@Scheduled(cron = "0 0/2 * * * ?")
    @Scheduled(cron = "0 0 8 * * ?")
    public void goodMorning(){
        Pusher.push();
    }
}
