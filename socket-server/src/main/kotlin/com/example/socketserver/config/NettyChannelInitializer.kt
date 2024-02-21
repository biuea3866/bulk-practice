package com.example.socketserver.config

import io.netty.channel.ChannelInitializer
import io.netty.channel.socket.SocketChannel
import org.springframework.stereotype.Component

@Component
class NettyChannelInitializer(
    private val testHandler: TestHandler
): ChannelInitializer<SocketChannel>() {
    // 클라이언트 소켓 채널이 생성될 때 호출
    override fun initChannel(ch: SocketChannel) {
        val pipeline = ch.pipeline()
        val testDecoder = TestDecoder()

        pipeline.addLast(testHandler)
        pipeline.addLast(testDecoder)
    }
}