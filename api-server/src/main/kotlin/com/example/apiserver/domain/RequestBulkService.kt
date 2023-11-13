package com.example.apiserver.domain

import com.example.apiserver.infrastructure.entity.BulkRequestLog
import com.example.apiserver.infrastructure.kafka.KafkaPublisher
import com.example.apiserver.infrastructure.mysql.BulkRequestLogRepository
import org.springframework.stereotype.Service

@Service
class RequestBulkService(
    private val bulkRequestLogRepository: BulkRequestLogRepository,
    private val kafkaPublisher: KafkaPublisher
) {
    fun execute(applicantIds: List<Int>): Long {
        val bulkRequestLog = this.bulkRequestLogRepository.save(BulkRequestLog())
        applicantIds.forEach { applicantId -> this.kafkaPublisher.publish(bulkRequestLog, applicantId) }
        return bulkRequestLog.id
    }
}