package com.example.apiserver.infrastructure.mysql

import com.example.apiserver.infrastructure.entity.BulkRequestLog
import org.springframework.data.jpa.repository.JpaRepository

interface BulkRequestLogRepository: JpaRepository<BulkRequestLog, Long> {
}