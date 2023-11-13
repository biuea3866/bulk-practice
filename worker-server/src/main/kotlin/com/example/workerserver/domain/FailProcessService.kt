package com.example.workerserver.domain

import com.example.workerserver.infrastructure.entity.BulkProcessLog
import com.example.workerserver.infrastructure.mysql.BulkProcessLogRepository
import org.springframework.stereotype.Service
import java.time.ZonedDateTime

@Service
class FailProcessService(
    private val bulkProcessLogRepository: BulkProcessLogRepository
) {
    fun execute(
        requestLogId: Long,
        applicantId: Int,
        sequenceIdx: Int,
        failReason: String?,
    ) {
        bulkProcessLogRepository.save(
            BulkProcessLog(
                id = 0,
                bulkActionRequestId = requestLogId,
                sequenceIdx = sequenceIdx,
                failureReason = "$applicantId 지원자 실패 사유: $failReason",
                success = false,
                startDate = ZonedDateTime.now(),
                endDate = ZonedDateTime.now()
            )
        )
    }
}