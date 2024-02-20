package com.example.socketserver.config

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.ByteToMessageDecoder
import org.springframework.stereotype.Component

@Component
class TestDecoder: ByteToMessageDecoder() {
    private val dataLength = 2048

    override fun decode(
        ctx: ChannelHandlerContext,
        `in`: ByteBuf,
        out: MutableList<Any>
    ) {
        if (`in`.readableBytes() < dataLength) {
            return
        }

        out.add(`in`.readBytes(this.dataLength))
    }
}