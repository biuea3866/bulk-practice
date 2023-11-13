package com.example.workerserver.application

import com.example.workerserver.domain.BulkProcessService
import com.example.workerserver.domain.FailProcessService
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class BulkProcessApplication(
    private val bulkProcessService: BulkProcessService,
    private val failProcessService: FailProcessService
) {
    @Transactional
    fun bulkProcess(
        requestLogId: Long,
        applicantId: Int,
        sequenceIdx: Int,
        end: Boolean
    ) {
        bulkProcessService.execute(
            requestLogId = requestLogId,
            applicantId = applicantId,
            sequenceIdx = sequenceIdx,
            end = end
        )
    }

    @Transactional
    fun failProcess(
        requestLogId: Long,
        applicantId: Int,
        sequenceIdx: Int,
        failReason: String?,
        end: Boolean
    ) {
        failProcessService.execute(
            requestLogId = requestLogId,
            applicantId = applicantId,
            sequenceIdx = sequenceIdx,
            failReason = failReason,
            end = end
        )
    }
}