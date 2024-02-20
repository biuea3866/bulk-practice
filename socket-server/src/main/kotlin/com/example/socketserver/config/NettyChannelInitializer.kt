package com.example.socketserver.config

import io.netty.channel.ChannelInitializer
import io.netty.channel.socket.SocketChannel
import org.springframework.stereotype.Component

@Component
class NettyChannelInitializer(
    private val testHandler: TestHandler
): ChannelInitializer<SocketChannel>() {
    override fun initChannel(ch: SocketChannel) {
        ch.pipeline().addLast(testHandler)
    }
}