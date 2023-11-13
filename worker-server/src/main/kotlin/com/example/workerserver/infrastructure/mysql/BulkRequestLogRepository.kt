package com.example.workerserver.infrastructure.mysql

import com.example.workerserver.infrastructure.entity.BulkRequestLog
import org.springframework.data.jpa.repository.JpaRepository

interface BulkRequestLogRepository: JpaRepository<BulkRequestLog, Long> {
}