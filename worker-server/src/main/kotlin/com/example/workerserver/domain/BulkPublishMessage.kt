package com.example.workerserver.domain

import com.fasterxml.jackson.annotation.JsonProperty

data class BulkPublishMessage(
    @JsonProperty("success")
    val success: Boolean,
    @JsonProperty("requestLogId")
    val requestLogId: Long,
    @JsonProperty("processCount")
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