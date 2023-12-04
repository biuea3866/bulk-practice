package com.example.workerserver.infrastructure.entity

import java.time.ZonedDateTime
import jakarta.persistence.*

@Entity
@Table(name = "BulkRequestLog")
class BulkRequestLog(
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    val id: Long,

    @Column(name = "total_count")
    val totalCount: Int,

    @Column(name = "start_date")
    val startDate: ZonedDateTime,

    @Column(name = "end_date")
    val endDate: ZonedDateTime?,
) {
}