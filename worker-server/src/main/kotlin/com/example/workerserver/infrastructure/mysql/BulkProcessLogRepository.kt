package com.example.workerserver.infrastructure.mysql

import com.example.workerserver.infrastructure.entity.BulkProcessLog
import com.example.workerserver.infrastructure.entity.BulkRequestLog
import org.springframework.data.jpa.repository.JpaRepository

interface BulkProcessLogRepository: JpaRepository<BulkProcessLog, Long> {

}