package com.example.workerserver.domain

class BulkPublishMessage(
    val success: Boolean,
    val requestLogId: Long,
    val processCount: Int
) {
    companion object {
        fun success(
            requestLogId: Long,
            processCount: Int
        ): BulkPublishMessage {
            return BulkPublishMessage(
                success = true,
                requestLogId = requestLogId,
                processCount = processCount
            )
        }

        fun fail(
            requestLogId: Long,
            processCount: Int
        ): BulkPublishMessage {
            return BulkPublishMessage(
                success = false,
                requestLogId = requestLogId,
                processCount = processCount
            )
        }
    }
}