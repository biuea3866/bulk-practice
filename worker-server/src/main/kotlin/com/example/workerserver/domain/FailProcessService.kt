package com.example.workerserver.domain

import com.example.workerserver.infrastructure.entity.BulkProcessLog
import com.example.workerserver.infrastructure.mysql.BulkProcessLogRepository
import com.example.workerserver.infrastructure.redis.RedisPublisher
import org.springframework.stereotype.Service
import java.time.ZonedDateTime

@Service
class FailProcessService(
    private val bulkProcessLogRepository: BulkProcessLogRepository,
    private val redisPublisher: RedisPublisher
) {
    fun execute(
        requestLogId: Long,
        applicantId: Int,
        sequenceIdx: Int,
        failReason: String?,
        end: Boolean
    ) {
        val processLog = BulkProcessLog(
            id = 0,
            bulkActionRequestId = requestLogId,
            sequenceIdx = sequenceIdx,
            failureReason = "$applicantId 지원자 실패 사유: $failReason",
            success = false,
            startDate = ZonedDateTime.now(),
            endDate = null
        )

        if (sequenceIdx % 10 == 0) {
            redisPublisher.publish(
                BulkPublishMessage.fail(
                    requestLogId = requestLogId,
                    processCount = sequenceIdx
                )
            )
        }

        if (end) {
            redisPublisher.publish(
                BulkPublishMessage.fail(
                    requestLogId = requestLogId,
                    processCount = sequenceIdx
                )
            )
        }

        bulkProcessLogRepository.save(processLog.end())
    }
}