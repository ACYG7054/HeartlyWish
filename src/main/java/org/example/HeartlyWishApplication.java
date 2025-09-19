package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class HeartlyWishApplication {

    public static void main(String[] args) {
        SpringApplication.run(HeartlyWishApplication.class, args);
    }

    @Component
    static class CatPrinter implements ApplicationListener<ContextRefreshedEvent> {

        @Override
        public void onApplicationEvent(ContextRefreshedEvent event) {
            System.out.println("  /\\_/\\  ");
            System.out.println(" ( °w° ) ");
            System.out.println("  > ^ <  ");
            System.out.println("应用启动成功！这里有一只小猫猫送给你~");
        }
    }
}
