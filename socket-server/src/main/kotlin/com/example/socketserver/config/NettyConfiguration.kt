package com.example.socketserver.config

import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.ChannelOption
import io.netty.channel.EventLoopGroup
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.netty.handler.logging.LogLevel
import io.netty.handler.logging.LoggingHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.net.InetSocketAddress

@Configuration
class NettyConfiguration {
    private val host: String = "127.0.0.1"
    private val port: Int = 8080
    private val bossThread: Int = 1
    private val workerThread: Int = 1
    private val keepAlive: Boolean = true
    private val backlog: Int = 128

    @Bean
    fun serverBootStrap(nettyChannelInitializer: NettyChannelInitializer): ServerBootstrap {
        val serverBootstrap = ServerBootstrap()
        serverBootstrap.group(bossGroup(), workerGroup())
            // NioServerSocketChannel: incoming connection을 수락하기 위해 새로운 Channel을 객체화
            .channel(NioServerSocketChannel::class.java)
            .handler(LoggingHandler(LogLevel.DEBUG))
            // ChannelInitializer: 새로운 채널을 구성할 때 사용되는 handler, 주로 ChannelPipeline으로 구성
            .childHandler(nettyChannelInitializer)
        serverBootstrap.option(ChannelOption.SO_BACKLOG, backlog)
        return serverBootstrap
    }

    // boss: incoming connection을 수락하고, 수락한 connection을 worker에게 전달
    @Bean(destroyMethod = "shutdownGracefully")
    fun bossGroup(): EventLoopGroup {
        return NioEventLoopGroup(bossThread)
    }

    // worker: boss가 수락한 연결의 트래픽 관리
    @Bean(destroyMethod = "shutdownGracefully")
    fun workerGroup(): EventLoopGroup {
        return NioEventLoopGroup(workerThread)
    }

    // IP 소켓 주소
    // 도메인 이름으로 객체 생성 가능
    @Bean
    fun tcpPort(): InetSocketAddress {
        return InetSocketAddress(host, port)
    }
}