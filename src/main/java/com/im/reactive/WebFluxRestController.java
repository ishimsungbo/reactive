package com.im.reactive;

import io.netty.channel.nio.NioEventLoopGroup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.Netty4ClientHttpRequestFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

@Slf4j
@RestController
public class WebFluxRestController {

    private final String URL1 = "http://localhost:8081/service?req={req}";
    private final String URL2 = "http://localhost:8081/service2?req={req}";

    @Autowired MyService myService;

    WebClient client = WebClient.create();

    @GetMapping("/rest")
    public Mono<String> rest(int idx) {
        //Mono<ClientResponse> res = client.get().uri(URL1,idx).exchange();
        //return body;
        //return  client.get().uri(URL1,idx).exchange().flatMap(c -> c.bodyToMono(String.class))

        return client.get().uri(URL1, idx).exchange()
                .flatMap(c -> c.bodyToMono(String.class))
                .flatMap(res1 -> client.get().uri(URL2, res1).exchange())
                .flatMap(c -> c.bodyToMono(String.class));
    }

    @Service
    public static class MyService{
        public String work(String req){
            return req + "/asyncwork";
        }
    }
}
