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
            val future = serverBootstrap.bind(tcpPort).sync()
            future.channel().closeFuture().sync()
        }.getOrElse {
            logger.error("NettyServerSocket start error.", it)
            throw Exception(it.message)
        }
    }

    @PreDestroy
    fun stop() {
        this.channel.close()
        this.channel.parent().closeFuture()
    }
}