package com.example.apiserver.infrastructure.entity

import java.time.ZonedDateTime
import javax.persistence.*

@Entity
@Table(name = "BulkRequestLog")
class BulkRequestLog(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(name = "total_count")
    val totalCount: Int,

    @Column(name = "start_date")
    val startDate: ZonedDateTime,

    @Column(name = "end_date")
    val endDate: ZonedDateTime?,
) {
}