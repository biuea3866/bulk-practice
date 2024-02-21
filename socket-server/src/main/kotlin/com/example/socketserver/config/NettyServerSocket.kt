package com.example.socketserver.config

import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.Channel
import jakarta.annotation.PreDestroy
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.net.InetSocketAddress

@Component
class NettyServerSocket(
    private val serverBootstrap: ServerBootstrap,
    private val tcpPort: InetSocketAddress,
    private val channel: Channel
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    fun start() {
        runCatching {
            // ChannelFuture: IO operation의 결과나 상태를 제공하는 객체
            // 지정한 host, port로 소켓을 바인딩하고, incoming connections을 받도록 준비
            val future = serverBootstrap.bind(tcpPort).sync()
            // 서버 소켓이 닫힐 때까지 대기
            future.channel().closeFuture().sync()
        }.getOrElse {
            logger.error("NettyServerSocket start error.", it)
            throw Exception(it.message)
        }
    }

    // Bean을 제거하기 전 해야할 작업이 있을 때 사용
    @PreDestroy
    fun stop() {
        this.channel.close()
        this.channel.parent().closeFuture()
    }
}