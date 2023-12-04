package com.example.workerserver.infrastructure.entity

import jakarta.persistence.*
import java.time.ZonedDateTime

@Entity
@Table(name = "BulkProcessLog")
class BulkProcessLog(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(name = "bulk_action_request_id")
    val bulkActionRequestId: Long,

    @Column(name = "sequence_idx")
    val sequenceIdx: Int,

    @Column(name = "success")
    val success: Boolean,

    @Column(name = "failure_reason")
    val failureReason: String?,

    @Column(name = "start_date")
    val startDate: ZonedDateTime,

    @Column(name = "end_date")
    var endDate: ZonedDateTime?,
) {
    fun end(): BulkProcessLog {
        this.endDate = ZonedDateTime.now()

        return this
    }
}