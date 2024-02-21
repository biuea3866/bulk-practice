package com.example.socketserver.config

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.ByteToMessageDecoder
import org.springframework.stereotype.Component

@Component
class TestDecoder: ByteToMessageDecoder() {
    // 정해진 데이터 길이(byte)만큼 버퍼에서 데이터를 읽어옴
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