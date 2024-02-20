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

    override fun handlerAdded(ctx: ChannelHandlerContext) {
        this.buffer = ctx.alloc().buffer(dataLength)
    }

    override fun handlerRemoved(ctx: ChannelHandlerContext) {
        this.buffer.release()
    }

    override fun channelActive(ctx: ChannelHandlerContext) {
        val address = ctx.channel().remoteAddress().toString()
        logger.info("Connected to: $address")
    }

    override fun channelRead(ctx: ChannelHandlerContext, msg: Any) {
        val bufferFromClient = msg as ByteBuf
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