package com.example.workerserver.domain

import com.example.workerserver.infrastructure.entity.BulkProcessLog
import com.example.workerserver.infrastructure.mysql.BulkProcessLogRepository
import com.example.workerserver.infrastructure.redis.RedisPublisher
import org.springframework.stereotype.Service
import java.time.ZonedDateTime

@Service
class BulkProcessService(
    private val bulkProcessLogRepository: BulkProcessLogRepository,
    private val redisPublisher: RedisPublisher
) {
    fun execute(
        requestLogId: Long,
        applicantId: Int,
        sequenceIdx: Int
    ) {
        bulkProcessLogRepository.save(
            BulkProcessLog(
                id = 0,
                bulkActionRequestId = requestLogId,
                sequenceIdx = sequenceIdx,
                failureReason = null,
                success = true,
                startDate = ZonedDateTime.now(),
                endDate = ZonedDateTime.now()
            )
        )
    }
}