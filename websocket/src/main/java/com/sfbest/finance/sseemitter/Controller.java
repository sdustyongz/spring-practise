package com.sfbest.finance.sseemitter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.concurrent.CompletableFuture;

@org.springframework.stereotype.Controller
@RequestMapping("/sse")
public class Controller {

    @Autowired
    SseEmitterServicd service;

    @GetMapping(value = "test/{clientId}")
     public SseEmitter test(@PathVariable("clientId") String clientId){
        final SseEmitter emitter = service.getConn(clientId);
        CompletableFuture.runAsync(() -> {
            try {
                service.send(clientId);
            } catch (Exception e) {
                throw new RuntimeException("推送数据异常");
            }
        });

        return emitter;
     }


}
