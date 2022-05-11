package com.im.reactive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ReactiveApplication {

    public static void main(String[] args) {
        System.setProperty("reactor.ipc.netty.workerCount","2");
        System.setProperty("reactor.ipc.netty.pool.maxConnections","2000");
        SpringApplication.run(ReactiveApplication.class, args);
    }

}
