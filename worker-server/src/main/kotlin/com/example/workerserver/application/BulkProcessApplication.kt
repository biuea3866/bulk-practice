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
        sequenceIdx: Int
    ) {
        bulkProcessService.execute(
            requestLogId = requestLogId,
            applicantId = applicantId,
            sequenceIdx = sequenceIdx
        )
    }

    @Transactional
    fun failProcess(
        requestLogId: Long,
        applicantId: Int,
        sequenceIdx: Int,
        failReason: String?
    ) {
        failProcessService.execute(
            requestLogId = requestLogId,
            applicantId = applicantId,
            sequenceIdx = sequenceIdx,
            failReason = failReason
        )
    }
}