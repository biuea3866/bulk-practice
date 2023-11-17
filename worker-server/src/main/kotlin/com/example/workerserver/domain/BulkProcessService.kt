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
        sequenceIdx: Int,
        end: Boolean
    ) {
        val processLog = bulkProcessLogRepository.save(BulkProcessLog(
            id = 0,
            bulkActionRequestId = requestLogId,
            sequenceIdx = sequenceIdx,
            failureReason = null,
            success = true,
            startDate = ZonedDateTime.now(),
            endDate = null
        ))

//        if (sequenceIdx % 10 == 0) {
//            redisPublisher.publish(BulkPublishMessage.success(
//                requestLogId = requestLogId,
//                processCount = sequenceIdx
//            ))
//        }
//
//        if (end) {
//            redisPublisher.publish(
//                BulkPublishMessage.success(
//                    requestLogId = requestLogId,
//                    processCount = sequenceIdx
//                )
//            )
//        }

        redisPublisher.publish(BulkPublishMessage.success(
            requestLogId = requestLogId,
            processCount = sequenceIdx
        ))

        bulkProcessLogRepository.save(processLog.end())
    }
}