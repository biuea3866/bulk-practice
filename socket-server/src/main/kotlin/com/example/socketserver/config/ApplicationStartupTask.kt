package com.example.socketserver.config

import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component

@Component
class ApplicationStartupTask(
    private val nettyServerSocket: NettyServerSocket
): ApplicationListener<ApplicationReadyEvent> {
    // 스프링 부트 서비스를 시작 시 초기화하는 코드를 bean으로 생성
    // 네티 서버 소켓을 시작
    override fun onApplicationEvent(event: ApplicationReadyEvent) {
        this.nettyServerSocket.start()
    }
}