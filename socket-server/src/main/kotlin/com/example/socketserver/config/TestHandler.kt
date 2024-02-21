package com.example.socketserver.config

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelFutureListener
import io.netty.channel.ChannelHandler
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.nio.ByteBuffer

@Component
@ChannelHandler.Sharable
class TestHandler: ChannelInboundHandlerAdapter() {
    private val logger = LoggerFactory.getLogger(javaClass)
    private val dataLength = 2048
    private lateinit var buffer: ByteBuf

    // 핸들러 생성 시 호출되는 메서드
    override fun handlerAdded(ctx: ChannelHandlerContext) {
        this.buffer = ctx.alloc().buffer(dataLength)
    }

    // 핸들러 제거 시 호출되는 메서드
    override fun handlerRemoved(ctx: ChannelHandlerContext) {
        this.buffer.release()
    }

    // 클라이언트와 연결되어 새로운 트래픽을 수신할 때 호출되는 메서드
    override fun channelActive(ctx: ChannelHandlerContext) {
        val address = ctx.channel().remoteAddress().toString()
        logger.info("Connected to: $address")
    }

    override fun channelRead(ctx: ChannelHandlerContext, msg: Any) {
        val bufferFromClient = msg as ByteBuf
        // 클라이언트에서 보내는 데이터가 축적됨
        this.buffer.writeBytes(bufferFromClient)
        bufferFromClient.release()
        val future = ctx.writeAndFlush(this.buffer)
        future.addListener { ChannelFutureListener.CLOSE }
    }

    override fun exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable) {
        ctx.close()
        cause.printStackTrace()
    }
}