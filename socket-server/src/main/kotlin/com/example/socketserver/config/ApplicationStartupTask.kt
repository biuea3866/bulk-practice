package com.example.socketserver.config

import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component

@Component
class ApplicationStartupTask(
    private val nettyServerSocket: NettyServerSocket
): ApplicationListener<ApplicationReadyEvent> {
    override fun onApplicationEvent(event: ApplicationReadyEvent) {
        this.nettyServerSocket.start()
    }
}